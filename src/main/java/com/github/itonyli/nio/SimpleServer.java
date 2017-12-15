package com.github.itonyli.nio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

/**
 * @author tony
 * @date 2017-12-08 14:42:27
 */
public class SimpleServer {
    private static final ExecutorService CACHED_THREAD_POOL = Executors.newCachedThreadPool();

    private static class HandleMsg implements Runnable {
        Socket client;

        HandleMsg(Socket client) {
            this.client = client;
        }

        @Override
        public void run() {
            BufferedReader bufferedReader = null;
            PrintWriter printWriter = null;
            try {
                bufferedReader = new BufferedReader(new InputStreamReader(client.getInputStream()));
                printWriter = new PrintWriter(client.getOutputStream(), true);
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    printWriter.println("Server Thread: " + Thread.currentThread().getName() + " -> " + line);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (bufferedReader != null)
                        bufferedReader.close();
                    if (printWriter != null)
                        printWriter.close();
                    client.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(8808);
        Socket client;
        while (true) {
            client = ss.accept();
            System.out.println(client.getRemoteSocketAddress()+" Connect Success!");
            CACHED_THREAD_POOL.submit(new HandleMsg(client));
        }
    }

}
