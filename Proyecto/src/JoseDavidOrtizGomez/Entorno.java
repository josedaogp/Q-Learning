package JoseDavidOrtizGomez;

import java.util.ArrayList;

import core.game.Observation;
import core.game.StateObservation;

public class Entorno { // todos los obstáculos tienen el mismo número

	private int mapa[][];
	private StateObservation Ob;
	private int xPelota = -1;
	private int yPelota = -1;
	
	private int IzBandeja = -1;

	private int CenBandeja = -1;
	
	private int DrBandeja = -1;
	
	public Entorno(StateObservation Ob) {
		this.Ob = Ob;
		ArrayList<Observation>[][] M = Ob.getObservationGrid();
		mapa = new int[M.length][M[1].length];

		for (int i = 0; i < mapa.length; i++) {
			for (int j = 0; j < mapa[0].length; j++) {
				if (M[i][j].size() == 0) {// espacios en blanco
					if (mapa[i][j] != 7 && mapa[i][j] != 8 && mapa[i][j] != 2) {
						mapa[i][j] = 1;
					}

				} else {
					if (M[i][j].get(0).itype == 8) { // bloques verdes grandes
						mapa[i][j] = 8;
						mapa[i + 1][j] = 8;
					} else if (M[i][j].get(0).itype == 0) { // paredes
						mapa[i][j] = 3;
					} else if (M[i][j].get(0).itype == 5) { // pelota
						mapa[i][j] = 5;
						xPelota = i;
						yPelota = j;
					} else if (M[i][j].get(0).itype == 9) { // bloque verde pequeño
						mapa[i][j] = 9;
					} else if (M[i][j].get(0).itype == 1) { // bandeja
						mapa[i][j] = 2;
						IzBandeja=i;
						
						CenBandeja=i+1;
						
						DrBandeja=i+3;
						mapa[i + 1][j] = 2;
						mapa[i + 2][j] = 2;
						mapa[i + 3][j] = 2;
					} else if (M[i][j].get(0).itype == 11) { // bloques grises
						mapa[i][j] = 7;
						mapa[i + 1][j] = 7;
					} else {// se le asigna el tipo
						mapa[i][j] = M[i][j].get(0).itype;
					}
				}

			}
		}

	}

	public void verDimensiones() {
		System.out.println("El número de filas es: " + mapa.length);
		System.out.println("El número de columnas es: " + mapa[0].length);
	}

	public void verMapa() {

		for (int j = 0; j < mapa[1].length; j++) {
			System.out.print("\n");
			for (int i = 0; i < mapa.length; i++) {
				System.out.print(mapa[i][j] + "  ");
			}

		}
		System.out.print("\n Fin \n\n");
	}

	public static int[] getPosAvatar(StateObservation Ob) {

		// Calculamos la posicion del avatar
		int tB = Ob.getBlockSize();// cuantos pixeles tiene cada bloque
		tools.Vector2d v = Ob.getAvatarPosition();
		int[] A = { (int) v.x / tB, (int) v.y / tB };// Posicion del avatar

		return A;
	}
	
	
	
	public int getIzBandeja() {
		return IzBandeja;
	}

	public void setIzBandeja(int izBandeja) {
		IzBandeja = izBandeja;
	}

	public int getCenBandeja() {
		return CenBandeja;
	}

	public void setCenBandeja(int cenBandeja) {
		CenBandeja = cenBandeja;
	}

	public int getDrBandeja() {
		return DrBandeja;
	}

	public void setDrBandeja(int drBandeja) {
		DrBandeja = drBandeja;
	}

	
	public int getPosPelota() {
		int[]A = getPosAvatar(Ob);
		int i = A[0];
		int j = A[1];
		
		//0 izquierda, 1 centro, 2 derecha, -1 no hay pelota
		if (xPelota == -1) //no hay pelota
			return -1;
		if (i == xPelota) { //si está en la misma columna
			return 1;
		} else {
			if (xPelota < i)
				return 0; //si la pelota está a la izquierda
			else
				return 2; //sino, está a la derecha
		}

	}
	
	public int getPosPelota(int i, int j) {
		//0 izquierda, 1 centro, 2 derecha, -1 no hay pelota
		if (xPelota == -1) //no hay pelota
			return -1;
		if (i == xPelota) { //si está en la misma columna
			return 1;
		} else {
			if (xPelota < i)
				return 0; //si la pelota está a la izquierda
			else
				return 2; //sino, está a la derecha
		}

	}

}