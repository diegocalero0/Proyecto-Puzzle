package interfaz;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import logica.Juego;
import logica.Pareja;

/**
 * Ventana donde se muestran los puntajes
 * @author Diego Calero
 *
 */
public class VentanaPuntajes extends JFrame implements ActionListener, Serializable{

	private static final long serialVersionUID = 8L;
	private JScrollPane scroll;
	private JList<Pareja<String, Integer>> lst_puntajes;
	private DefaultListModel<Pareja<String, Integer>> lst_modelo;
	private Juego juego;
	private JButton btn_volver;
	private JLabel lbl_titulo;
	public static final String PATH="/recursos/";
	
	public VentanaPuntajes(Juego juego){
		this.juego = juego;
		init();
	}
	
	/**
	 * Inicializa los componentes de esta ventana
	 */
	public void init(){
		this.getContentPane().setBackground(new Color(19, 136, 149));
		this.setSize(180, juego.getVentana().getHeight());
		this.setUndecorated(true);
		this.setLayout(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		lst_puntajes = new JList<Pareja<String, Integer>>();
		lst_modelo = new DefaultListModel<>();
		lst_puntajes.setModel(lst_modelo);
		lst_puntajes.setBounds(5, 30, this.getWidth() - 10, this.getHeight() - 70);
		scroll = new JScrollPane(lst_puntajes);
		scroll.setBounds(5, 30, lst_puntajes.getWidth() , lst_puntajes.getHeight());
		
		btn_volver = new JButton();
		btn_volver.setBounds(5, 515, scroll.getWidth(), 30);
		btn_volver.setIcon(new ImageIcon(getClass().getResource(PATH+"ocultar.png")));
		btn_volver.addActionListener(this);
		
		lbl_titulo = new JLabel("Puntajes");
		lbl_titulo.setBounds(50, 5, scroll.getHeight(), 20);
		Font fuente = new Font("Arial", Font.BOLD, 16);
		lbl_titulo.setFont(fuente);
		lbl_titulo.setForeground(Color.WHITE);
		
		this.add(scroll);
		this.add(btn_volver);
		this.add(lbl_titulo);
		
		
	}

	/**
	 * Actualiza el Jlist en caso de que haya algun cambio
	 */
	public void actualizar(){
		lst_modelo.clear();
		
		for(ArrayList<Pareja<String, Integer>> aux: juego.getPuntajes().values()){
			for (int i = 0; i < aux.size() && i < 6; i++) {
				lst_modelo.addElement(aux.get(i));
			}
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btn_volver){
			this.setVisible(false);
		}
	}
	
}
