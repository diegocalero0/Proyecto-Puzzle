package logica;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import javax.swing.JOptionPane;


/**
 * En esta clase se hace lo correspondiente a escribir y leer de archivos
 * @author Diego Calero
 *
 */
public class EscritorLector implements Serializable{
	
	
	private static final long serialVersionUID = 5L;
	private static FileWriter escritor;
	private static Scanner lector;
	private static ObjectOutputStream escritorSer;
	private static ObjectInputStream lectorSer;
		
	/**
	 * Guarda en un archivo el puzzle
	 * @param ruta la ruta donde guardará el puzle
	 * @param juego el juego donde se creo el puzzle
	 */
	public static void escribir(String ruta, Juego juego){
		try {
			File archivo = new File(ruta);
			escritor = new FileWriter( archivo );
			for (int i = 0; i < 4; i++) {
				for (int j = 0; j < 4; j++) {
					escritor.write( juego.getMatriz()[i][j] + " " );
				}
				escritor.write("\r\n");
			}
			
			escritor.flush();
			escritor.close();
			
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "No se encontró el archivo");
		} catch (IOException e){
			JOptionPane.showMessageDialog(null, "Se encontró un problema de salida");
		}
	}
	
	/**
	 * Lee uno de los mapas
	 * @param ruta la ruta donde se encuentra el mapa
	 * @param juego el juego donde ingresará los datos
	 */
	public static void leer(String ruta, Juego juego){
		try {
			
			File archivo = new File(ruta);
			lector = new Scanner(archivo);
			int n;
			for (int i = 0; i < 4; i++) {
				for (int j = 0; j < 4; j++) {
					n = lector.nextInt();
					juego.getMatriz()[i][j] = n;
				}
			}
			
		} catch (FileNotFoundException e) {
			
		}
	}
	
	/**
	 * Serializa el juego actual
	 * @param juego el juego a serializar
	 * @param nombre el archivo que se guardará
	 */
	public static void serializar(Juego juego, File nombre){
		File ser = nombre;
		JOptionPane.showMessageDialog(null, ser.toString());
		try {
			escritorSer = new ObjectOutputStream( new FileOutputStream(ser) );
			escritorSer.writeObject(juego);
			escritorSer.close();
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Error al seleccionar el archivo");
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Error al serializar");
			e.printStackTrace();
		}
	}
	
	/**
	 * Deserializa un juego guardado anteriormente
	 * @param juego el juego que se actualizará
	 * @param archivo el archivo a deserializar
	 */
	public static void deserializar(Juego juego, File archivo){
		File ser = archivo;
		Juego juegoAux;
		try {
			lectorSer = new ObjectInputStream( new FileInputStream(ser) );
			if(ser.exists()){
				juegoAux = (Juego)(lectorSer.readObject());
				juego.setPasos(juegoAux.getPasos());
				juego.setDeshechos(juegoAux.getDeshechos());
				juego.setJugando(juegoAux.isJugando());
				juego.setMatriz(juegoAux.getMatriz());
				juego.setMovimientos(juegoAux.getMovimientos());
				juego.getVentana().setPuzzle(juegoAux.getVentana().getPuzzle());
				juego.getVentana().organizarJuego();
				juego.getVentana().getLbl_movimientos().setText("Movimientos: "+juego.getPasos());
				juego.getVentana().getLst_model().clear();
				System.out.println(juegoAux.getVentana().getPuzzle());
				for (int i = 0; i < juegoAux.getVentana().getLst_model().size(); i++) {
					juego.getVentana().getLst_model().addElement(juegoAux.getVentana().getLst_model().getElementAt(i));
				}
			}
			lectorSer.close();
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Error al seleccionar el archivo");
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Error al deserializar");
		} catch (ClassNotFoundException e){
			JOptionPane.showMessageDialog(null, "El archivo no es valido");
		}
	}
	
	/**
	 * Serializa los puntajes
	 * @param puntajes los puntajes a serializar
	 * @param archivo el archivo donde se guardarán los puntajes
	 */
	public static void serializarPuntajes(HashMap<String, ArrayList< Pareja<String, Integer> >> puntajes, File archivo){
		File ser = archivo;
		try {
			escritorSer = new ObjectOutputStream( new FileOutputStream(ser) );
			escritorSer.writeObject(puntajes);
			escritorSer.close();
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Error al seleccionar el archivo");
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Error al serializar");
		}
	}
	
	/**
	 * Deserializa un juego guardado anteriormente
	 * @param juego el juego donde se actualizaran los puntajes
	 * @param archivo el archivo donde se encuentras los puntajes
	 */
	@SuppressWarnings("unchecked")
	public static void deserializarPuntajes(Juego juego, File archivo){
		File ser = archivo;
		try {
			lectorSer = new ObjectInputStream( new FileInputStream(ser) );
			if(ser.exists()){
				juego.setPuntajes((HashMap<String, ArrayList< Pareja<String, Integer> >>)lectorSer.readObject());
			}
			lectorSer.close();
		} catch (Exception e) {
			juego.setPuntajes( new HashMap<String, ArrayList<Pareja<String, Integer>>>() );
		}
		
	}
	
}
