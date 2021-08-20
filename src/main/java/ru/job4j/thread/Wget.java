package ru.job4j.thread;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class Wget implements Runnable {
    private final String urlIn;
    private final String urlOut;
    private final int speed;

    public Wget(String urlIn, String urlOut, int speed) {
        this.urlIn = urlIn;
        this.urlOut = urlOut;
        this.speed = speed;
    }

    @Override
    public void run() {
        try (BufferedInputStream in = new BufferedInputStream(new URL(urlIn).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(urlOut)) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            long finish;
            long result;
            long start = System.currentTimeMillis();
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
                finish = System.currentTimeMillis();
                result = finish - start;
                if (result < speed) {
                    Thread.sleep(speed - result);
                }
                start = System.currentTimeMillis();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        if (args.length != 3) {
            throw new IllegalArgumentException("Arguments set incorrectly");
        }
        String urlIn = args[0];
        String urlOut = args[1];
        int speed = Integer.parseInt(args[2]);
        Thread wget = new Thread(new Wget(urlIn, urlOut, speed));
        wget.start();
        wget.join();
    }
}
