package view;

import controller.CorridorThread;

import java.util.concurrent.Semaphore;

public class Main {
    public static void main(String[] args)
    {
        Semaphore semaphore = new Semaphore(1);

        for (int x = 0; x < 4; x++)
        {
            Thread runner = new CorridorThread(semaphore);
            runner.start();
        }
    }
}
