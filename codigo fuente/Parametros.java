import java.io.*;

/**
 * Esta clase modela la lectura de los parametros de entrada a la hora de
 * ejecutar el programa, de modo que se sepa:
 * <ol>
 * <li>El modo de juego en el que se ejecuta la partida</li>
 * <li>Los ficheros que se van a emplear</li>
 * </ol>
 * 
 * @author Laura Honrubia Baamonde y Alicia Meleiro Est&eacute;vez
 */

public class Parametros {

	// Nombres de los ficheros correspondientes al MODO 1 de juego
	private static String nombreJugadores = null;
	private static String nombreCriaturas = null;
	private static String nombreReparto = null;
	// private static String nombrePartida = null;

	// Nombres de los ficheros correspondientes al MODO 2 de juego
	private static String nombreInstrucciones = null;
	private static String nombreSalida = null;

	public static String getNombreJugadores() {
		return nombreJugadores;
	}

	public static String getNombreCriaturas() {
		return nombreCriaturas;
	}

	public static String getNombreReparto() {
		return nombreReparto;
	}

	public static String getNombreInstrucciones() {
		return nombreInstrucciones;
	}

	public static String getNombreSalida() {
		return nombreSalida;
	}

	/**
	 * Lee los parametros con los que se ejecuta el programa, en funcion de los
	 * cuales guarda los nombres de fichero. Los que no se invocan mantienen el
	 * valor null.
	 * 
	 * @param args array de String con los parametros de invocacion
	 * @return <b>1</b> si es el MODO 1, <b>2</b> si es el MODO 2, <b>0</b> si no
	 *         fue bien invocado.
	 */
	public static int leeInvocacion(String[] args) {
		// MODO 1 DE JUEGO
		if (args[0].equals("-j")) {
			nombreJugadores = args[1];
			nombreCriaturas = args[3];

			// Si nombreReparto no existe, el reparto de las criaturas al principio de cada
			// batalla es aleatorio
			// Si nombreSalida no existe, el resultado de la partida se imprime por pantalla
			if (args.length > 4) {
				if (args[4].equals("-r")) {
					nombreReparto = args[5];
				} else {
					nombreSalida = args[5];
				}
				if (args.length > 6) {
					nombreSalida = args[7];
				}
			}
			return 1;
		}
		// MODO 2 DE JUEGO
		else if (args[0].equals("-i")) {
			nombreInstrucciones = args[1];

			// Si nombreSalida no existe, las instrucciones sen imprimen por pantalla
			if (args.length > 2) {
				nombreSalida = args[3];
			}
			return 2;
		} else {
			return 0;
		}
	}

	/**
	 * MODO 2 DE EJECUCION: inicializa la salida de los resultados, tanto si es un
	 * fichero como si es la pantalla
	 * 
	 * @return f_salida Archivo de salida
	 */
	public static PrintWriter abreFicheroSalida() {
		PrintWriter f_salida = null;

		if (nombreSalida != null) {
			try {
				f_salida = new PrintWriter(new FileOutputStream(nombreSalida, true));

			} catch (FileNotFoundException e) {
				System.out.println("Error abriendo el fichero" + nombreSalida);
				System.exit(-1);
			}
		} else {
			f_salida = new PrintWriter(System.out);
		}

		return f_salida;
	}

	/**
	 * Cierra el archivo de salida
	 * 
	 * @param f_salida Archivo de salida
	 */
	public static void cierraFicheroSalida(PrintWriter f_salida) {
		f_salida.close();
	}

}