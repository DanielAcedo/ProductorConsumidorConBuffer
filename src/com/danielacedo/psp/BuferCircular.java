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
		mostrarSalida();
		mostrarEstado(hiloLlamador+" consigue escribir "+valor);
		
		notify();
	}
	
	@Override
	public synchronized void mostrarSalida(){
		for(int i = 0; i < bufer.length; i++){
			System.out.println(bufer[i]+"--");
		}
	}

	//TODO HACERLO
	@Override
	public int leer() {

		String hiloLlamador = Thread.currentThread().getName();
		int valor;
		

		while(contadorOcupado == bufer.length){			
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
		mostrarSalida();
		mostrarEstado(hiloLlamador+" consigue leer "+valor);
		
		notify();
		
		return valor;
	}

	@Override
	public void mostrarEstado(String cadena) {
		System.out.println("Estado: \n"+cadena);
	}

}
