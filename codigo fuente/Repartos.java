
import java.util.*;
import java.io.*;

/**
 * Esta clase modela un array de repartos, y puede existir dadas dos
 * situaciones:
 * <ul>
 * <li>Tras la lectura del fichero de repartos.</li>
 * <li>Tras le generacion de un numero de repartos aleatorios.</li>
 * </ul>
 * 
 * @author Laura Honrubia Baamonde y Alicia Meleiro Est&eacute;vez
 */

public class Repartos {
	private List<Reparto> repartos;

	/**
	 * Constructor de la clase
	 */
	public Repartos() {
		repartos = new ArrayList<Reparto>();
	}

	/**
	 * Si hay fichero de repartos, lee todos los repartos y los guarda en el array
	 * de repartos
	 * 
	 * @param nombreFichero Nombre del fichero del que se leen los repartos
	 * @param bosque        Mapa con las criaturas de la partida
	 * @return <b>-1</b> si no se ha podido leer el fichero,<b>-2</b> si no existe
	 *         alguna criatura o el fichero esta vacio, y <b>0</b> si todo es
	 *         correcto
	 */
	public int leerRepartos(String nombreFichero, Bosque bosque) {
		Scanner f_repartos = null;

		try {
			f_repartos = new Scanner(new FileInputStream(nombreFichero));
		} catch (FileNotFoundException e) {
			return -1;
		}

		String linea = null;

		if (!(f_repartos.hasNextLine())) {
			return -2;
		}
		while (f_repartos.hasNextLine()) {
			Reparto reparto = new Reparto();

			linea = f_repartos.nextLine();
			String partesXReparto[] = linea.split("},");

			int i = 0;
			while (i < partesXReparto.length) {
				Criatura[] criaturas = new Criatura[3];
				boolean correcto = true;

				if (partesXReparto[i].contains("}"))
					partesXReparto[i] = partesXReparto[i].replace("}", "");

				String partesXJugador[] = partesXReparto[i].split(":");

				/*
				 * Parseamos IDJugador y sus criaturas asociadas (entre 1 y 3) en el array
				 * IDCriaturas[]
				 */
				String IDJugador = partesXJugador[0];
				partesXJugador[1] = partesXJugador[1].replace("{", "");
				String IDCriaturas[] = partesXJugador[1].split(",");

				/*
				 * Cada reparto (cada linea) es un mapa cuyo key es IDJugador, al que van
				 * asociado las criaturas
				 */
				if (IDCriaturas.length >= 1 && IDCriaturas.length <= 3) {
					int k = 0;
					while (k < IDCriaturas.length && correcto) {
						if (bosque.existeCriatura(IDCriaturas[k])) {
							criaturas[k] = bosque.buscaCriatura(IDCriaturas[k]);
						} else
							correcto = false;
						k++;
					}

					/* Añadimos al reparto actual la asignacion actual */
					if (correcto) {
						reparto.añadirAsignacion(IDJugador, criaturas);
					} else {
						System.out.println("La criatura " + IDCriaturas[k] + " no existe en el bosque.");
						return -2;
					}
				}

				else {
					System.out.println("Se han asignado demasiadas criaturas a los jugadores.");
					return -2;
				}

				i++;
			}
			repartos.add(reparto);
		}

		return 0;

	}

	/**
	 * MODO 2 DE EJECUCION (INSTRUCCION 15): genera un &#60;fichero_reparto&#62; con
	 * &#60;num&#62; repartos aleatorios.Muestra por la salida el resultado de la
	 * operacion:
	 * <ul>
	 * <li>OK si tuvo &eacute;xito la instrucci&oacute;n.</li>
	 * <li>FAIL si no existen</li>
	 * <li>FAIL si no hay al menos tantas criaturas como jugadores</li>
	 * <li>FAIL si no se puede escribir en &#60;fichero_reparto&#62;</li>
	 * </ul>
	 * 
	 * @param num             numero de repartos a generar
	 * @param fichero_reparto fichero en el que imprimir el resultado
	 * @param bosque          es el mapa con las criaturas de la partida
	 * @param jugadores       es el mapa con los jugadores de la partida
	 */
	public void generarAsignacionCriaturas(int num, String fichero_reparto, Bosque bosque, Jugadores jugadores) {

		PrintWriter f_reparto = null;

		System.out.print("GenerarAsignacionCriaturas " + num + " " + fichero_reparto);

		if (jugadores == null) {
			System.out.println(": FAIL.");
			return;
		}

		try {
			f_reparto = new PrintWriter(new FileOutputStream(fichero_reparto));
		} catch (FileNotFoundException e) {
			System.out.println(": FAIL.");
			return;
		}

		for (int i = 0; i < num; i++) {
			Reparto aux = new Reparto();
			aux.setReparto(aux.repartoAleatorio(bosque, jugadores)); // Ahora en aux tenemos un reparto aleatorio

			if (aux.getReparto() == null) {
				System.out.println(": FAIL.");
				f_reparto.close();
				return;
			}

			for (String ID : aux.getKey()) {
				String[] IDcriaturas = aux.obtenerAsignacion(ID);

				for (String IDcriatura : IDcriaturas) {
					try {
						if (bosque.existeCriatura(IDcriatura)) {
							Criatura c = bosque.buscaCriatura(IDcriatura);
							bosque.devolverCriatura(c);
						}
					} catch (NullPointerException e) {
						break;
					}
				}

			}

			for (String ID : jugadores.obtenerIds()) {
				f_reparto.print(ID + ":{");

				Criatura[] criaturas = aux.buscaJugador(ID);

				for (int j = 0; j < criaturas.length; j++) {
					f_reparto.print(criaturas[j].getID());

					if (j < criaturas.length - 1) {
						f_reparto.print(",");
					} else {
						f_reparto.print("}");
					}
				}

				if (!(jugadores.esUltimo(ID)))
					f_reparto.print(",");
			}
			f_reparto.println();
		}

		f_reparto.close();
		System.out.println(": OK.");
	}



	/**
	 * Numero de repartos contenidos en el array de repartos
	 * 
	 * @return numero
	 */
	public int numRepartos() {
		return repartos.size();
	}

	/**
	 * Devuelve un reparto especifico en funcion del numero de batalla que se vaya a
	 * ejecutar y del numero de repartos existentes en el array
	 * 
	 * @param num es el numero de batalla que se va iniciar
	 * @return reparto buscado, el que se usara en la batalla
	 */
	public Map<String, Criatura[]> obtenerReparto(int num) {
		num--;
		int linea = num % repartos.size();
		Reparto aux = repartos.get(linea);

		return aux.getReparto();
	}

}
