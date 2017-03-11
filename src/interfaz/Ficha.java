package interfaz;

import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.Serializable;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import logica.Juego;
import logica.Pareja;

/**
 * Clase ficha apartir de esta clase creamos las 15 fichas
 * @author Diego Calero
 *
 */
public class Ficha extends JLabel implements MouseListener, Serializable{


	private static final long serialVersionUID = 1L;
	private static final String PATH="/recursos/";
	private static final int ANCHO = 80;
	private static final int ALTO = 80;
	private int fila, columna;
	private Dimension d;
	private Juego juego;
	private Ventana ventana;
	private int i, j;
	private int id;
	private int ianterior;
	private int janterior;
	
	/**
	 * Constructor de la clase ficha
	 * @param id el id de la ficha
	 * @param juego el juego donde está la ficha
	 * @param ventana la ventana donde está la ficha
	 */
	public Ficha(int id, Juego juego, Ventana ventana) {
		
		d = new Dimension(ANCHO, ALTO);
		this.juego = juego;
		this.id = id;
		this.ventana = ventana;
		this.setSize(78,78);
		this.setPreferredSize(d);
		this.setIcon(new ImageIcon(Object.class.getResource(PATH + id + ".png")));
		this.setText("");
		this.setVisible(true);
		this.addMouseListener(this);
		if(id == 0)
			this.setIcon(null);
	}
	
	/**
	 * Mueve la ficha si es posible
	 * @return true si se realizó el movimiento
	 */
	public boolean mover(){
		if(!ventana.getJuego().isJugando())
			return false;
		int mov = juego.verificar(i, j);
		int aux = juego.getMatriz()[i][j];
		if(mov != -1){
			ianterior = i;
			janterior = j;
			switch(mov){
			case Juego.ABAJO:
				i++;
				break;
			case Juego.ARRIBA:
				i--;
				break;
			case Juego.DERECHA:
				j++;
				break;
			case Juego.IZQUIERDA:
				j--;
				break;
			}
			
			juego.getMatriz()[i][j] = aux;
			juego.getMatriz()[ianterior][janterior] = 0;
			Ficha f = ventana.getMatriz_lbl()[i][j];
			ventana.getMatriz_lbl()[i][j] = this;
			ventana.getMatriz_lbl()[ianterior][janterior] = f;
			ventana.getMatriz_lbl()[ianterior][janterior].setI(ianterior);
			ventana.getMatriz_lbl()[ianterior][janterior].setJ(janterior);
			this.setLocation(j * 80 + 2, i * 80 + 2);
			ventana.getMatriz_lbl()[ianterior][janterior].setLocation(janterior * 80 + 2, ianterior * 80 + 2);
			
			if(ventana instanceof VentanaPrincipal){
				juego.getMovimientos().push(new Pareja<Integer, Integer>(i, j));
				juego.getDeshechos().clear();
				juego.setPasos(juego.getPasos() + 1);
				( (VentanaPrincipal)ventana ).getLbl_movimientos().setText("Movimientos: "+juego.getPasos());
				( (VentanaPrincipal)ventana ).getLst_model().addElement(this.id);
				( (VentanaPrincipal)ventana ).verificar();
			}
			
			return true;
			
		}
		return false;
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		mover();
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		int x = this.getX();
		int y = this.getY();
		i = y/80;
		j = x/80;
	}
	
	/**
	 * @return the fila
	 */
	public int getFila() {
		return fila;
	}



	/**
	 * @param fila the fila to set
	 */
	public void setFila(int fila) {
		this.fila = fila;
	}



	/**
	 * @return the columna
	 */
	public int getColumna() {
		return columna;
	}



	/**
	 * @param columna the columna to set
	 */
	public void setColumna(int columna) {
		this.columna = columna;
	}



	/**
	 * @return the d
	 */
	public Dimension getD() {
		return d;
	}



	/**
	 * @param d the d to set
	 */
	public void setD(Dimension d) {
		this.d = d;
	}



	/**
	 * @return the path
	 */
	public static String getPath() {
		return PATH;
	}



	/**
	 * @return the ancho
	 */
	public static int getAncho() {
		return ANCHO;
	}



	/**
	 * @return the alto
	 */
	public static int getAlto() {
		return ALTO;
	}



	

	/**
	 * @return the juego
	 */
	public Juego getJuego() {
		return juego;
	}



	/**
	 * @param juego the juego to set
	 */
	public void setJuego(Juego juego) {
		this.juego = juego;
	}



	/**
	 * @return the ventana
	 */
	public Ventana getVentana() {
		return ventana;
	}



	/**
	 * @param ventana the ventana to set
	 */
	public void setVentana(Ventana ventana) {
		this.ventana = ventana;
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
	 * @return the id
	 */
	public int getId() {
		return id;
	}



	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}



	/**
	 * @return the ianterior
	 */
	public int getIanterior() {
		return ianterior;
	}



	/**
	 * @param ianterior the ianterior to set
	 */
	public void setIanterior(int ianterior) {
		this.ianterior = ianterior;
	}



	/**
	 * @return the janterior
	 */
	public int getJanterior() {
		return janterior;
	}



	/**
	 * @param janterior the janterior to set
	 */
	public void setJanterior(int janterior) {
		this.janterior = janterior;
	}
	
	

	

}
