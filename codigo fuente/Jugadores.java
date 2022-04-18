import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.*;

/**
 * Clase que modela el conjunto de jugadores participantes en la partida. 
 * @author Laura Honrubia Baamonde y Alicia Meleiro Est&eacute;vez
 * @see Jugador
 */
public class Jugadores {
	/**
	 * Mapa que guarda internamente el conjunto de jugadores.
	 */
	private Map<String, Jugador> mapaJugadores;

	/**
	 * Constructor de la clase que inicializa el objeto vac&iacute;o.
	 */
	public Jugadores() {
		mapaJugadores = new LinkedHashMap<String, Jugador>();
	}

	/**
	 * A&ntilde;ade un conjunto de jugadores obtenidos de un archivo cuyo nombre se facilita.
	 * @param nombre_f Nombre del archivo en formato A2
	 * @return Devuelve <b>0</b> si se carg&oacute; el archivo sin problemas y <b>-1</b> en caso contrario.
	 */
	public int añadir(String nombre_f) {
		Scanner f_jugadores = null;

		try {
			f_jugadores = new Scanner(new FileInputStream(nombre_f));
		} catch (FileNotFoundException e) {
			return -1;
		}
		// Lectura linea a linea, finaliza cuando no haya mas lineas
		String linea = null;

		while (f_jugadores.hasNextLine()) {
			linea = f_jugadores.nextLine();
			if(!(linea.isEmpty())){
				String id = linea.substring(0, linea.indexOf(":"));
				String nombre = linea.substring(linea.indexOf("{") + 1, linea.indexOf("}"));
			
//Si existe sobreescribe, en vez de no meterlo (put se encarga de sobreescribirlo)

			Jugador jugador = new Jugador(id, nombre);
			mapaJugadores.put(id, jugador);
			}
		}
		return 0;
	}

	/**
	 * Instrucci&oacute;n para el modelo de ejecuci&oacute;n por instrucciones para borrar un Jugador
	 * del conjunto de Jugadores.Muestra por consola o el fichero especificado, el resultado de la 
	 * instrucci&oacute;n.
	 * @param id Identificador del Jugador a eliminar.
	 */
	public void borrarJugador(String id) {
		if (!mapaJugadores.containsKey(id))
			System.out.println("BorrarJugador " + id + ": FAIL.");
		else {
			mapaJugadores.remove(id);
			System.out.println("BorrarJugador " + id + ": OK.");
		}
	}

	/**
	 * Instrucci&oacute;n para el modelo de ejecuci&oacute;n por instrucciones para cargar un 
	 * conjunto de jugadores obtenidos de un archivo en formato A2 cuyo nombre se facilita. Muestra por 
	 * consola o el fichero especificado, el resultado de la instrucci&oacute;n.
	 * @param nombre_f Nombre del fichero para cargar.
	 */
	public void cargarJugadores(String nombre_f) {
		if (añadir(nombre_f) == 0) {
			System.out.println("CargarJugadores " + nombre_f + ": OK.");
		} else {
			System.out.println("CargarJugadores " + nombre_f + ": FAIL.");
		}
	}

	/**
	 * Devuelve la lista de Jugadores.
	 * @return Lista de Jugadores.
	 */
	public ArrayList<Jugador> convertirEnLista() {
		return new ArrayList<Jugador>(mapaJugadores.values());
	}

	/**
	 * Construye una cadena para la grabaci&oacute;n del Jugador en el m&eacute;todo 
	 * {@link #volcarJugadores(String) volcarJugadores}
	 * @param j
	 * @return
	 */
	private String creaLineaA2(Jugador j) {
		return j.getId() + ":{" + j.getNombre() + "}";
	}

	/**
	 * Instrucci&oacute;n para el modelo de ejecuci&oacute;n por instrucciones para 
	 * crear un Jugador y a&ntilde;adirlo a la partida.
	 * Muestra por consola o el fichero especificado, el resultado de la instrucci&oacute;n.
	 * @param id Identificador del Jugador.
	 * @param nombre Nombre del Jugador.
	 */
	public void crearJugador(String id, String nombre) {
		if (mapaJugadores.containsKey(id))
			System.out.println("CrearJugador " + id + " " + nombre + ": FAIL.");
		else {
			Jugador j = new Jugador(id, nombre);
			mapaJugadores.put(id, j);
			System.out.println("CrearJugador " + id + " " + nombre + ": OK.");
		}
	}

	/**
	 * Construye una cadena de caracteres con la informaci&oacute;n del identificador 
	 * del Jugador, y los identificadores y salud de sus criaturas, seg&uacute;n el formato 
	 * especificado A5
	 * @return Cadena con los jugadores y criaturas involucrados en la batalla.
	 */
	public String devolverLineaBatalla() {
		String cadena = "";
		boolean primerJugador = true;
		for (String id : mapaJugadores.keySet()) {
			if (!primerJugador)
				cadena = cadena + ",";
			else
				primerJugador = false;
			Jugador jugador = mapaJugadores.get(id);
			cadena = cadena + id + ":{";
			boolean esPrimero = true;
			for (Criatura criatura : jugador.getListaCriaturas()) {
				if (!esPrimero)
					cadena = cadena + ",";
				else
					esPrimero = false;
				cadena = cadena + criatura.devolverIdSalud();
			}
			cadena = cadena + "}";
		}
	
		return cadena;
	}

	/**
	 * Construye la cadena con todas las puntuaciones de los jugadores.
	 * @return cadena con todas las puntuaciones de los jugadores.
	 */
	public String devolverPuntosBatalla() {
		boolean primerJugador = true;
		String cadena = "  PUNTOS CONSEGUIDOS: ";
		for (String id : mapaJugadores.keySet()) {
			if (!primerJugador)
				cadena = cadena + ",";
			else
				primerJugador = false;
			Jugador jugador = mapaJugadores.get(id);
			cadena = cadena + jugador.getId() + "=" + jugador.getPuntos();
		}
		return cadena;
	}

	/**
	 * Imprime la puntuaci&oacute;n de la partida.
	 */
	public void imprimirPuntosPartida() {
		Jugador ganador = obtenerGanador();
		for (String id : mapaJugadores.keySet()) {
			String cadena = mapaJugadores.get(id).devolverPuntuacion();
			System.out.print(cadena);
			if(ganador != null){
				if(id.equals(ganador.getId())){
					System.out.print(" (VENCEDOR)");
				}
			}
			System.out.println();

		}
	}

	
	/**
	 * Devuelve el mapa interno de Jugadores.
	 * @return Mapa con los Jugadores, cuya clave son los identificadores.
	 */
	public Map<String, Jugador> getJugadores() {
		return mapaJugadores;
	}

	/**
	 * Comprueba si el n&uacute;mero de jugadores en la partida es la permitida
	 * @return <b>true</b> si est&aacute; entre 2 y 4, <b>falso</b> en caso contrario.
	 */
	public boolean numeroCorrecto() {
		return (numJugadores() >= 2 && numJugadores() <= 4);
	}

	/**
	 * Obtiene el n&uacute;mero de jugadores participantes.
	 * @return N&uacute;mero de jugadores
	 */
	public int numJugadores() {
		return mapaJugadores.size();
	}

	/**
	 * Obtiene un conjunto con los identificadores de los jugadores.
	 * @return Conjunto con los identificadores de los jugadores.
	 */
	public HashSet<String> obtenerIds() {
		HashSet<String> conjuntoId = new HashSet<String>();
		for(String id:mapaJugadores.keySet())
			conjuntoId.add(id);
		return conjuntoId;
	}

	/**
	 * Obtener un objeto Jugador a partir de su identificador
	 * @param id Identificador del jugador.
	 * @return Jugador si est&aacute; en la partida y <b>null</b> en caso contrario.
	 */
	public Jugador obtenerPorId(String id) {
		return mapaJugadores.get(id);
	}

	/**
	 * Instrucci&oacute;n para el modelo de ejecuci&oacute;n por instrucciones para
	 * guardar los jugadores en un archivo en formato A2.Muestra por 
	 * consola o el fichero especificado, el resultado de la instrucci&oacute;n.
	 * <ul>
	 * <li> OK.  Si se pudo generar un archivo con todos los jugadores y el nombre especificado.</li>
	 * <li> FAIL. Si no se pudo escribir el archivo especificado.</li>
	 * </ul>
	 * @param nombre_f Nombre del fichero donde guardar la informaci&oacute;n
	 */
	public void volcarJugadores(String nombre_f) {
		PrintWriter salida = null;
		try {
			salida = new PrintWriter(new FileOutputStream(nombre_f));
			for (String id : mapaJugadores.keySet()) {
				String linea = creaLineaA2(mapaJugadores.get(id));
				salida.println(linea);
			}
		} catch (FileNotFoundException e) {
			System.out.println("VolcarJugadores " + nombre_f + ": FAIL.");
		} finally {
			salida.close();
		}
		System.out.println("VolcarJugadores " + nombre_f + ": OK.");
	}
	
	/**
	 * Comprueba si es el &uacute;ltimo jugador de la lista.
	 * @param idJug Identificador del jugador a comprobar.
	 * @return <b>true</b> si es el &uacute;ltimo y <b>false</b> en cso contrario.
	 */
	public boolean esUltimo(String idJug) {
		ArrayList<Jugador> lista = new ArrayList<Jugador>(mapaJugadores.values());
		return lista.get(lista.size()-1).getId().equals(idJug);
	}

	/**
	 * Si ha finalizado la partida, el ganador es el jugador con mas puntos que los demas
	 * en caso de que haya uno.
	 * 
	 * @return Jugador ganador de la partida si lo hay, <b>null</b> si hay empate entre varios con 
	 * la puntuacion mas alta
	 */
	public Jugador obtenerGanador(){
		ArrayList<Jugador> lista = new ArrayList<Jugador>(mapaJugadores.values());
		Jugador ganador = lista.get(0);
		
		//Obtenemos el jugador ganador
		for(Jugador jugador : mapaJugadores.values()){
			if(jugador.getPuntos() > ganador.getPuntos()){
				ganador = jugador;
			}
		}
		lista.remove(ganador);

		//Comprobamos que es el unico con esa puntuacion. Si no lo es, ganador es null
		for(int i=0; i<lista.size(); i++){
			if(ganador.getPuntos() == lista.get(i).getPuntos()){
				ganador = null;
				break;
			}
		}
		//Si el jugador con puntuacion maxima no llega a 10 puntos, no es considerado ganador
		/*if(ganador != null){
			if(!(ganador.ganadorPartida()))
				ganador = null;
		}*/

		return ganador;
	}
}