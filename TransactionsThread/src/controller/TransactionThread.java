package controller;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Semaphore;

public class TransactionThread extends Thread{

    private static Semaphore semaphore;
    private static Map<Integer, Boolean> activeTransaction = new HashMap<>();
    private String accountCode;
    private int assets;
    private int value;
    private int type;

    public TransactionThread (Semaphore semaphore, int type, int value)
    {
        TransactionThread.semaphore = semaphore;
        this.value = value;
        this.type = type;
        this.assets = (int)((Math.random() * 1000) + (6000 - 1000));
        this.accountCode = "#" + this.threadId();
        TransactionThread.activeTransaction.put(0, false);
        TransactionThread.activeTransaction.put(1, false);
    }

    private void transactions ()
    {
        System.out.println(this.accountCode + " vai realizar um " + (this.type == 0 ? "saque" : "depósito"));
        System.out.println(this.accountCode + " possui " + this.assets);
        if (type == 0)
        {
            try
            {
                if (value < this.assets)
                {
                    if (TransactionThread.activeTransaction.get(0))
                    {
                        System.out.println(this.accountCode + " está esperando para sacar...");
                        sleep(1000);
                    }

                    semaphore.acquire(1);
                    TransactionThread.activeTransaction.replace(0, true);
                    System.out.println(this.accountCode + " está sacando " + value);
                    this.assets -= value;
                    TransactionThread.activeTransaction.replace(0, false);
                }
                else
                {
                    System.out.println(this.accountCode + " não conseguiu realizar saque por falta de saldo");
                }
            }
            catch (Exception ex)
            {
                System.out.println(ex.getMessage());
            }
            finally
            {
                semaphore.release();
            }
        }
        else
        {
            try
            {
                if (TransactionThread.activeTransaction.get(1))
                {
                    System.out.println(this.accountCode + " está esperando para depositar...");
                    sleep(1000);
                }

                TransactionThread.activeTransaction.replace(1, true);
                semaphore.acquire(1);
                System.out.println(this.accountCode + " está depositando " + value);
                this.assets += value;
                TransactionThread.activeTransaction.replace(1, false);
            }
            catch (Exception ex)
            {
                System.out.println(ex.getMessage());
            }
            finally
            {
                semaphore.release();
            }
        }
    }

    @Override
    public void run ()
    {
        transactions();
    }
}
