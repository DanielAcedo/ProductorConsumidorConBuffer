package com.danielacedo.psp;
/**
 * Consumidor
 * @author Daniel Acedo
 *
 */
class Productor extends Thread{
	private IBufer compartido;
	
	public Productor(IBufer bufer){
		super("Productor");
		compartido = bufer;
	}
	
	@Override
	public void run(){
		for (int i = 0; i<10; i++){
			try{
				Thread.sleep((int)Math.random() * 3001);
				compartido.escribir(i);
			}catch(InterruptedException ex){
				
			}
			
		}
		
		System.out.println(getName()+" terminó de producir datos");
	}
}

/**
 * Consumidor
 * @author Daniel Acedo
 *
 */
class Consumidor extends Thread{
	
	private IBufer compartido;
	
	public Consumidor(IBufer bufer){
		super("Consumidor");
		compartido = bufer;
	}
	
	@Override
	public void run(){
		
		int suma = 0;
		
		for (int i = 0; i<20; i++){
			try{
				Thread.sleep((int)Math.random() * 3001);
				suma += compartido.leer();
			}catch(InterruptedException ex){
				
			}
			
		}
		
		System.out.println(getName()+" terminó de consumir datos");
		System.out.println("Suma: "+suma);
	}
}

public class PruebaBufferCircular {

	public static void main(String[] args) {
		IBufer elBuffer = new BuferCircular();
		
		Productor prod = new Productor(elBuffer);
		Consumidor consu = new Consumidor(elBuffer);
		
		prod.start();
		consu.start();
		
	
		try{
			prod.join(); 
			consu.join();
		}catch(InterruptedException ex){
			
		}
		
	}

}
