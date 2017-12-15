package com.github.itonyli.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author tony
 * @date 2017-12-08 16:29:35
 */
public class NioServer {

    private Selector selector;
    private static final int PORT = 8808;
    private static final int BUFF_SIZE = 4096;


    private void init() throws Exception {
        this.selector = Selector.open();

        ServerSocketChannel channel = ServerSocketChannel.open();
        channel.configureBlocking(false);
        channel.socket().bind(new InetSocketAddress(PORT));

        channel.register(selector, SelectionKey.OP_ACCEPT);
        for (; ; ) {
            selector.select();
            Set<SelectionKey> keys = selector.selectedKeys();
            Iterator<SelectionKey> it = keys.iterator();
            while (it.hasNext()) {
                SelectionKey key = it.next();
                it.remove();
                if (key.isAcceptable()) {
                    doAccept(key);
                } else if (key.isReadable()) {
                    doRead(key);
                } else if (key.isValid() && key.isWritable()) {
                    doWrite(key);
                } else if (key.isConnectable()) {
                    System.out.println("Server Connect Success!");
                }
            }
        }
    }

    private void doAccept(SelectionKey key) throws IOException {
        ServerSocketChannel channel = (ServerSocketChannel) key.channel();
        System.out.println("ServerSocketChannel is listening!");
        SocketChannel clientChannel = channel.accept();
        clientChannel.configureBlocking(false);
        clientChannel.register(key.selector(), SelectionKey.OP_READ);
    }

    private void doRead(SelectionKey key) throws IOException {
        SocketChannel channel = (SocketChannel) key.channel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(BUFF_SIZE);
        int pos;
        while ((pos = channel.read(byteBuffer)) > 0) {
            byteBuffer.flip();
            String data = new String(byteBuffer.array()).trim();
            System.out.println(Thread.currentThread().getName() + " Server Receive Msg: " + data);
            byteBuffer.clear();
        }
        if (pos == -1) {
            channel.close();
        }
        channel.register(key.selector(), SelectionKey.OP_WRITE);
    }


    private void doWrite(SelectionKey key) throws IOException {
        ByteBuffer byteBuffer = ByteBuffer.allocate(BUFF_SIZE);
        byteBuffer.put("Server Receive Data!".getBytes());
        byteBuffer.flip();
        SocketChannel channel = (SocketChannel) key.channel();
        while (byteBuffer.hasRemaining()) {
            channel.write(byteBuffer);
        }
        byteBuffer.compact();
        channel.write(byteBuffer);
    }


    public static void main(String[] args) throws Exception {
        NioServer server = new NioServer();
        server.init();
    }

}
