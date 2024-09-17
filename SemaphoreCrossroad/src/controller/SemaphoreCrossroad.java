package controller;

import java.util.concurrent.Semaphore;

public class SemaphoreCrossroad extends Thread {
    private Semaphore semaphore;
    private String name;
    private int direction;

    public SemaphoreCrossroad (Semaphore semaphore, int direction)
    {
        this.semaphore = semaphore;
        this.name = "O carro " + threadId();
        this.direction = direction;
    }

    private void move()
    {
        System.out.println(this.name + " está se movendo para " + parsePosition());
    }

    private void cross ()
    {
        try
        {
            this.semaphore.acquire();
            System.out.println(this.name + " está atravessando para " + this.parsePosition());
            System.out.println(this.name + " terminou a travessia!");
        }
        catch (Exception ex)
        {
            System.err.println(ex.getMessage());
        }
        finally
        {
            this.semaphore.release();
        }
    }

    private String parsePosition ()
    {
        return switch (this.direction) {
            case 0 -> "Baixo";
            case 1 -> "Esquerda";
            case 2 -> "Cima";
            case 3 -> "Direita";
            default -> "";
        };
    }

    @Override
    public void run ()
    {
        move();
        cross();
    }
}
