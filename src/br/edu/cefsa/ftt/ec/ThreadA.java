package br.edu.cefsa.ftt.ec;

//Fonte: https://www.devmedia.com.br/java-threads-utilizando-wait-notify-e-notifyall/29545

public class ThreadA {
	  
     public static void main(String[] args){
     
     ThreadB b = new ThreadB();
     b.start();

     synchronized(b){
    	 
         try{
             System.out.println("Aguardando o b completar...");
             b.wait();
         } catch(InterruptedException e){
             e.printStackTrace();
         } //try
         
         System.out.println("Total é igual a: " + b.getTotal());
     } //synchronized
     
 } //main
     
} //ThreadA

class ThreadB extends Thread {

        private int total;
        
        @Override
        public void run(){
            synchronized(this){
            	
                for(int i=0; i < 1200 ; i++){
                    this.total += i;
                } //for
                
                notify();
            
            } //syncronized
            
        } //run
        
        public synchronized int getTotal( ) {
        	return this.total;
        } //getTotal
        
} //ThreadB