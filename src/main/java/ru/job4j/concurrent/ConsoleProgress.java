package ru.job4j.concurrent;

public class ConsoleProgress implements Runnable {
    String[] process = {"－", "\\", "|", "/"};

    @Override
    public void run() {
        int i = 0;
        while (!Thread.currentThread().isInterrupted()) {
            try {
                System.out.print("\rLoading... " + process[i]);
                Thread.sleep(500);
                if (i++ == 3) {
                    i = 0;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        Thread.sleep(1000);
        progress.interrupt();
    }
}
