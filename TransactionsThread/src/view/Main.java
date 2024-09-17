package view;

import controller.TransactionThread;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class Main {
    public static void main (String[] args)
    {
        Semaphore semaphore = new Semaphore(2);
        Random rnd = new Random();

        for (int x = 0; x < 20; x++)
        {
            Thread transaction = new TransactionThread(semaphore, rnd.nextInt(2), rnd.nextInt(1000, 6000));
            transaction.start();
        }
    }
}
