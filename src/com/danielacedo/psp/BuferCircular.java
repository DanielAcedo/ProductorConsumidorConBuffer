package com.danielacedo.psp;

public class BuferCircular implements IBufer{

	private int bufer[] = {-1, -1, -1};
	private int contadorOcupado = 0;
	private int posLectura = 0;
	private int posEscritura = 0;
	
	@Override
	public synchronized void escribir(int valor) {
		String hiloLlamador = Thread.currentThread().getName();
		
		while(contadorOcupado == bufer.length){			
			try {
				System.out.println(hiloLlamador+" trata de escribir");
				mostrarEstado("Buffer lleno "+hiloLlamador+" espera");
				mostrarSalida();
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		bufer[posEscritura] = valor;
		contadorOcupado++;
		posEscritura = (posEscritura + 1) % bufer.length;
		mostrarEstado(hiloLlamador+" consigue escribir "+valor);
		mostrarSalida();
		
		notify();
	}
	
	@Override
	public synchronized void mostrarSalida(){
		String salida = "(huecos ocupados: "+contadorOcupado+")\nhuecos: ";
		
		salida+="\n";
		
		for(int i = 0; i < bufer.length; i++){
			salida+=" "+bufer[i]+"  ";
		}
		
		salida+="\n";
		
		for(int i = 0; i < bufer.length; i++){
			salida+="--- ";
		}
		
		salida+="\n";
		
		for(int i = 0; i < bufer.length; i++){
			if( i == posEscritura && posEscritura == posLectura){
				salida+="EL  ";
			}
			else if (i == posEscritura){
				salida+="E  ";
			}
			else if (i == posLectura){
				salida+="L  ";
			}
			else{
				salida+="   ";
			}
			
		}
		
		System.out.println(salida);
		
	}

	@Override
	public synchronized int leer() {

		String hiloLlamador = Thread.currentThread().getName();
		int valor;
		

		while(contadorOcupado == 0){			
			try {
				System.out.println(hiloLlamador+" trata de leer");
				mostrarEstado("Buffer vacio "+hiloLlamador+" espera");
				mostrarSalida();
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		valor = bufer[posLectura];
		contadorOcupado--;
		posLectura = (posLectura + 1) % bufer.length;
		mostrarEstado(hiloLlamador+" consigue leer "+valor);
		mostrarSalida();
		notify();
		
		return valor;
	}

	@Override
	public void mostrarEstado(String cadena) {
		System.out.println("Estado: \n"+cadena);
	}

}
