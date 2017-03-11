package interfaz;

import java.io.Serializable;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import logica.Juego;

/**
 * Clase Ventana, Super clase de VentanaPrincipal y VentanaCreacion
 * @author Diego Calero
 *
 */
public class Ventana extends JFrame implements Serializable{
	
	private static final long serialVersionUID = 2L;
	protected JButton btn_guardar, btn_mezclar;
	protected Ficha[][] matriz_lbl;
	protected JPanel pnl_juego, pnl_botones;
	protected JFileChooser chooser;
	public static final String PATH="/recursos/";
	public static final String PUZZLES="puzzles/";
	public static final String JUEGOS="juegos/";
	protected Juego juego;
	
	public Ventana(String titulo,Juego juego){
		this.juego = juego;
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setLayout(null);
		this.setTitle(titulo);
		chooser = new JFileChooser();
		matriz_lbl = new Ficha[4][4];
		btn_guardar = new JButton();
		pnl_juego = new JPanel();
		pnl_botones = new JPanel();
		
		
	}
	
	/**
	 * Crea la matriz de Labes
	 */
	public void crearMatriz(){
		matriz_lbl = new Ficha[4][4];
		int x=80;
		int k=1;
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
					if(k == 16)
						k = 0;
					matriz_lbl[i][j] = new Ficha(k++, juego, this);
					matriz_lbl[i][j].setI(i);
					matriz_lbl[i][j].setJ(j);
					pnl_juego.add(matriz_lbl[i][j]);
					matriz_lbl[i][j].setLocation(j*x + 2, i*x + 2);
			}
		}	
	}

	/**
	 * @return the btn_guardar
	 */
	public JButton getBtn_guardar() {
		return btn_guardar;
	}

	/**
	 * @param btn_guardar the btn_guardar to set
	 */
	public void setBtn_guardar(JButton btn_guardar) {
		this.btn_guardar = btn_guardar;
	}

	/**
	 * @return the btn_mezclar
	 */
	public JButton getBtn_mezclar() {
		return btn_mezclar;
	}

	/**
	 * @param btn_mezclar the btn_mezclar to set
	 */
	public void setBtn_mezclar(JButton btn_mezclar) {
		this.btn_mezclar = btn_mezclar;
	}

	/**
	 * @return the matriz_lbl
	 */
	public Ficha[][] getMatriz_lbl() {
		return matriz_lbl;
	}

	/**
	 * @param matriz_lbl the matriz_lbl to set
	 */
	public void setMatriz_lbl(Ficha[][] matriz_lbl) {
		this.matriz_lbl = matriz_lbl;
	}

	/**
	 * @return the pnl_juego
	 */
	public JPanel getPnl_juego() {
		return pnl_juego;
	}

	/**
	 * @param pnl_juego the pnl_juego to set
	 */
	public void setPnl_juego(JPanel pnl_juego) {
		this.pnl_juego = pnl_juego;
	}

	/**
	 * @return the pnl_botones
	 */
	public JPanel getPnl_botones() {
		return pnl_botones;
	}

	/**
	 * @param pnl_botones the pnl_botones to set
	 */
	public void setPnl_botones(JPanel pnl_botones) {
		this.pnl_botones = pnl_botones;
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

}
