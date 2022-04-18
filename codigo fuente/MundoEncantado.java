
import java.io.*;

/**
 * Aplicaci&oacute;n para el juego Mundo Encantado que contempla la existencia
 * de diferentes criaturas mitol&oacute;gicas y modela el combate entre varios
 * jugadores a los que se les asignan las mismas.
 * 
 * @author Laura Honrubia Baamonde y Alicia Meleiro Est&eacute;vez
 *
 */
public class MundoEncantado {

	/**
	 * M&eacute;todo principal. La aplicaci&oacute;n se puede invocar en dos modos:
	 * <ul>
	 * <li>Modo normal: <code>java MundoEncantado -j f_jugadores -c f_criaturas [-r f_reparto][-o f_partida]</code></li>
	 * <li>Modo ejecuci&oacute;n de instrucciones: <code>java MundoEncantado -i f_instrucciones [-o f_salida]</code></li>
	 * </ul>
	 * @param args Argumentos pasados en la l&iacute;nea de par&aacute;metros
	 */
	public static void main(String[] args) {
		int modoJuego;
		PrintStream consola = null;
		PrintStream o = null;
		Bosque bosque = new Bosque();
		LagoSagrado lago = new LagoSagrado();
		TemploMaldito templo = new TemploMaldito();
		Jugadores jugadores = new Jugadores();
		modoJuego = Parametros.leeInvocacion(args);
		// Para que detecte si hay fichero o no y redirige la salida standard al Stream.

		if (Parametros.getNombreSalida() != null) {
			try {
				o = new PrintStream(new File(Parametros.getNombreSalida()));
				consola = System.out;
				System.setOut(o);
			} catch (FileNotFoundException e) {
				System.err.println("Error abriendo fichero de salida");
				e.printStackTrace();
				System.exit(-1);
			}
		}
		Partida partida = new Partida(bosque, jugadores, templo, lago);
		if (modoJuego == 1)
			partida.ejecutarPartida();
		else if (modoJuego == 2)
			partida.ejecutarInstrucciones();
		else
			System.err.println("Modo de juego no válido");
		System.setOut(consola);

		if (o != null)
			o.close();

	}
}