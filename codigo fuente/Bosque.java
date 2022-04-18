import java.util.*;
import java.io.*;

/**
 * Esta clase modela el bosque del MundoEncantado donde habitan las criaturas
 * que son repartidas a los jugadores en cada batalla, y al que son devueltas al
 * terminar la misma.
 * 
 * @author Laura Honrubia Baamonde y Alicia Meleiro Est&eacute;vez
 */
public class Bosque {

	/**
	 * Contiene las criaturas, donde la clave es el ID y Criatura contiene su
	 * informaci&oacute;n
	 */
	private Map<String, Criatura> mapaCriaturas;

	/**
	 * Contiene las criaturas asignadas a alg&uacute;n jugador
	 */
	private Set<Criatura> asignadas;

	/**
	 * Inicializa el objeto sin criaturas
	 */
	public Bosque() {
		mapaCriaturas = new TreeMap<String, Criatura>();
		asignadas = new HashSet<Criatura>();
	}

	/**
	 * Lee un conjunto de criaturas de un fichero y las a&ntilde;ade al bosque.
	 * 
	 * @param nombre_f Nombre del fichero donde se encuentran las criaturas.
	 * @return Si ha tenido exito <b>0</b>, <b>-1</b> si se produce un error y
	 *         <b>-2</b> si el fichero esta vac&iacute;o
	 */
	public int añadir(String nombre_f) {
		Scanner f_criaturas = null;

		try {
			f_criaturas = new Scanner(new FileInputStream(nombre_f));
		} catch (FileNotFoundException e) {
			return -1;
		}

		// Lectura linea a linea, finaliza cuando no haya mas lineas
		String linea = null;

		while (f_criaturas.hasNextLine()) {
			linea = f_criaturas.nextLine();

			if (linea.isEmpty()) {
				return -2;
			}

			String tipo = linea.substring(0, linea.indexOf(":"));
			String[] parts = linea.substring(linea.indexOf("{") + 1, linea.indexOf("}")).split(",");

			String ID = parts[0];
			String nombre = parts[1];

			// si existe se sobreescribe, en vez de no meterlo (put ya se encarga de
			// sobreescribir)

			switch (tipo) {
			case "N":
				int divinidad = Integer.parseInt(parts[2].replace("D", ""));
				int varita = Integer.parseInt(parts[3].replace("V", ""));
				int velocidad = Integer.parseInt(parts[4].replace("R", ""));
				int engano = Integer.parseInt(parts[5].replace("E", ""));
				int armadura = Integer.parseInt(parts[6].replace("A", ""));

				Ninfa ninfa = new Ninfa(ID, nombre, divinidad, velocidad, engano, varita, armadura);
				mapaCriaturas.put(ID, ninfa);
				break;

			case "O":
				int fuerza = Integer.parseInt(parts[2].replace("F", ""));
				int garrote = Integer.parseInt(parts[3].replace("G", ""));
				int escudo = Integer.parseInt(parts[4].replace("E", ""));

				Orco orco = new Orco(ID, nombre, fuerza, garrote, escudo);
				mapaCriaturas.put(ID, orco);
				break;

			case "E":
				int inteligencia = Integer.parseInt(parts[2].replace("I", ""));
				int arco = Integer.parseInt(parts[3].replace("A", ""));
				int coraza = Integer.parseInt(parts[4].replace("C", ""));

				Elfo elfo = new Elfo(ID, nombre, inteligencia, arco, coraza);
				mapaCriaturas.put(ID, elfo);
				break;

			case "B":
				int sabiduria = Integer.parseInt(parts[2].replace("S", ""));
				int magia = Integer.parseInt(parts[3].replace("M", ""));
				int baston = Integer.parseInt(parts[4].replace("B", ""));
				int vestido = Integer.parseInt(parts[5].replace("V", ""));

				Bruja bruja = new Bruja(ID, nombre, sabiduria, magia, baston, vestido);
				mapaCriaturas.put(ID, bruja);
				break;

			default:
				System.out.println("Tipo de criatura desconocido");
			}
		}
		return 0;
	}

	/**
	 * Marca una criatura como asignada a un jugador en una batalla.
	 * 
	 * @param criatura Criatura a incluir en las asignadas.
	 */
	public void asignarCriatura(Criatura criatura) {
		asignadas.add(criatura);
	}

	/**
	 * Instrucci&oacute;n para el modelo de ejecuci&oacute;n por instrucciones para
	 * borrar una criatura del bosque. Muestra por la salida est&aacute;ndar el
	 * resultado de la operaci&oacute;n.
	 * <ul>
	 * <li>OK. Si la criatura se elimin&oacute; del bosque con &eacute;xito.</li>
	 * <li>FAIL. Si la criatura no estaba en el bosque.</li>
	 * </ul>
	 * 
	 * @param id Identificador de la criatura a borrar.
	 */
	public void borrarCriatura(String id) {
		if (mapaCriaturas.remove(id) == null)
			System.out.println("BorrarCriatura " + id + ": FAIL.");
		else
			System.out.println("BorrarCriatura " + id + ": OK.");
	}

	/**
	 * Devuelve una <code>Criatura</code> a partir de su identificador si
	 * est&aacute; en el Bosque
	 * 
	 * @param id Identificador de la criatura
	 * @return Objeto Criatura si est&aacute; en el bosque y <b>null</b> si no
	 *         est&aacute; en el Bosque
	 */
	public Criatura buscaCriatura(String id) {
		if (mapaCriaturas.containsKey(id)) {
			Criatura c = mapaCriaturas.get(id);
			return c;
		} else
			return null;
	}

	/**
	 * Instrucci&oacute;n para el modelo de ejecuci&oacute;n por instrucciones para
	 * cargar las criaturas del bosque mediante un fichero con formato A1. Muestra
	 * por la salida est&aacute;ndar el resultado de la operaci&oacute;n.
	 * <ul>
	 * <li>OK. Si se pudo leer el archivo con las criaturas y crearlas en el
	 * bosque.</li>
	 * <li>FAIL. Si no se pudo leer el archivo de criaturas.</li>
	 * </ul>
	 * 
	 * @param nombreFichero Nombre del fichero donde se encuentran las criaturas
	 */
	public void cargarCriaturas(String nombreFichero) {
		if (añadir(nombreFichero) == 0) {
			System.out.println("CargarCriaturas " + nombreFichero + ": OK.");
		} else {
			System.out.println("CargarCriaturas " + nombreFichero + ": FAIL.");
		}
	}

	/**
	 * Crea una cadena con el formato especificado A1 para la criatura
	 * 
	 * @param criatura Objeto criatura a representar.
	 * @return Cadena con la descripci&oacute;n de la criatura en formato A1.
	 */
	public String creaLineaA1(Criatura criatura) {
		return criatura.getTipo() + ":{" + criatura.getAtributos() + "}";
	}

	/**
	 * Devuelve una criatura al bosque tras la batalla, desasign&aacute;ndola.
	 * 
	 * @param criatura Criatura que vuelve al bosque.
	 */
	public void devolverCriatura(Criatura criatura) {
		asignadas.remove(criatura);
	}

	/**
	 * Elige aleatoriamente un conjunto de criaturas de las libres en el bosque. No
	 * las retira del conjunto de criaturas libres por defecto.
	 * 
	 * @param numCriaturas N&uacute;mero de criaturas a elegir.
	 * @return Matriz de objetos Criatura elegidos.
	 */
	public ArrayList<Criatura> elegirCriaturas(int numCriaturas) {
		ArrayList<Criatura> asignacion = new ArrayList<Criatura>();

		// -----------------------------CODIGO NUEVO
		List<Criatura> mazo = new ArrayList<Criatura>();
		Set<String> keys = mapaCriaturas.keySet();
		for (String k : keys) {
			mazo.add(mapaCriaturas.get(k));
		}
		// ----------------------------CODIGO ANTIGUO
		// List<Criatura> mazo = (List<Criatura>) mapaCriaturas.values();
		// ----------------------------

		for (Criatura criatura : asignadas) {
			mazo.remove(criatura);
		} // asumiendo que quedan criaturas suficientes
		Collections.shuffle(mazo);
		try {
			for (int i = 0; i < numCriaturas; i++) {
				asignacion.add(mazo.get(i));

				// ----------------------------CODIGO NUEVO
				asignadas.add(mazo.get(i));
				// ---------------------------.CODIGO ANTINUO
			}
		} catch (IndexOutOfBoundsException e) {
			System.out.println("Intentando asignar demasiadas criaturas");
		}
		return asignacion;
	}

	/**
	 * Comprueba si la criatura ya est&aacute; en el bosque.
	 * 
	 * @param id Identificador de la criatura.
	 * @return Devuelve <b>true</b> si existe y <b>false</b> en caso contrario.
	 */
	public boolean existeCriatura(String id) {
		return (mapaCriaturas.containsKey(id));
	}

	/**
	 * Devuelve una referencia al mapa interno del bosque
	 * 
	 * @return mapa interno del bosque
	 */
	public Map<String, Criatura> getBosque() {
		return mapaCriaturas;
	}

	/**
	 * A&ntilde;ade una criatura al bosque almacenando una referencia al
	 * objeto.
	 * 
	 * @param criatura Criatura a a&ntilde;adir al bosque.
	 */
	public void guardaCriatura(Criatura criatura) {
		mapaCriaturas.put(criatura.getID(), criatura);
	}

	/**
	 * Devuelve el n&uacute;mero de criaturas libres (no asignadas) en el bosque.
	 * 
	 * @return n&uacute;mero de criaturas libres
	 */
	public int numCriaturasLibres() {
		return mapaCriaturas.size() - asignadas.size();
	}

	/**
	 * Comprueba si todas las criaturas del bosque est&aacute;n neutralizadas, es
	 * decir con la salud a <code>0</code>
	 * 
	 * @return <b>true</b> si est&aacute;n neutralizadas y <b>false</b> en caso
	 *         contrario.
	 */
	public boolean todasNeutralizadas() {
		boolean resultado = true;
		for (Criatura criatura : mapaCriaturas.values())
			if (criatura.getSalud() > 0) {
				resultado = false;
				break; // sale del bucle por que ya no hace falta buscar más
			}
		return resultado;
	}

	/**
	 * Instrucci&oacute;n para el modelo de ejecuci&oacute;n por instrucciones para
	 * volcar las criaturas del bosque a un fichero con formato A1. Muestra por la
	 * salida est&aacute;ndar el resultado de la operaci&oacute;n.
	 * 
	 * <ul>
	 * <li> OK.  Si se pudo generar un archivo con todas las criaturas del bosque y el nombre especificado.</li>
	 * <li> FAIL. Si no se pudo escribir el archivo especificado.</li>
	 * </ul>
	 * @param nombreFichero nombre del fichero donde se van a guardar las criaturas
	 */
	public void volcarCriaturas(String nombreFichero) {
		PrintWriter salida = null;
		try {
			salida = new PrintWriter(new FileOutputStream(nombreFichero));
			for (String id : mapaCriaturas.keySet()) {
				String linea = creaLineaA1(mapaCriaturas.get(id));
				salida.println(linea);
			}
		} catch (FileNotFoundException e) {
			System.out.println("VolcarCriaturas " + nombreFichero + ": FAIL.");
		} finally {
			salida.close();
		}
		System.out.println("VolcarCriaturas " + nombreFichero + ": OK.");
	}

	/**
	 * Instrucci&oacute;n para el modelo de ejecuci&oacute;n por instrucciones para
	 * mostrar una criaturas del bosque. Muestra por la salida est&aacute;ndar el
	 * resultado de la operaci&oacute;n.
	 * <ul>
	 * <li>OK. Si se mostr&oacute; con &eacute;xito la criatura.</li>
	 * <li>FAIL. Si no existe la criatura especificada en el bosque.</li>
	 * </ul>
	 * 
	 * @param id Identificador de la criatura a mostrar
	 */
	public void mostrarCriatura(String id) {
		Criatura criatura = buscaCriatura(id);
		if (criatura == null)
			System.out.println("MostrarCriatura " + id + ": FAIL.");
		else
			System.out.println("MostrarCriatura " + id + ": " + buscaCriatura(id));
	}

	/**
	 * Instrucci&oacute;n para el modelo de ejecuci&oacute;n por instrucciones para
	 * Atacar una criatura a otra a partir de sus identificadores. Muestra por la
	 * salida est&aacute;ndar el resultado de la operaci&oacute;n.
	 * 
	 * <ul>
	 * <li>OK. Si se pudo realizar el combate entre ambas criaturas.</li>
	 * <li>FAIL. Si no existe alguna de las dos criaturas especificadas.</li>
	 * </ul>
	 * @param idAtacante Identificador del jugador atacante.
	 * @param idDefensor Identificador del jugador defensor.
	 */
	public void atacar(String idAtacante, String idDefensor) {
		Criatura cAtacante = buscaCriatura(idAtacante);
		Criatura cDefensor = buscaCriatura(idDefensor);

		if (cAtacante != null && cDefensor != null) {
			cAtacante.atacarA(cDefensor);
			System.out.println("Atacar " + idAtacante + " " + idDefensor + ": OK.");
		} else
			System.out.println("Atacar " + idAtacante + " " + idDefensor + ": FAIL.");

	}

	/**
	 * M&eacute;todo para comprobar si el bosque tiene criaturas.
	 * 
	 * @return <b>true</b> si el bosque est&aacute; vac&iacute;o.
	 */
	public boolean bosqueVacio() {
		return mapaCriaturas.values().isEmpty();
	}

}