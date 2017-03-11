package interfaz;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import logica.EscritorLector;
import logica.Juego;

/**
 * Ventana principal de la aplicacion
 * @author Diego Calero
 *
 */
public class VentanaCreacion extends Ventana implements ActionListener{

	private static final long serialVersionUID = 3L;
	private JButton btn_guardar, btn_revolver, btn_volver;
	private VentanaPrincipal vp;
	private int mov;
	/**
	 * Constructor de la clase Ventana
	 * @param vp la ventana prinicpal
	 * @param juego el juego donde se creara el puzzle
	 */
	public VentanaCreacion(VentanaPrincipal vp,Juego juego){
		super("Crear puzzle",juego);
		this.vp = vp;
		mov = 1;
		for (int i = 0; i < matriz_lbl.length; i++) {
			for (int j = 0; j < matriz_lbl.length; j++) {
				System.out.print(juego.getMatriz()[i][j]+" ");
			}
			System.out.println();
		}
		System.out.println();
		
		init();
	}
	
	/**
	 * Inicializa todos los componentes de este JFrame
	 */
	private void init() {
		this.setSize(80*4+8,80*4+110);
		this.setLocationRelativeTo(null);
		btn_volver = new JButton();
		btn_guardar = new JButton();
		btn_revolver = new JButton();
		pnl_juego = new JPanel();
		pnl_botones = new JPanel();
		
		pnl_juego.setSize(80 * 4 + 10, 80 * 4 + 2);
		pnl_juego.setLocation(0, 0);
		pnl_juego.setBackground(new Color(0, 0, 0));
		pnl_botones.setSize(80 * 4 + 10, this.getHeight() - 80 * 4);
		pnl_botones.setLocation(0, 80 * 4 +2);
		pnl_botones.setBackground(new Color(176,192,222));
		pnl_botones.setLayout(null);
		pnl_juego.setLayout(null);
		
		add(pnl_botones);
		add(pnl_juego);
		
		btn_volver.setBounds(80, 20, 40, 40);
		pnl_botones.add(btn_volver);
		btn_guardar.setBounds(200, 20, 40, 40);
		pnl_botones.add(btn_guardar);
		btn_revolver.setBounds(140, 20, 40, 40);
		pnl_botones.add(btn_revolver);
		
		btn_volver.setIcon(new ImageIcon(Object.class.getResource(PATH+"principal.png")));
		btn_guardar.setIcon(new ImageIcon(Object.class.getResource(PATH+"guardar.png")));
		btn_revolver.setIcon(new ImageIcon(Object.class.getResource(PATH+"revolver.png")));
		
		btn_guardar.addActionListener(this);
		btn_revolver.addActionListener(this);
		btn_volver.addActionListener(this);
		
		crearMatriz();
		juego.setJugando(true);
	}
	
	/**
	 * actionPerformed para los botones
	 * @param e el evento
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == btn_revolver){
			if(mov++ <= 3)
				juego.revolver();
			else
				JOptionPane.showMessageDialog(null, "No es posible desorganizarlo mas");
		}
		
		if(e.getSource() == btn_guardar){
			chooser.setCurrentDirectory(new File("/"));
			int a = chooser.showSaveDialog(this);
			
			if(a == 0){
				File archivo = chooser.getSelectedFile();
				EscritorLector.escribir(archivo+".txt", juego);
			}
			
		}
		
		if(e.getSource() == btn_volver){
			vp.setVisible(true);
			vp.setLocationRelativeTo(null);
			juego.setJugando(false);
			this.setVisible(false);
			this.dispose();
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
	 * @return the btn_revolver
	 */
	public JButton getBtn_revolver() {
		return btn_revolver;
	}

	/**
	 * @param btn_revolver the btn_revolver to set
	 */
	public void setBtn_revolver(JButton btn_revolver) {
		this.btn_revolver = btn_revolver;
	}
}