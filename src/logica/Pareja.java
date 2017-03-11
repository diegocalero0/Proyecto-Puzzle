package logica;

import java.io.Serializable;

/**
 * Estructura que se compone de dos valores
 * @author Diego Calero
 *
 * @param <T> primer elemento de la pareja
 * @param <H> segundo elemento de la pareja
 */
public class Pareja<T,H> implements Serializable{
	
	private static final long serialVersionUID = 7L;
	private T i;
	private H j;
	
	/**
	 * Constructor de la clase Pareja
	 * @param i el primer valor
	 * @param j el segundo valor
	 */
	public Pareja(T i, H j){
		this.i = i;
		this.j = j;
	}

	/**
	 * Metodo toString para usar en el JList
	 */
	public String toString(){
		if(j instanceof Integer && (Integer)j == -1)
			return "PUZZLE "+i;
		return i+": "+j;
	}
	
	/**
	 * @return the i
	 */
	public T getI() {
		return i;
	}

	/**
	 * @param i the i to set
	 */
	public void setI(T i) {
		this.i = i;
	}

	/**
	 * @return the j
	 */
	public H getJ() {
		return j;
	}

	/**
	 * @param j the j to set
	 */
	public void setJ(H j) {
		this.j = j;
	}
	
	
	
}
