package controller;

import java.util.concurrent.Semaphore;

public class CorridorThread extends Thread {
    private static Semaphore semaphore;
    private int distance = 0;
    private String name;

    public CorridorThread (Semaphore semaphore)
    {
        CorridorThread.semaphore = semaphore;
        this.name = "#" + this.threadId();
    }

    private void rush ()
    {
        while (this.distance < 200)
        {
            int ms = (int)((Math.random() * 6) + (6 - 4));

            if (this.distance < 200 && this.distance + ms < 200)
            {
                System.out.println(this.name + " percorreu " + ms + "m " + "e andou um total de " + this.distance + "m");
                this.distance += ms;
            }
            else
            {
                try
                {
                    System.out.println(this.name + " chegou à porta");

                    semaphore.acquire();

                    System.out.println(this.name + " está abrindo a porta...");
                    sleep((int)((Math.random() * 1000) + (2000 - 1000)));
                    System.out.println(this.name + " saiu!");
                }
                catch (Exception ex)
                {
                    System.err.println(ex.getMessage());
                }
                finally
                {
                    semaphore.release();
                }
                return;
            }
        }
    }

    @Override
    public void run ()
    {
        rush();
    }
}
