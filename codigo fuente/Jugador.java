import java.util.*;

/**
 * Clase que modela un jugador en MundoEncantado. Cada jugador tiene un
 * identificador, un nombre, una lista de criaturas, y su puntuaci&oacute;n en
 * el juego.
 * 
 * @author Laura Honrubia Baamonde y Alicia Meleiro Est&eacute;vez
 *
 */
public class Jugador {
	/**
	 * Constante con la puntuaci&oacute;n m&aacute;xima de un jugador.
	 */
	private final static int PUNTOS_MAX = 10;
	/**
	 * Identificador del jugador.
	 */
	private String id;
	/**
	 * Nombre del jugador.
	 */
	private String nombre;
	/**
	 * Lista de criaturas que recibe del bosque para la batalla.
	 */
	private ArrayList<Criatura> listaCriaturas;
	/**
	 * Puntuaci&oacute;n obtenida en una batalla.
	 */
	private int puntos = 0;

	/**
	 * Constructor de un Jugador a partir de su identificador y nombre.
	 * 
	 * @param id     Cadena con el identificador del jugador.
	 * @param nombre Cadena con el nombre del jugador.
	 */
	public Jugador(String id, String nombre) {
		this.id = id;
		this.nombre = nombre;
		listaCriaturas = new ArrayList<Criatura>();
	}

	
	/*
	 * public Jugador() { } //ahora innecesario, antes usado en esFinBatalla()
	 */

	/*
	 * NOTA PARA LAURA: modifique esta parte para que en caso de que, por ejemplo,
	 * haya dos jugadores con 12 puntos, no se imprima VENCEDOR para nadie y se
	 * quede en empate
	 * 
	 * En el metodo Jugadores.imprimirPuntosPartida() llamo a un metodo llamado
	 * obtenerGanador() que hace esa operacion de ver si hay empate o no
	 */

	/**
	 * Devuelve una cadena con la puntuaci&oacute;n del jugador
	 * 
	 * @return Cadena con el identificador del jugador y su puntuaci&oacute;n
	 */
	public String devolverPuntuacion() {
		return "  " + nombre + " (" + id + ") = " + puntos;
	}

	/**
	 * Determina si el jugador tiene al menos una criatura no neutralizada, es decir
	 * cuya salud sea no nula.
	 * 
	 * @return <b>true</b> si el jugador aun se considera activo y <b>false</b> en
	 *         caso contrario.
	 */
	public boolean estaActivo() {
		boolean resultado = false;
		for (Criatura criatura : listaCriaturas) {
			if (criatura.getSalud() > 0)
				resultado = true;
		}
		return resultado;
	}

	/**
	 * Determina si un jugador ha alcanzado su puntuaci&oacute;n el valor
	 * m&aacute;ximo.
	 * 
	 * @return <b>true</b> si el jugador tiene la puntuaci&oacute;n m&aacute;xima y
	 *         <b>false</b> en caso contrario.
	 */
	public boolean ganadorPartida() {
		return (puntos >= PUNTOS_MAX);
	}

	/**
	 * Obtiene el identificador del Jugador.
	 * 
	 * @return Cadena con el identificador del Jugador.
	 */
	public String getId() {
		return id;
	}

	/**
	 * Obtiene la lista de las criaturas asignadas al jugador para una Batalla.
	 * 
	 * @return Lista de criaturas asignadas.
	 */
	public ArrayList<Criatura> getListaCriaturas() {
		return listaCriaturas;
	}

	/**
	 * Devuelve la lista de criaturas activas que le han sido repartidas al jugador.
	 * @return Lista de criaturas activas
	 */
	public ArrayList<Criatura> obtenerCriaturasActivas() {
		ArrayList<Criatura> listaActivas = new ArrayList<Criatura>();
		for (Criatura criatura : listaCriaturas)
			if (criatura.getSalud() > 0)
				listaActivas.add(criatura);
		return listaActivas;
	}

	/**
	 * Obtiene el nombre del Jugador.
	 * 
	 * @return Cadena con el nombre del Jugador.
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Obtiene la puntuaci&oacute;n acumulada por el jugador en una partida.
	 * 
	 * @return Puntuaci&oacute;n acumulada por el jugador.
	 */
	public int getPuntos() {
		return puntos;
	}

	/**
	 * Asigna una lista de criaturas al Jugador.
	 * 
	 * @param lista Lista de criaturas repartidas al jugador.
	 */
	public void setListaCriaturas(ArrayList<Criatura> lista) {
		listaCriaturas = lista;
	}

	/**
	 * Suma los puntos que recibe como par&aacute;metro al total del jugador.
	 * 
	 * @param incr Cantidad de puntos a incrementar la puntuaci&oacute;n del
	 *             jugador.
	 */
	public void sumarPuntos(int incr) {
		puntos = puntos + incr;
	}
}