
import java.util.ArrayList;
import java.util.TreeMap;

/**
 * Clase que modela el lugar sagrado donde los {@link UsuarioTemploMaldito} oran
 * para recuperarse.
 * 
 * @author Laura Honrubia Baamonde y Alicia Meleiro Est&eacute;vez
 *
 */
public class TemploMaldito extends LugarSagrado {

	/**
	 * Lista que registra todas las visitas al Templo Maldito
	 */
	private ArrayList<UsuarioTemploMaldito> libroVisitas;

	/**
	 * Constructor que inicializa el libro de visitas.
	 */
	public TemploMaldito() {
		libroVisitas = new ArrayList<UsuarioTemploMaldito>();
	}

	/**
	 * Devuelve una cadena con los identificadores de las criaturas que han visitado el lago sagrado y sus visitas.
	 * @return Cadena para imprimir los datos de las visitas.
	 */
	@Override
	public String devolverVisitas(){
		TreeMap<String, Integer> mapaVisitas = new TreeMap<String, Integer>();
		String cadena = "  Templo Maldito: {";
		for (UsuarioTemploMaldito tml: libroVisitas) {
			Criatura criatura = (Criatura)tml;
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
	 * Devuelve el n&uacute;mero de visitas que ha realizado al templo maldito una criatura.
	 * @param criatura Criatura que solicita el n&uacute;mero de visitas realizado.
	 * @return N&uacute;mero de visitas.
	 */
	@Override
	public int numVisitas(Criatura criatura) {
		int num = 0;
		for (UsuarioTemploMaldito v : libroVisitas) {
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
		return "Templo Maldito";
	}

	/**
	 *A&ntilde;ade una visita de la criatura al libro de visitas y le permite la oraci&oacute;n.
	 *@param criatura Criatura que visita el templo maldito.
	 */
	@Override
	public void visita(Criatura criatura) {
		libroVisitas.add((UsuarioTemploMaldito) criatura);
		((UsuarioTemploMaldito) criatura).orar(this);
	}
}