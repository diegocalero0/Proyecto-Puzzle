package interfaz;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import logica.EscritorLector;
import logica.Juego;
import logica.Pareja;
import logica.Resolver;

/**
 * Ventana principal de la aplicacion
 * @author Diego Calero
 *
 */
public class VentanaPrincipal extends Ventana implements ActionListener, Serializable{

	private static final long serialVersionUID = 4L;
	private JButton btn_deshacer, btn_rehacer, btn_jugar, btn_puntajes, btn_solucionar, btn_crear, btn_cargar;
	private JPanel pnl_movimientos;
	private JLabel lbl_movimientos;
	private JList<Integer> lst_movimientos;
	private DefaultListModel<Integer> lst_model;
	private JScrollPane scroll;
	private VentanaPuntajes ventana_puntajes;
	private String puzzle;
	private File puntajes;
	private boolean guardadoPorPrimeraVez;
	
	/**
	 * Constructor de la clase Ventana
	 * @param juego juego principal
	 */
	public VentanaPrincipal(Juego juego){
		super("Ventana Principal",juego);
		guardadoPorPrimeraVez = false;
		init();
	}
	
	/**
	 * Inicializa todos los componentes de este JFrame
	 */
	private void init() {
		this.setSize(80*4+208,80*4+230);
		this.setLocationRelativeTo(null);
		pnl_movimientos = new JPanel();
		btn_deshacer = new JButton();
		btn_rehacer = new JButton();
		btn_jugar = new JButton();
		btn_puntajes = new JButton();
		btn_solucionar = new JButton();
		btn_crear = new JButton();
		btn_cargar = new JButton();
		
		pnl_juego.setSize(80 * 4 + 2, 80 * 4 + 2);
		pnl_juego.setLocation(0, 0);
		pnl_juego.setBackground(new Color(0, 0, 0));
		pnl_botones.setSize(80 * 4 + 2, this.getHeight() - 80 * 4);
		pnl_botones.setLocation(0, 80 * 4 +2);
		pnl_botones.setBackground(new Color(176,192,222));
		pnl_botones.setLayout(null);
		pnl_juego.setLayout(null);
		pnl_movimientos.setLayout(null);
		pnl_movimientos.setBounds(80 * 4 + 2, 0, this.getWidth() - pnl_juego.getWidth(), this.getHeight());
		add(pnl_botones);
		add(pnl_juego);
		add(pnl_movimientos);
		
		btn_deshacer.setBounds(80, 20, 40, 40);
		pnl_botones.add(btn_deshacer);
		btn_rehacer.setBounds(140, 20, 40, 40);
		pnl_botones.add(btn_rehacer);
		btn_guardar.setBounds(200, 20, 40, 40);
		pnl_botones.add(btn_guardar);
		btn_jugar.setBounds(80, 65, 160, 20);
		pnl_botones.add(btn_jugar);
		btn_puntajes.setBounds(80, 90, 160, 20);
		pnl_botones.add(btn_puntajes);
		btn_solucionar.setBounds(80, 115, 160, 20);
		pnl_botones.add(btn_solucionar);
		btn_crear.setBounds(80, 140, 160, 20);
		pnl_botones.add(btn_crear);
		btn_cargar.setBounds(80, 165, 160, 20);
		pnl_botones.add(btn_cargar);

		btn_crear.setIcon(new ImageIcon(getClass().getResource((PATH + "crear.png"))));
		btn_deshacer.setIcon(new ImageIcon(Object.class.getResource(PATH + "deshacer.png")));
		btn_rehacer.setIcon(new ImageIcon(Object.class.getResource(PATH + "rehacer.png")));
		btn_guardar.setIcon(new ImageIcon(Object.class.getResource(PATH + "guardar.png")));
		btn_jugar.setIcon(new ImageIcon(Object.class.getResource(PATH + "jugar.png")));
		btn_puntajes.setIcon(new ImageIcon(Object.class.getResource(PATH + "puntajes.png")));
		btn_solucionar.setIcon(new ImageIcon(Object.class.getResource(PATH + "solucionar.png")));
		btn_cargar.setIcon(new ImageIcon(Object.class.getResource(PATH + "cargar.png")));
		
		lbl_movimientos = new JLabel("Movimientos: "+juego.getPasos());
		lst_movimientos = new JList<>();
		Font fuente = new Font("Arial", Font.BOLD, 14);
		scroll = new JScrollPane(lst_movimientos);
		
		pnl_movimientos.add(lbl_movimientos);
		pnl_movimientos.add(scroll);
		
		lbl_movimientos.setBounds(45, 10, pnl_movimientos.getWidth(), 30);
		lbl_movimientos.setFont(fuente);
		lbl_movimientos.setForeground(new Color(19, 136, 149));
		
		lst_movimientos.setBounds(10, 55, pnl_movimientos.getWidth() - 25, 450);
		lst_model = new DefaultListModel<>();
		lst_movimientos.setModel(lst_model);
		
		scroll.setBounds(10, 55, pnl_movimientos.getWidth() - 25, 450);
		
		btn_deshacer.addActionListener(this);
		btn_guardar.addActionListener(this);
		btn_puntajes.addActionListener(this);
		btn_rehacer.addActionListener(this);
		btn_jugar.addActionListener(this);
		btn_solucionar.addActionListener(this);
		btn_crear.addActionListener(this);
		btn_cargar.addActionListener(this);
		
		crearMatriz();
	}
	
	/**
	 * Verifica si el juego está resuelto
	 */
	public void verificar(){
		if(juego.resuelto()){
			juego.setJugando(false);
			this.terminar();
		}
	}
	
	/**
	 * Deshace el ultimo movimiento hecho
	 */
	public void deshacer(){
		if(!juego.getMovimientos().isEmpty()){
			Pareja<Integer, Integer> p = juego.getMovimientos().pop();
			int i = (int) p.getI();
			int j = (int) p.getJ();
			int mov = juego.verificar(i, j);
			int aux = juego.getMatriz()[i][j];
			int ianterior;
			int janterior;
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
				Ficha f = matriz_lbl[i][j];
				matriz_lbl[i][j] = matriz_lbl[ianterior][janterior];
				matriz_lbl[ianterior][janterior] = f;
				matriz_lbl[i][j].setLocation(j * 80 + 2, i * 80 + 2);
				matriz_lbl[ianterior][janterior].setLocation(janterior * 80 + 2, ianterior * 80 + 2);
				juego.getDeshechos().push(new Pareja<Integer, Integer>(i, j));
				lst_model.removeElementAt(lst_model.size()-1);
			}
		}
	}
	
	/**
	 * Deshace el ultimo movimiento hecho
	 */
	public void rehacer(){
		if(!juego.getDeshechos().isEmpty()){
			Pareja<Integer, Integer> p = juego.getDeshechos().pop();
			int i = (int) p.getI();
			int j = (int) p.getJ();
			int mov = juego.verificar(i, j);
			int aux = juego.getMatriz()[i][j];
			int ianterior;
			int janterior;
			
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
				Ficha f = matriz_lbl[i][j];
				matriz_lbl[i][j] = matriz_lbl[ianterior][janterior];
				matriz_lbl[ianterior][janterior] = f;
				matriz_lbl[i][j].setLocation(j * 80 + 2, i * 80 + 2);
				matriz_lbl[ianterior][janterior].setLocation(janterior * 80 + 2, ianterior * 80 + 2);
				juego.getMovimientos().push(new Pareja<Integer, Integer>(i, j));
				lst_model.addElement(juego.getMatriz()[i][j]);
			}
		}
		
	}	
	
	/**
	 * Organiza lo necesario para empezar a jugar
	 */
	public void organizarJuego(){
		for (int i = 0; i < matriz_lbl.length; i++) {
			for (int j = 0; j < matriz_lbl.length; j++) {
				if(juego.getMatriz()[i][j] != 0){
					matriz_lbl[i][j].setId(juego.getMatriz()[i][j]);
					matriz_lbl[i][j].setIcon(new ImageIcon(Object.class.getResource(PATH + juego.getMatriz()[i][j] + ".png")));
				}else{
					matriz_lbl[i][j].setIcon(null);
				}		
			}
		}
		
	}
	
	/**
	 * Devuelve solamente el nombre del archivo
	 * @param aux la ruta del archivo
	 * @return el nombre del puzzle
	 */
	public String nombreArchivo(String aux){
		puzzle = "";
		for (int i = aux.length()-5; i >= 0; i--) {
			if(aux.charAt(i) == '\\')
				break;
			else
				puzzle = aux.charAt(i) + puzzle;
		}
		return puzzle;
	}
	
	/**
	 * Termina el juego una ves está resuelto
	 */
	public void terminar(){
		String nombre = JOptionPane.showInputDialog("Juego resuelto en "+juego.getPasos()+" pasos\nPorfavor ingrese su nombre");
		if(nombre == null){
			nombre = "Sin nombre";
		}
		if(juego.agregarJugador(nombre, juego.getPasos(), puzzle)){
			JOptionPane.showMessageDialog(null, "Estas entre los mejores 5 del mapa que jugaste");
			ventana_puntajes.actualizar();
			
			File archivo;
			if(guardadoPorPrimeraVez){
				archivo = puntajes;
			}else{
				JOptionPane.showMessageDialog(null, "Seleccione donde guardará los puntajes");
				archivo = guardar();
				puntajes = archivo;
			}
			
			EscritorLector.serializarPuntajes(juego.getPuntajes(), archivo);
		}else{
			JOptionPane.showMessageDialog(null, "No has ingresado entre los mejores 5 del mapa que jugaste");
		}
		juego.setPasos(0);
	}
	
	/**
	 * Retorna un directorio donde guardar.
	 * @return el archivo o ruta donde se va a guardar
	 */
	public File guardar(){
		chooser.setCurrentDirectory(new File("/"));
		int a = chooser.showSaveDialog(null);
		File archivo = null;
		if(a == 0)
			archivo = chooser.getSelectedFile();
		return archivo;
	}
	
	/**
	 * Retorna un archivo para deserializar
	 * @return el archivo que se va a cargar
	 */
	public File cargar(){
		JOptionPane.showMessageDialog(null, "Seleccione el archivo de puntajes.\nSi es primera ves, oprima cancelar en el siguiente diálogo.");
		chooser.setCurrentDirectory(new File("/"));
		int a = chooser.showOpenDialog(null);
		File archivo = null;
		if(a == 0)
			archivo = chooser.getSelectedFile();
		return archivo;
	}
	
	/**
	 * actionPerformed para los botones
	 * @param e el evento
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == btn_puntajes){
			ventana_puntajes.actualizar();
			ventana_puntajes.setVisible(true);
			ventana_puntajes.setLocation(this.getX() - 180, this.getY());
		}
		
		if(e.getSource() == btn_jugar){
			if(juego.isJugando()){
				int aux = JOptionPane.showConfirmDialog(null, "¿Desea guardar el juego actual? perderá todos los datos no guardados");
				if(aux == JOptionPane.CANCEL_OPTION || aux == JOptionPane.CLOSED_OPTION){
					return;
				}else if(aux == JOptionPane.YES_OPTION){
					File archivo = guardar();
					if(archivo != null && archivo.exists()){
						JOptionPane.showMessageDialog(null, "El nombre guardado ya existe, intente nuevamente.");
						return;
					}else if(archivo == null){
						JOptionPane.showMessageDialog(null, "No se ha guardado");
						return;
					}
					EscritorLector.serializar(juego,archivo);
				}
			}
			chooser.setCurrentDirectory(new File("/"));
			int a = chooser.showOpenDialog(this);
			if(a == 0){
				chooser.getCurrentDirectory();
				File archivo = chooser.getSelectedFile();
				
				String aux = archivo.getPath();
				puzzle = nombreArchivo(aux);
				EscritorLector.leer(archivo.getPath(), juego);
				juego.setJugando(true);
				juego.getDeshechos().clear();
				juego.getMovimientos().clear();
				juego.setPasos(0);
				lbl_movimientos.setText("Movimientos: "+juego.getPasos());
				lst_model.clear();
				organizarJuego();
			}
			
		}
		
		if(e.getSource() == btn_deshacer){
			if(juego.isJugando())
				deshacer();
		}
		
		if(e.getSource() == btn_rehacer){
			if(juego.isJugando())
				rehacer();
		}

		if(e.getSource() == btn_crear){
			if(juego.isJugando()){
				JOptionPane.showMessageDialog(null, "Debe terminar el juego que está en marcha");
			}else{
				juego.setVentanaC(null);
				juego.inicializar();
				VentanaCreacion v = new VentanaCreacion(this,juego);
				juego.setVentanaC(v);
				v.setVisible(true);
				this.setVisible(false);
			}
			
		}
		
		if(e.getSource() == btn_guardar){
			
			if(juego.isJugando()){
				File archivo = guardar();
				if(archivo != null && archivo.exists()){
					JOptionPane.showMessageDialog(null, "El nombre guardado ya existe, intente nuevamente.");
					return;
				}else if(archivo == null){
					JOptionPane.showMessageDialog(null, "No se ha guardado");
					return;
				}
				EscritorLector.serializar(juego,archivo);
			}else{
				JOptionPane.showMessageDialog(null, "Debe haber un juego iniciado para poder guardar.");
			}
		}
		
		if(e.getSource() == btn_solucionar){
			
			if(juego.isJugando()){
				int i = 0, j = 0;
				c:for (i = 0; i < juego.getMatriz().length; i++) {
					for (j = 0; j < juego.getMatriz().length; j++) {
						if(juego.getMatriz()[i][j] == 0){
							break c;
						}
							
					}
				}
				
				Resolver r = new Resolver(juego.getMatriz().clone());
				
				long ini = System.currentTimeMillis();
				
				r.solucion(0, new ArrayList<>(), r.getOrden(), i, j);
				ArrayList<Integer> solucion = r.getSolucion();
				
				lbl_movimientos.setText("Movimientos: "+(juego.getPasos() + solucion.size()));
				
				for (int k = solucion.size()-1; k >=0; k--) {
					lst_model.addElement(solucion.get(k));
				}
				
				juego.inicializar();
				this.organizarJuego();
				juego.setJugando(false);
				
				
				long fin = System.currentTimeMillis();
				JOptionPane.showMessageDialog(null, "Juego solucionado en: "+(fin - ini)/1000+" segundos");
			}else{
				JOptionPane.showMessageDialog(null, "El juego actual ya se encuentra solucionado");
			}
		}
		
		if(e.getSource() == btn_cargar){
			if(juego.isJugando()){
				int aux = JOptionPane.showConfirmDialog(null, "¿Desea guardar el juego actual? perderá todos los datos no guardados.");
				if(aux == JOptionPane.CANCEL_OPTION || aux == JOptionPane.CLOSED_OPTION){
					return;
				}else if(aux == JOptionPane.YES_OPTION){
					File archivo = guardar();
					if(archivo.exists()){
						JOptionPane.showMessageDialog(null, "El nombre guardado ya existe, intente nuevamente.");
						return;
					}
					EscritorLector.serializar(juego,archivo);
				}
			}
			
			chooser.setCurrentDirectory(new File("/"));
			int a = chooser.showOpenDialog(this);
			if(a == 0){
				EscritorLector.deserializar(juego, chooser.getSelectedFile());
			}
		}
		
	}

	/**
	 * @return the btn_deshacer
	 */
	public JButton getBtn_deshacer() {
		return btn_deshacer;
	}

	/**
	 * @param btn_deshacer the btn_deshacer to set
	 */
	public void setBtn_deshacer(JButton btn_deshacer) {
		this.btn_deshacer = btn_deshacer;
	}

	/**
	 * @return the btn_rehacer
	 */
	public JButton getBtn_rehacer() {
		return btn_rehacer;
	}

	/**
	 * @param btn_rehacer the btn_rehacer to set
	 */
	public void setBtn_rehacer(JButton btn_rehacer) {
		this.btn_rehacer = btn_rehacer;
	}


	/**
	 * @return the btn_revolver
	 */
	public JButton getBtn_jugar() {
		return btn_jugar;
	}

	/**
	 * @param btn_revolver the btn_revolver to set
	 */
	public void setBtn_revolver(JButton btn_revolver) {
		this.btn_jugar = btn_revolver;
	}

	/**
	 * @return the btn_puntajes
	 */
	public JButton getBtn_puntajes() {
		return btn_puntajes;
	}

	/**
	 * @param btn_puntajes the btn_puntajes to set
	 */
	public void setBtn_puntajes(JButton btn_puntajes) {
		this.btn_puntajes = btn_puntajes;
	}

	/**
	 * @return the btn_solucionar
	 */
	public JButton getBtn_solucionar() {
		return btn_solucionar;
	}

	/**
	 * @param btn_solucionar the btn_solucionar to set
	 */
	public void setBtn_solucionar(JButton btn_solucionar) {
		this.btn_solucionar = btn_solucionar;
	}

	/**
	 * @return the btn_crear
	 */
	public JButton getBtn_crear() {
		return btn_crear;
	}

	/**
	 * @param btn_crear the btn_crear to set
	 */
	public void setBtn_crear(JButton btn_crear) {
		this.btn_crear = btn_crear;
	}

	/**
	 * @return the pnl_movimientos
	 */
	public JPanel getPnl_movimientos() {
		return pnl_movimientos;
	}

	/**
	 * @param pnl_movimientos the pnl_movimientos to set
	 */
	public void setPnl_movimientos(JPanel pnl_movimientos) {
		this.pnl_movimientos = pnl_movimientos;
	}

	/**
	 * @return the lbl_movimientos
	 */
	public JLabel getLbl_movimientos() {
		return lbl_movimientos;
	}

	/**
	 * @param lbl_movimientos the lbl_movimientos to set
	 */
	public void setLbl_movimientos(JLabel lbl_movimientos) {
		this.lbl_movimientos = lbl_movimientos;
	}

	/**
	 * @return the lst_movimientos
	 */
	public JList<Integer> getLst_movimientos() {
		return lst_movimientos;
	}

	/**
	 * @param lst_movimientos the lst_movimientos to set
	 */
	public void setLst_movimientos(JList<Integer> lst_movimientos) {
		this.lst_movimientos = lst_movimientos;
	}

	/**
	 * @param btn_jugar the btn_jugar to set
	 */
	public void setBtn_jugar(JButton btn_jugar) {
		this.btn_jugar = btn_jugar;
	}

	/**
	 * @return the lst_model
	 */
	public DefaultListModel<Integer> getLst_model() {
		return lst_model;
	}

	/**
	 * @param lst_model the lst_model to set
	 */
	public void setLst_model(DefaultListModel<Integer> lst_model) {
		this.lst_model = lst_model;
	}

	/**
	 * @return the btn_cargar
	 */
	public JButton getBtn_cargar() {
		return btn_cargar;
	}

	/**
	 * @param btn_cargar the btn_cargar to set
	 */
	public void setBtn_cargar(JButton btn_cargar) {
		this.btn_cargar = btn_cargar;
	}

	/**
	 * @return the scroll
	 */
	public JScrollPane getScroll() {
		return scroll;
	}

	/**
	 * @param scroll the scroll to set
	 */
	public void setScroll(JScrollPane scroll) {
		this.scroll = scroll;
	}

	/**
	 * @return the ventana_puntajes
	 */
	public VentanaPuntajes getVentana_puntajes() {
		return ventana_puntajes;
	}

	/**
	 * @param ventana_puntajes the ventana_puntajes to set
	 */
	public void setVentana_puntajes(VentanaPuntajes ventana_puntajes) {
		this.ventana_puntajes = ventana_puntajes;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @return the puzzle
	 */
	public String getPuzzle() {
		return puzzle;
	}

	/**
	 * @param puzzle the puzzle to set
	 */
	public void setPuzzle(String puzzle) {
		this.puzzle = puzzle;
	}

	/**
	 * @return the puntajes
	 */
	public File getPuntajes() {
		return puntajes;
	}

	/**
	 * @param puntajes the puntajes to set
	 */
	public void setPuntajes(File puntajes) {
		this.puntajes = puntajes;
	}

	/**
	 * @return the guardadoPorPrimeraVez
	 */
	public boolean isGuardadoPorPrimeraVez() {
		return guardadoPorPrimeraVez;
	}

	/**
	 * @param guardadoPorPrimeraVez the guardadoPorPrimeraVez to set
	 */
	public void setGuardadoPorPrimeraVez(boolean guardadoPorPrimeraVez) {
		this.guardadoPorPrimeraVez = guardadoPorPrimeraVez;
	}
	
	
	
}
