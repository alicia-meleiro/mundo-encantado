
import java.util.*;

/**
 * Cada reparto es la asignaci&oacute;n de <code>1-3</code> criaturas a cada 
 * uno de los <code>2-4</code> jugadores de la partida. Esta se realiza al comienzo de 
 * cada batalla. Puede ser:
 * <ul>
 * <li>Aleatorio</li>
 * <li>Segun lo marcado en el fichero de repartos</li>
 * </ul>
 * Si no hay al menos una criatura para cada jugador, la partida no se puede celebrar.
 * 
 * @author Laura Honrubia Baamonde y Alicia Meleiro Est&eacute;vez
 */

public class Reparto {

	/**
	 * Un reparto es un mapa, donde el key es el ID del jugador y Criatura[]
	 * contiene las criaturas asignadas
	 */
	private Map<String, Criatura[]> reparto;

	/**
	 * Constructor de un reparto
	 */
	public Reparto() {
		reparto = new LinkedHashMap<String, Criatura[]>();
	}

	/**
	 * Obtiene el mapa interno con el reparto de criaturas.
	 * @return Mapa con el reparto de criaturas.
	 */
	public Map<String, Criatura[]> getReparto() {
		return reparto;
	}

	/**
	 * Establece el mapa interno con el reparto de criaturas pasado como par&aacute;metro.
	 * @param reparto Mapa con el reparto de criaturas.
	 */
	public void setReparto(Map<String, Criatura[]> reparto) {
		this.reparto = reparto;
	}

	/**
	 * Añade a reparto una asignacion correspondiente a un jugador
	 * 
	 * @param IDJugador ID del jugador al que se le han asignado criaturas
	 * @param criaturas array de criaturas que se le asignan
	 */
	public void añadirAsignacion(String IDJugador, Criatura[] criaturas) {
		reparto.put(IDJugador, criaturas);
	}

	/**
	 * Devuelve el array de criaturas asignadas a un jugador
	 * 
	 * @param IDJugador ID del jugador
	 * @return array de criaturas si el jugador existe en el reparto, null si no
	 *         existe
	 */
	public Criatura[] buscaJugador(String IDJugador) {
		if (reparto.containsKey(IDJugador)) {
			return reparto.get(IDJugador);
		} else {
			System.out.println("El jugador" + IDJugador + "no existe.");
			return null;
		}
	}

	/**
	 * Genera un reparto aleatorio. Este m&eacute;todo es llamado para cada batalla en caso
	 * de no existir un fichero de repartos.
	 * 
	 * @param bosque Bosque donde habitan todas las criaturas del Mundo Encantado.
	 * @param jugadores Jugadores participantes en una partida de Mundo Encantado.
	 * @return Reparto aleatorio generado
	 */
	public Map<String, Criatura[]> repartoAleatorio(Bosque bosque, Jugadores jugadores) {

		if (jugadores.numJugadores() == 0) {
			return null;
		}
		int numCriaturasXJug = bosque.numCriaturasLibres() / jugadores.numJugadores();

		if (numCriaturasXJug == 0) {
			// Mejor imprimir esto desde PARTIDA para que no interfiera con el MODO 2
			// System.out.println("No se puede celebrar la partida porque el numero de
			// criaturas es insuficiente.");
			return null;
		} else if (numCriaturasXJug > 3)
			numCriaturasXJug = 3;

		for (String ID : jugadores.obtenerIds()) {
			ArrayList<Criatura> lc = bosque.elegirCriaturas(numCriaturasXJug);
			// List<Criatura> lc = new
			// ArrayList<Criatura>(bosque.elegirCriaturas(numCriaturasXJug));

			Criatura[] criaturas = new Criatura[numCriaturasXJug];
			for (int i = 0; i < lc.size(); i++) {
				criaturas[i] = lc.get(i);
			}

			reparto.put(ID, criaturas);
		}

		return reparto;
	}

	/**
	 * Devuelve un array con los ID de las criaturas de un jugador Cada batalla es
	 * una linea de <code>Repartos.txt</code>
	 * 
	 * @param IDJugador El ID del jugador
	 * @return String[] con los ID de las criaturas asignadas a dicho jugador, <b>null</b>
	 *         si ese ID no ha sido encontrado en el reparto
	 */
	public String[] obtenerAsignacion(String IDJugador) {

		if (reparto.containsKey(IDJugador)) {
			Criatura[] criaturas = reparto.get(IDJugador);
			String[] IDcriaturas = new String[criaturas.length];

			try {
				for (int i = 0; i < criaturas.length; i++) {
					IDcriaturas[i] = criaturas[i].getID();
				}
			} catch (NullPointerException e) {
				return IDcriaturas;
			}
			return IDcriaturas;
		} else {
			return null;
		}

	}

	/*
	 * DESDE PARTIDA Comprobar si hay fichero de repartos Si lo hay, crear un objeto
	 * Repartos e invocar el metodo repartos.leerRepartos() Si no lo hay, dejar el
	 * String nombreRepartos a null DESDE BATALLA Crear un objeto Reparto Llamar al
	 * metodo reparto.realizaReparto(), tras lo que el atributo reparto del objeto
	 * reparto contendra el reparto actual Llamar al metodo
	 * reparto.obtenerAsignacion(String IDJugador) para obtener la asignacion al
	 * jugador deseado
	 */

	/**
	 * Realiza el reparto que se va a usar en la batalla: 
	 * <ul>
	 * <li> Si hay fichero de repartos, llama al metodo que lee el reparto buscado del array de repartos </li>
	 * <li> Si no lo hay, llama al metodo que genera un reparto aleatorio</li>
	 * </ul>
	 * 
	 * @param nombreRepartos Nombre del fichero de repartos, que es <b>null</b> en
	 *                       caso de no existir
	 * @param bosque         Bosque donde habitan todas las criaturas del Mundo
	 *                       Encantado.
	 * @param jugadores      Lista de jugadores participantes en la partida.
	 * @param repartos       Repartos obtenidos de un archivo o de una
	 *                       generaci&oacute;n aleatoria.
	 * @param numBatalla     N&uacute;mero secuencial de batalla.
	 */
	public void realizaReparto(String nombreRepartos, Bosque bosque, Jugadores jugadores, Repartos repartos,
			int numBatalla) {

		if (nombreRepartos == null) {
			reparto = repartoAleatorio(bosque, jugadores);
		} else {
			reparto = repartos.obtenerReparto(numBatalla);
		}

	}

	/**
	 * Devuelve una coleccion con los ID de los jugadores que participan en el
	 * reparto
	 * 
	 * @return Set con los ID
	 */
	public Set<String> getKey() {
		return reparto.keySet();
	}

}