package com.github.itonyli.nio;

import com.google.common.base.Charsets;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * @author tony
 * @date 2017-12-08 16:29:44
 */
public class NioClient {

    private final static int PORT = 8808;
    private final static int BUF_SIZE = 4096;

    private void init() throws IOException {
        Selector selector = Selector.open();
        SocketChannel channel = SocketChannel.open();
        channel.configureBlocking(false);
        channel.connect(new InetSocketAddress(PORT));
        channel.register(selector, SelectionKey.OP_CONNECT);
        for (; ; ) {
            selector.select();
            Iterator<SelectionKey> it = selector.selectedKeys().iterator();
            while (it.hasNext()) {
                SelectionKey key = it.next();
                it.remove();
                if (key.isConnectable()) {
                    doConnect(key);
                } else if (key.isReadable()) {
                    doRead(key);
                }
            }

        }
    }

    private void doConnect(SelectionKey key) throws IOException {
        SocketChannel channel = (SocketChannel) key.channel();
        if (channel.isConnectionPending()) {
            channel.finishConnect();
        }
        channel.configureBlocking(false);
        ByteBuffer byteBuffer = ByteBuffer.allocate(BUF_SIZE);
        byteBuffer.clear();
        byteBuffer.put((Thread.currentThread().getName() + " Client Data!").getBytes(Charsets.UTF_8));
        byteBuffer.flip();
        channel.write(byteBuffer);
        channel.close();
    }


    private void doRead(SelectionKey key) throws IOException {
        SocketChannel channel = (SocketChannel) key.channel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(BUF_SIZE);
        channel.read(byteBuffer);
        System.out.println(Thread.currentThread().getName() + " Receive From Server: " + new String(byteBuffer.array()).trim());
        channel.close();
        key.selector().close();
    }

    public static void main(String[] args) throws IOException {
        NioClient client = new NioClient();
        client.init();
    }
}
