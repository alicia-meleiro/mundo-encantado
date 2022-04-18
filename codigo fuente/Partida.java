
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Clase que modela una partida de MundoEncantado como una serie de batallas
 * hasta que haya un vencedor.
 * 
 * @author Laura Honrubia Baamonde y Alicia Meleiro Est&eacute;vez
 *
 */
public class Partida {
	/**
	 * Referencia al bosque que contiene todas las criaturas del juego.
	 */
	private Bosque bosque;
	/**
	 * Referencia los jugadores que participane en el juego.
	 */
	private Jugadores jugadores;
	/**
	 * Referencia al templo maldito que visitar&aacute;n algunas criaturas.
	 */
	private TemploMaldito templo;
	/**
	 * Referencia al lago sagrado que visitar&aacute;n algunas criaturas.
	 */
	private LagoSagrado lago;
	/**
	 * Referencia al reparto de criaturas por jugador y batalla.
	 */
	private Repartos repartos;
	/**
	 * Contador de batallas realizadas.
	 */
	private int numBatalla;

	private Reparto reparto;

	/**
	 * Nombre del fichero del que se leen los repartos. Si no existe, vale null
	 */
	private String f_reparto = Parametros.getNombreReparto();

	/**
	 * Constructor de la clase
	 * 
	 * @param bosque    Referencia al bosque que contiene todas las criaturas del
	 *                  juego.
	 * @param jugadores Referencia los jugadores que participane en el juego.
	 * @param templo    Referencia al templo maldito que visitar&aacute;n algunas
	 *                  criaturas.
	 * @param lago      Referencia al lago sagrado que visitar&aacute;n algunas
	 *                  criaturas.
	 */
	public Partida(Bosque bosque, Jugadores jugadores, TemploMaldito templo, LagoSagrado lago) {
		this.bosque = bosque;
		this.jugadores = jugadores;
		this.lago = lago;
		this.templo = templo;
		this.repartos = new Repartos();
		numBatalla = 1;
		reparto = null;

	}

	/**
	 * Constructor de la clase
	 * 
	 * @param bosque    Referencia al bosque que contiene todas las criaturas del
	 *                  juego.
	 * @param jugadores Referencia los jugadores que participane en el juego.
	 * @param templo    Referencia al templo maldito que visitar&aacute;n algunas
	 *                  criaturas.
	 * @param lago      Referencia al lago sagrado que visitar&aacute;n algunas
	 *                  criaturas.
	 * @param repartos  Referencia al reparto de criaturas por jugador y batalla.
	 */
	public Partida(Bosque bosque, Jugadores jugadores, TemploMaldito templo, LagoSagrado lago, Repartos repartos) {
		this.bosque = bosque;
		this.jugadores = jugadores;
		this.lago = lago;
		this.templo = templo;
		this.repartos = repartos;
		numBatalla = 1;
		reparto = null;
	}

	/**
	 * Env&iacute;a a todas las criaturas que no est&eacute;n neutralizadas a vsitar
	 * su lugar sagrado
	 */
	private void curarCriaturas() {
		for (String idJugador : jugadores.obtenerIds()) {
			ArrayList<Criatura> listaCriaturas = jugadores.obtenerPorId(idJugador).getListaCriaturas();
			for (Criatura criatura : listaCriaturas) {
				if (criatura.getSalud() > 0) {
					if (criatura.getClass().getInterfaces()[0].getName().equals("UsuarioLagoSagrado"))
						lago.visitarLugarSagrado(criatura);
					else
						templo.visitarLugarSagrado(criatura);

				}
			}
		}
	}

	/**
	 * Instrucci&oacute;n para el modelo de ejecuci&oacute;n por instrucciones para
	 * visitar el lugar sagrado.Muestra por la salida est&aacute;ndar el
	 * resultado de la operaci&oacute;n.
	 * <ul>
	 * <li>OK. Si se se produce la visita con &eacute;xito.</li>
	 * <li>FAIL. Si no existe la criatura especificada en el bosque.</li>
	 * </ul>
	 * 
	 * @param id Identificador de la criatura que visita el lugar sagrado.
	 */
	public void visitarLugarSagrado(String id) {
		Criatura criatura = bosque.buscaCriatura(id);
		if (criatura == null)
			System.out.println("VisitarLugarSagrado " + id + ": FAIL.");
		else {
			if (criatura.getClass().getInterfaces()[0].getName().equals("UsuarioLagoSagrado"))
				lago.visitarLugarSagrado(criatura);
			else
				templo.visitarLugarSagrado(criatura);

			System.out.println("VisitarLugarSagrado " + id + ": OK.");
		}
	}

	/**
	 * Devolver al bosque a todas las criaturas de los jugadores.
	 */
	private void devolverBosque() {
		for (String idJugador : jugadores.obtenerIds()) {
			ArrayList<Criatura> listaCriaturas = jugadores.obtenerPorId(idJugador).getListaCriaturas();
			for (Criatura criatura : listaCriaturas) {
				bosque.devolverCriatura(criatura);
			}
		}
	}

	/**
	 * Ejecuta una a una las instrucciones del fichero que se especifica en el objeto Parametros.
	 */
	public void ejecutarInstrucciones() {
		String linea = null;
		Scanner instrucciones = null;
		try {
			instrucciones = new Scanner(new FileInputStream(Parametros.getNombreInstrucciones()));
		} catch (FileNotFoundException e) {
			System.err.println("Error leyendo fichero de instrucciones");
			e.printStackTrace();
			System.exit(-1);
		}
		while (instrucciones.hasNextLine()) {
			linea = instrucciones.nextLine().trim();
			if (!(linea.isEmpty())) {
				if (!(linea.charAt(0) == '#')) {
					String[] partes = linea.split(" ");
					if (partes[0].equals("CargarJugadores"))
						jugadores.cargarJugadores(partes[1]);
					if (partes[0].equals("CrearJugador")) {
						String nombre = partes[2];
						for (int i = 3; i < partes.length; i++)
							nombre = nombre + " " + partes[i];
						jugadores.crearJugador(partes[1], nombre);
					}
					if (partes[0].equals("BorrarJugador"))
						jugadores.borrarJugador(partes[1]);
					if (partes[0].equals("VolcarJugadores"))
						jugadores.volcarJugadores(partes[1]);
					if (partes[0].equals("CargarCriaturas"))
						bosque.cargarCriaturas(partes[1]);
					if (partes[0].equals("CrearNinfa"))
						new Ninfa().crearNinfa(partes[1], partes[2], Integer.parseInt(partes[3]),
								Integer.parseInt(partes[4]), Integer.parseInt(partes[5]), Integer.parseInt(partes[6]),
								Integer.parseInt(partes[7]), bosque);
					if (partes[0].equals("CrearOrco"))
						new Orco().crearOrco(partes[1], partes[2], Integer.parseInt(partes[3]),
								Integer.parseInt(partes[4]), Integer.parseInt(partes[5]), bosque);
					if (partes[0].equals("CrearBruja"))
						new Bruja().crearBruja(partes[1], partes[2], Integer.parseInt(partes[3]),
								Integer.parseInt(partes[4]), Integer.parseInt(partes[5]), Integer.parseInt(partes[6]),
								bosque);
					if (partes[0].equals("CrearElfo"))
						new Elfo().crearElfo(partes[1], partes[2], Integer.parseInt(partes[3]),
								Integer.parseInt(partes[4]), Integer.parseInt(partes[5]), bosque);
					if (partes[0].equals("BorrarCriatura"))
						bosque.borrarCriatura(partes[1]);
					if (partes[0].equals("VolcarCriaturas"))
						bosque.volcarCriaturas(partes[1]);
					if (partes[0].equals("MostrarCriatura"))
						bosque.mostrarCriatura(partes[1]);
					if (partes[0].equals("Atacar"))
						bosque.atacar(partes[1], partes[2]);
					if (partes[0].equals("VisitarLugarSagrado"))
						visitarLugarSagrado(partes[1]);
					if (partes[0].equals("GenerarAsignacionCriaturas"))
						new Repartos().generarAsignacionCriaturas(Integer.parseInt(partes[1]), partes[2], bosque,
								jugadores);
					if (partes[0].equals("JugarPartida"))
						jugarPartida(partes[1], partes[2]);

				}
			}
		}

	}

	/**
	 * 
	 * Instrucci&oacute;n para el modelo de ejecuci&oacute;n por instrucciones para
	 * jugar una partida.Muestra por la salida est&aacute;ndar el
	 * resultado de la operaci&oacute;n.
	 * <ul>
	 * <li>OK. Si se se produce la visita con &eacute;xito.</li>
	 * <li>FAIL. Si no se puede leer el fichero de reparto o el fichero de salida.</li>
	 * </ul>
	 * 
	 * @param fReparto Nombre del archivo donde se encuentra el reparto de criaturas entre jugadores para cada batalla.
	 * @param fPartida Nombre del archivo donde se almacenar&aacute; la evoluci&oacute;n de la partida.
	 */
	public void jugarPartida(String fReparto, String fPartida) {
		// Los mensajes de existo fallo van a la salida estandar o su redirección
		int i = repartos.leerRepartos(fReparto, bosque);
		if (i == -1) {
			System.out.println("JugarPartida " + fReparto + " " + fPartida + ": FAIL.");
			return;
		}

		f_reparto = fReparto;

		PrintStream streamActual = System.out;
		PrintStream streamTemp = null;
		try {
			streamTemp = new PrintStream(new File(fPartida));
		} catch (FileNotFoundException e) {
			System.out.println("JugarPartida " + fReparto + " " + fPartida + ": FAIL.");
			return;
			// ----------------------------------------------PENDIENTE DE REVISION POR PARTE
			// DE LAURA
			/*
			 * e.printStackTrace(); System.exit(-1);
			 */
		}
		System.out.println("JugarPartida " + fReparto + " " + fPartida + ": OK.");

		// La evolucion de la partida se va a StreamTemp
		System.setOut(streamTemp);

		if (i == -2 || !jugadores.numeroCorrecto()) {
			System.out.println(
					"No se puede celebrar la partida porque el numero de criaturas o jugadores es incorrecto.");
			return;
		}

		while (!esFinPartida()) {
			prepararJugadores();
			Batalla batalla = new Batalla(jugadores);
			batalla.ejecutarBatalla(numBatalla++);
			curarCriaturas();
			devolverBosque();
		}
		System.out.println();
		System.out.println("VISITAS A LOS LUGARES SAGRADOS:");
		System.out.println(lago.devolverVisitas());
		System.out.println(templo.devolverVisitas());
		System.out.println();
		System.out.println("PUNTUACIONES:");
		jugadores.imprimirPuntosPartida();
		//System.out.println();
		System.setOut(streamActual);
		streamTemp.close();

	}

	/**
	 * Ejecuta una partida de MundoEncantado como una sucesi&oacute;n de la
	 * siguiente secuencia
	 * <ol>
	 * <li>Prepara los jugadores, reparti&eacute;ndole sus criaturas.</li>
	 * <li>Ejecuta una batalla.</li>
	 * <li>Las criaturas no neutralizadas visitan su Lugar Sagrado.</li>
	 * <li>Devuelve todas las criaturas al bosque.</li>
	 * </ol>
	 * mientras no se alcance el final de la partida, bien porque hay un ganador,
	 * bien porque no quedan criaturas sin neutralizar.
	 */
	public void ejecutarPartida() {
		compruebaFicherosModo1();
		while (!esFinPartida()) {
			prepararJugadores();
			Batalla batalla = new Batalla(jugadores);
			batalla.ejecutarBatalla(numBatalla++);
			curarCriaturas();
			devolverBosque();
		}

		System.out.println("VISITAS A LOS LUGARES SAGRADOS:");
		System.out.println(lago.devolverVisitas());
		System.out.println(templo.devolverVisitas());
		System.out.println();
		System.out.println("PUNTUACIONES:");
		jugadores.imprimirPuntosPartida();
		//System.out.println();
	}

	/**
	 * Determina si se dan las condiciones para terminar la partida, bien porque hay
	 * un ganador, bien porque no quedan criaturas sin neutralizar.
	 * 
	 * @return <b>true</b> si es el final de la partida y <b>false</b> en caso contrario.
	 */
	private boolean esFinPartida() {
		boolean finPartida = false;
		if (!(finPartida = bosque.todasNeutralizadas())) {
			for (String id : jugadores.obtenerIds()) {
				if (jugadores.obtenerPorId(id).ganadorPartida()) {
					finPartida = true;
					break;
				}
			}
		}
		return finPartida;
	}

	/**
	 * Para todos los jugadores de la partida, obtiene el reparto de criaturas a un
	 * jugador en la batalla en curso y se le asigna al jugador.
	 */
	private void prepararJugadores() {

		reparto = new Reparto();
		reparto.realizaReparto(f_reparto, bosque, jugadores, repartos, numBatalla);

		if (reparto.getReparto() == null) {
			System.out.println(
					"No se puede celebrar la partida porque el numero de criaturas o de jugadores es insuficiente.");
			System.exit(-1);
		}

		for (String idJugador : jugadores.obtenerIds()) {
			String[] asignacion = reparto.obtenerAsignacion(idJugador);
			if (asignacion == null) {
				System.out.println(
						"No se puede celebrar la partida porque las asignaciones a los jugadores son incorrectas");
				System.exit(-1);
			}
			ArrayList<Criatura> listaCriaturas = new ArrayList<Criatura>();
			for (String id : asignacion) {
				try {
					listaCriaturas.add(bosque.buscaCriatura(id));
				} catch (NullPointerException e) {
					break;
				}
			}
			jugadores.obtenerPorId(idJugador).setListaCriaturas(listaCriaturas);
		}
	}

	/**
	 * Para el MODO 1, comprueba que el contenido de los ficheros de lectura es
	 * correcto. Si no, aborta el programa - jugadores - criaturas - repartos (si
	 * hay fichero de repartos)
	 * 
	 */
	private void compruebaFicherosModo1() {
		if (jugadores.añadir(Parametros.getNombreJugadores()) == -1) {
			System.out.println("Error abriendo el fichero " + Parametros.getNombreJugadores() + ".");
			System.exit(-1);
		}
		if (bosque.añadir(Parametros.getNombreCriaturas()) == -1) {
			System.out.println("Error abriendo el fichero " + Parametros.getNombreCriaturas() + ".");
			System.exit(-1);
		}
		if (!jugadores.numeroCorrecto()) {
			System.out.println("No se puede celebrar la partida porque el numero de jugadores es incorrecto.");
			System.exit(-1);
		}
		if (bosque.bosqueVacio()) {
			System.out.println("No se puede celebrar la partida porque no hay criaturas en el bosque.");
			System.exit(-1);
		}
		if (Parametros.getNombreReparto() != null) {
			int i = repartos.leerRepartos(Parametros.getNombreReparto(), bosque);
			if (i == -1) {
				System.out.println("Error abriendo el fichero " + Parametros.getNombreReparto() + ".");
				System.exit(-1);
			} else if (i == -2) {
				System.out.println("No se puede celebrar la partida porque el contenido de "
						+ Parametros.getNombreReparto() + " es incorrecto.");
				System.exit(-1);
			}

		}

	}

}
