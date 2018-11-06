package br.edu.cefsa.ftt.ec;

import java.util.Random;

/* 
 
www.java2s.com 

Fonte: http://www.java2s.com/Tutorials/Java/Java_Thread/0030__Java_Thread_Producer_Consumer.htm

Produtor e consumidor são problemas típicos de sincronização de Threads
onde usamos os métodos wait() e notify().

*/

public class PCMain {
	
  public static void main(String[] args) {
    
	Buffer buffer = new Buffer();
	
	//As duas threads compartilham o mesma classe Buffer
    Producer p = new Producer(buffer);
    Consumer c = new Consumer(buffer);

    p.start();
    c.start();
  }
}

//Producer gera um número aleatório e grava em Buffer
class Producer extends Thread {
  private Buffer buffer;

  public Producer(Buffer buffer) {
    this.buffer = buffer;
  }

  public void run() {
    Random rand = new Random();
    while (true) {
      int n = rand.nextInt();
      buffer.produce(n);
      
    }
  }
}

//Consumer acessa valor armazenado em Buffer
class Consumer extends Thread {
	  private Buffer buffer;

	  public Consumer(Buffer buffer) {
	    this.buffer = buffer;
	  }

	  public void run() {
	    int data;
	    
	    while (true) {
	      data = buffer.consume();
	    }
	  }
	}

//Buffer é um repositório para o valor...
class Buffer {
	
  private int data;
  private boolean empty;

  public Buffer() {
    this.empty = true;
  }

  public synchronized void produce(int newData) {
    while (!this.empty) {
      try {
        this.wait(); //Espera a flag da classe estar disponível para continuar
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    
    this.data = newData;
    this.empty = false;
    this.notify(); //Informa as classes/métodos que estão aguardando para continuar execução...
    
    System.out.println("Produced:" + newData);
  }

  public synchronized int consume() {
	  
    while (this.empty) {
      try {
        this.wait();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    
    this.empty = true;
    this.notify();
    System.out.println("Consumed:" + data);
    return data;
  }
}