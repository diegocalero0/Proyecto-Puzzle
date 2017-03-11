package logica;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;
import interfaz.VentanaCreacion;
import interfaz.VentanaPrincipal;
import interfaz.VentanaPuntajes;

/**
 * Clase juego donde se realizan procesos logicos de la aplicacion
 * @author Diego Calero
 *
 */
public class Juego implements Serializable{
	
	private static final long serialVersionUID = 6L;
	public final static int DERECHA = 1, IZQUIERDA = 2, ARRIBA = 3, ABAJO = 4;
	private int matriz[][];
	private Stack<Pareja<Integer, Integer>> movimientos;
	private Stack<Pareja<Integer, Integer>> deshechos;
	private boolean jugando;
	private VentanaPrincipal ventana;
	private VentanaCreacion ventanaC;
	private int pasos;
	private HashMap<String, ArrayList< Pareja<String, Integer> >> puntajes;
	/**
	 * Constructor de la clase juego
	 */
	public Juego(){
		this.ventana = new VentanaPrincipal(this);
		ventana.setVisible(true);
		ventana.setVentana_puntajes(new VentanaPuntajes(this));
		jugando = false;
		matriz  = new int[4][4];
		movimientos = new Stack< Pareja< Integer, Integer > >();
		deshechos = new Stack< Pareja< Integer, Integer > >();
		pasos = 0;
		File archivo = ventana.cargar();
		if(archivo != null){
			ventana.setGuardadoPorPrimeraVez(true);
			ventana.setPuntajes(archivo);
		}
			
		System.out.println(archivo);
		EscritorLector.deserializarPuntajes(this, archivo);
		ventana.getVentana_puntajes().actualizar();
	}
	
	/**
	 * Agrega un jugador despues de finalizar el juego.
	 * @param nombre el nombre del jugador
	 * @param movimientos movimientos que realizo el jugador
	 * @param puzzle nombre del puzzle en el que jugo
	 * @return true si el jugador quedó entre los primeros 5
	 */
	public boolean agregarJugador(String nombre, int movimientos, String puzzle){
		if(!puntajes.containsKey(puzzle)){
			puntajes.put(puzzle, new ArrayList<Pareja<String, Integer>>());
			puntajes.get(puzzle).add(new Pareja<String, Integer>(puzzle, -1));
		}
		
		for (int i = 1; i < 6 && i < puntajes.get(puzzle).size(); i++) {
			if(puntajes.get(puzzle).get(i).getJ() > movimientos){						
				puntajes.get(puzzle).add(i, new Pareja<String, Integer>(nombre, movimientos));
				return true;
			}
		}
		
		if(puntajes.get(puzzle).size() < 6){
			puntajes.get(puzzle).add(new Pareja<String, Integer>(nombre, movimientos));
			return true;
		}
		
		return false;
		
	}
	
	/**
	 * Inicializa la matriz con numeros del 0 al 15
	 */
	public void inicializar(){
		matriz[3][3] = 0;
		int i, j, k = 1;
		for(i = 0; i < 4; i++){
			for (j = 0; j < 4; j++) {
				if(k != 16)
					matriz[i][j] = k++;
			}
		}
	}
	
	/**
	 * Revuelve los numeros en la matriz
	 */
	public void revolver(){
		int i, j, k = 0;
		
		do{
			i = (int)(Math.random() * 4);
			j = (int)(Math.random() * 4);
			
			if(ventanaC.getMatriz_lbl()[i][j].mover()){
				k++;
			}
		}while( k < 15 );
	}

	/**
	 * Verifica si la ficha seleccionada se puede mover
	 * @param i la coordenada en i de la ficha
	 * @param j la coordenada en j de la ficha 
	 * @return la direccion a la cual se puede mover
	 */
	public int verificar(int i, int j){
		if(matriz[i][j] == 0)
			return -1;
		else{
			if(i + 1 < 4 && matriz[i+1][j] == 0)
				return ABAJO;
			if(i - 1 >= 0 && matriz[i-1][j] == 0)
				return ARRIBA;
			if(j + 1 < 4 && matriz[i][j+1] == 0)
				return DERECHA;
			if(j - 1 >= 0 && matriz[i][j-1] == 0)
				return IZQUIERDA;
			return -1;
		}
	}
	
	/**
	 * Revisa si el juego está resuelto
	 * @return true si el juego a sido resuelto correctamente
	 */
	public boolean resuelto(){
		int k = 0;
		for (int i = 0; i < matriz.length; i++) {
			for (int j = 0; j < matriz.length; j++) {
				if(i == 3 && j == 3){
					return true;
				}
				if( matriz[i][j] - k++ != 1){
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * @return the matriz
	 */
	public int[][] getMatriz() {
		return matriz;
	}

	/**
	 * @param matriz the matriz to set
	 */
	public void setMatriz(int[][] matriz) {
		this.matriz = matriz;
	}

	/**
	 * @return the movimientos
	 */
	public Stack<Pareja<Integer, Integer>> getMovimientos() {
		return movimientos;
	}

	/**
	 * @param movimientos the movimientos to set
	 */
	public void setMovimientos(Stack<Pareja<Integer, Integer>> movimientos) {
		this.movimientos = movimientos;
	}

	/**
	 * @return the derecha
	 */
	public static int getDerecha() {
		return DERECHA;
	}

	/**
	 * @return the izquierda
	 */
	public static int getIzquierda() {
		return IZQUIERDA;
	}

	/**
	 * @return the arriba
	 */
	public static int getArriba() {
		return ARRIBA;
	}

	/**
	 * @return the abajo
	 */
	public static int getAbajo() {
		return ABAJO;
	}

	/**
	 * @return the deshechos
	 */
	public Stack<Pareja<Integer, Integer>> getDeshechos() {
		return deshechos;
	}

	/**
	 * @param deshechos the deshechos to set
	 */
	public void setDeshechos(Stack<Pareja<Integer, Integer>> deshechos) {
		this.deshechos = deshechos;
	}

	/**
	 * @return the jugando
	 */
	public boolean isJugando() {
		return jugando;
	}

	/**
	 * @param jugando the jugando to set
	 */
	public void setJugando(boolean jugando) {
		this.jugando = jugando;
	}

	/**
	 * @return the ventana
	 */
	public VentanaPrincipal getVentana() {
		return ventana;
	}

	/**
	 * @param ventana the ventana to set
	 */
	public void setVentana(VentanaPrincipal ventana) {
		this.ventana = ventana;
	}

	/**
	 * @return the pasos
	 */
	public int getPasos() {
		return pasos;
	}

	/**
	 * @param pasos the pasos to set
	 */
	public void setPasos(int pasos) {
		this.pasos = pasos;
	}

	/**
	 * @return the ventanaC
	 */
	public VentanaCreacion getVentanaC() {
		return ventanaC;
	}

	/**
	 * @param ventanaC the ventanaC to set
	 */
	public void setVentanaC(VentanaCreacion ventanaC) {
		this.ventanaC = ventanaC;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @return the puntajes
	 */
	public HashMap<String, ArrayList< Pareja<String, Integer> >> getPuntajes() {
		return puntajes;
	}

	/**
	 * @param puntajes the puntajes to set
	 */
	public void setPuntajes(HashMap<String, ArrayList< Pareja<String, Integer> >> puntajes) {
		this.puntajes = puntajes;
	}
	
	
	
}
