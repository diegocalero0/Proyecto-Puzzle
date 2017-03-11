package logica;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Clase donde se realiza todo lo que tiene que ver con solucion automatica
 * @author Diego Calero
 *
 */
public class Resolver {
	
	private int orden[][];
	private int finall[][] = {{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}, {13, 14, 15, 0}};
	private boolean solucionado;
	private int i;
	private int j;
	private ArrayList<Integer> solucion;
	public Resolver(int[][] orden){
		this.orden = orden;
		this.solucionado = false;
	}
	
	/**
	 * Soluciona el puzzle actual
	 * @param profundidad profundida del nodo actual.
	 * @param sol Arraylist de los movimientos realizados
	 * @param actual puzzle actual
	 * @param i coordenada en i del espacio vacio
	 * @param j coordenada en j del espacio vacio
	 */
	public void solucion(int profundidad, ArrayList<Integer> sol, int[][] actual, int i, int j){
		
		int aux[][];
		
		if(esSolucion(actual)){
			solucion = (ArrayList<Integer>)sol.clone();
			solucionado = true;
		}
		
		if( profundidad == 46 )
			return;
		if( verificar(i + 1, j) && !solucionado ){
			aux = obtenerNueva( actual, i, j, i + 1, j );
			sol.add(aux[i][j]);
			solucion(profundidad + 1, sol, aux, i + 1, j);
			sol.remove(sol.size() - 1);
		}
		
		if( verificar(i - 1, j) && !solucionado ){
			aux = obtenerNueva( actual, i, j, i - 1, j );
			sol.add(aux[i][j]);
			solucion(profundidad + 1, sol, aux, i - 1, j);
			sol.remove(sol.size() - 1);
		}
		
		if( verificar(i, j + 1) && !solucionado ){
			aux = obtenerNueva( actual, i, j, i, j + 1 );
			sol.add(aux[i][j]);
			solucion(profundidad + 1, sol, aux, i, j + 1);
			sol.remove(sol.size() - 1);
		}
		
		if( verificar(i, j - 1) && !solucionado ){
			aux = obtenerNueva( actual, i, j, i, j - 1 );
			sol.add(aux[i][j]);
			solucion(profundidad + 1, sol, aux, i, j - 1);
			sol.remove(sol.size() - 1);
		}
	}
	
	/**
	 * Obtiene una nueva matriz
	 * @param actual la matriz actual
	 * @param i coordenada en i de la posicion vacia
	 * @param j coordenada en j de la posicion vacia
	 * @param inuevo coordenada en i nueva de la posicion vacia
	 * @param jnuevo coordenada en jnueva de la posicion vacia
	 * @return nueva matriz
	 */
	public int[][] obtenerNueva(int[][] actual, int i, int j, int inuevo, int jnuevo){
		int[][] res = new int[4][4];
		
		for (int k = 0; k < res.length; k++) {
			for (int k2 = 0; k2 < res.length; k2++) {
				res[k][k2] = actual[k][k2];
			}
		}
		
		int aux = res[i][j];
		res[i][j] = res[inuevo][jnuevo];
		res[inuevo][jnuevo] = aux;
		
		return res;
		
		
		
	}
	
	/**
	 * Verifica si es solucion el estado actual
	 * @param actual la matriz actual
	 * @return true si es solucion
	 */
	public boolean esSolucion(int[][] actual){
		for (int i = 0; i < actual.length; i++) {
			for (int j = 0; j < actual.length; j++) {
				if(actual[i][j] != finall[i][j])
					return false;
			}
		}
		return true;
	}
	
	/**
	 * Verifica si las posiciones son validas
	 * @param i fila a verificar
	 * @param j columna a verificar
	 * @return true si es valido
	 */
	public boolean verificar(int i, int j){
		return i >= 0 && i < 4 && j >= 0 && j < 4;
	}

	/**
	 * @return the orden
	 */
	public int[][] getOrden() {
		return orden;
	}

	/**
	 * @param orden the orden to set
	 */
	public void setOrden(int[][] orden) {
		this.orden = orden;
	}

	/**
	 * @return the finall
	 */
	public int[][] getFinall() {
		return finall;
	}

	/**
	 * @param finall the finall to set
	 */
	public void setFinall(int[][] finall) {
		this.finall = finall;
	}

	/**
	 * @return the solucionado
	 */
	public boolean isSolucionado() {
		return solucionado;
	}

	/**
	 * @param solucionado the solucionado to set
	 */
	public void setSolucionado(boolean solucionado) {
		this.solucionado = solucionado;
	}

	/**
	 * @return the i
	 */
	public int getI() {
		return i;
	}

	/**
	 * @param i the i to set
	 */
	public void setI(int i) {
		this.i = i;
	}

	/**
	 * @return the j
	 */
	public int getJ() {
		return j;
	}

	/**
	 * @param j the j to set
	 */
	public void setJ(int j) {
		this.j = j;
	}

	/**
	 * @return the solucion
	 */
	public ArrayList<Integer> getSolucion() {
		Stack<Integer> sol = new Stack<Integer>();
		sol.push(solucion.get(0));
		for (int i = 1; i < solucion.size(); i++) {
			if(!sol.isEmpty() && sol.peek() == solucion.get(i))
				sol.pop();
			else
				sol.push(solucion.get(i));
		}
		
		ArrayList<Integer> def = new ArrayList<Integer>();
		while(!sol.isEmpty()){
			def.add(sol.pop());
		}
		
		return def;
	}

	/**
	 * @param solucion the solucion to set
	 */
	public void setSolucion(ArrayList<Integer> solucion) {
		this.solucion = solucion;
	}
	
	
}
