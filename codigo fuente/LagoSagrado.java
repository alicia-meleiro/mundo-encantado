import java.util.ArrayList;
import java.util.TreeMap;

/**
 * Clase que modela el lugar sagrado donde los {@link UsuarioLagoSagrado} se ba&ntilde;an para recuperarse.
 * @author Laura Honrubia Baamonde y Alicia Meleiro Est&eacute;vez
 */
public final class LagoSagrado extends LugarSagrado {

	/**
	 * Lista que registra todas las visitas al Lago Sagrado
	 */
	private ArrayList<UsuarioLagoSagrado> libroVisitas;

	/**
	 * Constructor que inicializa el libro de visitas.
	 */
	public LagoSagrado() {
		libroVisitas = new ArrayList<UsuarioLagoSagrado>();
	}

	/**
	 * Devuelve una cadena con los identificadores de las criaturas que han visitado el lago sagrado y sus visitas.
	 * @return Cadena para imprimir los datos de las visitas.
	 */
	@Override
	public String devolverVisitas(){
		TreeMap<String, Integer> mapaVisitas = new TreeMap<String, Integer>();
		String cadena = "  Lago Sagrado: {";
		for (UsuarioLagoSagrado usl: libroVisitas) {
			Criatura criatura = (Criatura)usl;
			mapaVisitas.put(criatura.getID(), numVisitas(criatura));
			}
		boolean EsPrimero = true;
		for(String id : mapaVisitas.keySet()) {
			if(!EsPrimero)
				cadena = cadena +", ";
			else
				EsPrimero = false;
			cadena = cadena + id + "=" + mapaVisitas.get(id);
		}
		cadena = cadena +"}";
		return cadena;
		
	}

	/**
	 * Devuelve el n&uacute;mero de visitas que ha realizado al lago sagrado una criatura.
	 * @param criatura Criatura que solicita el n&uacute;mero de visitas realizado.
	 * @return N&uacute;mero de visitas.
	 */
	@Override
	public int numVisitas(Criatura criatura) {
		int num = 0;
		for (UsuarioLagoSagrado v : libroVisitas) {
			if (v.equals(criatura))
				num++;
		}
		return num;
	}

	/**
	 *Devuelve una cadena con el tipo de lugar sagrado.
	 *@return  Tipo de lugar sagrado.
	 */
	@Override
	public String obtenerTipo() {
		return "Lago Sagrado";
	}

	/**
	 *A&ntilde;ade una visita de la criatura al libro de visitas y le permite el ba&ntilde;o.
	 *@param criatura Criatura que visita el lago sagrado.
	 */
	@Override
	public void visita(Criatura criatura) {
		libroVisitas.add((UsuarioLagoSagrado) criatura);
		((UsuarioLagoSagrado) criatura).baño(this);
	}
}