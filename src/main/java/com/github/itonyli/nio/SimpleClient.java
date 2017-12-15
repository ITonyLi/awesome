package com.github.itonyli.nio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author tony
 * @date 2017-12-08 15:26:49
 */
public class SimpleClient {

    private static final ExecutorService FIXED_THREAD_POOL = Executors.newFixedThreadPool(9);

    public static void main(String[] args) throws IOException {
        for (int i = 1; i < 10; i++) {
            FIXED_THREAD_POOL.submit(new Runnable() {
                @Override
                public void run() {
                    Socket client = null;
                    BufferedReader bufferedReader = null;
                    PrintWriter printWriter = null;

                    try {
                        client = new Socket("localhost", 8808);
                        printWriter = new PrintWriter(client.getOutputStream(), true);
                        printWriter.println("Client Thread:" + Thread.currentThread().getName() + " -> write data!");
                        printWriter.flush();

                        bufferedReader = new BufferedReader(new InputStreamReader(client.getInputStream()));
                        System.out.println("Receive for server: "+bufferedReader.readLine());
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            if (printWriter != null)
                                printWriter.close();
                            if (bufferedReader != null)
                                bufferedReader.close();
                            if (client != null)
                                client.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }
    }

}
