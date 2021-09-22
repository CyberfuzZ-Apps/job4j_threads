package ru.job4j.thread;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class Wget implements Runnable {
    private final String url;
    private final int speed;

    public Wget(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }

    @Override
    public void run() {
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream("pom_tmp.xml")) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            double finish = 0;
            double result = 0;
            double pause = 0;
            double start = System.nanoTime();
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
                finish = System.nanoTime();
                result = (finish - start) / 1_000_000_000; /* in seconds */
                pause = (1024 / (double) speed) - result;
                pause = pause < 0 ? 0 : pause;
                Thread.sleep((long) pause * 1000);
                start = System.nanoTime();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        String url = args[0];
        String speedStr = args[1];
        int index = 0;
        int speed = 0;
        if (speedStr.contains("k")) {
            index = speedStr.indexOf("k");
            speed = Integer.parseInt(speedStr.substring(0, index)) * 1_000;
        } else if (speedStr.contains("m")) {
            index = speedStr.indexOf("m");
            speed = Integer.parseInt(speedStr.substring(0, index)) * 1_000_000;
        } else {
            speed = Integer.parseInt(speedStr);
        }
        Thread wget = new Thread(new Wget(url, speed));
        wget.start();
        wget.join();
    }
}
