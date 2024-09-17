package view;

import controller.SemaphoreCrossroad;

import java.util.concurrent.Semaphore;

public class Main {
    public static void main (String[] args)
    {
        Semaphore semaphore = new Semaphore(1);

        for (int x = 0; x < 4; x++)
        {
            SemaphoreCrossroad carro = new SemaphoreCrossroad(semaphore, x);
            carro.start();
        }
    }
}
