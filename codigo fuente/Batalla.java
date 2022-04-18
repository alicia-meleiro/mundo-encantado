import java.util.ArrayList;

/**
 * Esta clase modela una batalla entre jugadores de MundoEncantado,consistente
 * en 10 luchas o hasta que queden menos de dos jugadores activos.
 * 
 * @author Laura Honrubia Baamonde y Alicia Meleiro Est&eacute;vez
 *
 */
public class Batalla {

	/**
	 * Referencia a un objeto Jugadores entre los que se disputa la batalla
	 */
	private Jugadores jugadores;
	/**
	 * Contador de Luchas en esta batalla
	 */
	private int numLuchas;

	/**
	 * Constructor de la clase, inicializa el n&uacute;mero de luchas y asigna los
	 * jugadores implicados.
	 * 
	 * @param jugadores Objeto con los jugadores implicados en la batalla.
	 */
	public Batalla(Jugadores jugadores) {
		this.jugadores = jugadores;
		numLuchas = 1;

	}

	/**
	 * Ejecuta una batalla, consistente en 10 luchas o hasta que queden menos de dos
	 * jugadores activos. Al final de la batalla se reparten los puntos a los
	 * jugadores. Informa por la consola o el archivo especificado mensajes con los
	 * participantes en la batalla, el resultado de la misma y las visitas
	 * realizadas a los lugares sagrados.
	 * 
	 * @param numBatalla N&uacute;mero de batalla
	 * @see Lucha
	 */
	public void ejecutarBatalla(int numBatalla) {
		ArrayList<Jugador> listaJugadores = jugadores.convertirEnLista();
		int atacante = 0;
		int defensor = 0;
		System.out.println("BATALLA_" + numBatalla + " " + jugadores.devolverLineaBatalla());
		while (!esFinBatalla()) {
			int[] combatientes = elegirCombatientes(listaJugadores, atacante, defensor);
			atacante = combatientes[0];
			defensor = combatientes[1];
			Lucha lucha = new Lucha(listaJugadores.get(atacante), listaJugadores.get(defensor));
			lucha.ejecutaLucha(numLuchas++);
			System.out.println("  " + jugadores.devolverLineaBatalla());
			atacante = defensor;
		}

	}

	/**
	 * M&eacute;todo para determinar entre los jugadores activos los siguientes
	 * roles de atacante y defensor, partiendo de los &uacute;ltimos en
	 * desempe&ntilde;arlos.
	 * 
	 * @param listaJugadores Lista de jugadores participantes en la batalla.
	 * @param antAtac        Entero &iacute;ndice en la colecci&oacute;n de
	 *                       Jugadores al &uacute;ltimo atacante
	 * @param antDef         Entero &iacute;ndice en la colecci&oacute;n de
	 *                       Jugadores al &uacute;ltimo atacante
	 * @return Matriz de dos enteros, el primero de los cuales identifica el
	 *         &iacute;ndice del atacante y el segundo el del defensor.
	 */
	private int[] elegirCombatientes(ArrayList<Jugador> listaJugadores, int antAtac, int antDef) {
		while (!listaJugadores.get(antAtac).estaActivo())
			if (antAtac == listaJugadores.size() - 1)
				antAtac = 0;
			else
				antAtac++;
		if (antAtac == listaJugadores.size() - 1)
			antDef = 0;
		else
			antDef = antAtac + 1;
		while (!listaJugadores.get(antDef).estaActivo())
			if (antDef == listaJugadores.size() - 1)
				antDef = 0;
			else
				antDef++;
		int[] arrayCombat = { antAtac, antDef };
		return arrayCombat;
	}

	/**
	 * M&eacute;todo para determinar si se dan las condiciones para finalizar la
	 * batalla. Es decir:
	 * <ol>
	 * <li>Solo 1 jugador activo, que ser&aacute; el ganador y recibe 2 puntos por
	 * cada criatura inactiva en su poder.</li>
	 * <li>Se alcancen las 10 luchas sin ganador, cada jugador activo recibe 1 punto
	 * por cada criatura inactiva en su poder.</li>
	 * </ol>
	 * 
	 * @return <b>true</b> si se termin&oacute; la batalla y <b>false</b> en caso
	 *         contrario.
	 */
	private boolean esFinBatalla() {
		boolean finBatalla = false;
		int jugActivos = 0;
		Jugador ganador = null; // antes new Jugador(); se elimina la necesidad de un constructor vacío
		for (String id : jugadores.obtenerIds())
			if (jugadores.obtenerPorId(id).estaActivo()) {
				ganador = jugadores.obtenerPorId(id);
				jugActivos++;
			}
		if (jugActivos == 0) {
			finBatalla = true;
			System.out.println();
			System.out.println("  FIN BATALLA: No hay jugadores activos.");
			System.out.print("  PUNTOS CONSEGUIDOS: ");
			for (String id : jugadores.obtenerIds()) {
				int contador = 0;
				System.out.print(id + "=" + contador);
				if (!jugadores.esUltimo(id))
					System.out.print(",");
				else {
					System.out.println();
					System.out.println();
				}

			}
		} else if (jugActivos == 1) {
			System.out.println();
			System.out.println("  FIN BATALLA: Solo hay un jugador activo.");
			finBatalla = true;
			System.out.print("  PUNTOS CONSEGUIDOS: ");

			for (String id : jugadores.obtenerIds()) {
				Jugador jugador = jugadores.obtenerPorId(id);
				int contador = 0;
				if (jugador.equals(ganador)) {
					for (Criatura criatura : ganador.getListaCriaturas())
						if (criatura.getSalud() > 0)
							contador = contador + 2;
					jugador.sumarPuntos(contador);
				}
				System.out.print(id + "=" + contador);
				if (!jugadores.esUltimo(id))
					System.out.print(",");
				else {
					System.out.println();
					System.out.println();
				}
			}

		}
		if (numLuchas == 11 && !finBatalla) {
			System.out.println();
			System.out.println("  FIN BATALLA: Ya se han producido 10 luchas.");
			finBatalla = true;
			System.out.print("  PUNTOS CONSEGUIDOS: ");
			for (String id : jugadores.obtenerIds()) {
				Jugador jugador = jugadores.obtenerPorId(id);
				int contador = 0;
				if (jugador.estaActivo()) {
					for (Criatura criatura : jugador.getListaCriaturas())
						if (criatura.getSalud() > 0)
							contador++;
					jugador.sumarPuntos(contador);
				}
				System.out.print(id + "=" + contador);
				if (!jugadores.esUltimo(id))
					System.out.print(",");
				else {
					System.out.println();
					System.out.println();
				}

			}

		}

		return finBatalla;
	}
}
