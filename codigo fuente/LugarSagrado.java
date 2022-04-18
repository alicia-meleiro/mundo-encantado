/**
 * Clase abstracta que modela un Lugar sagrado que se puede visitar y que informa del n&uacute;mero de visitas
 * @author Laura Honrubia Baamonde y Alicia Meleiro Est&eacute;vez
 *
 */
public abstract class LugarSagrado {

	/**
	 * Devuelve una cadena con los identificadores de las criaturas que han visitado el lugar sagrado y sus visitas.
	 * @return Cadena para imprimir los datos de las visitas.
	 */
	public abstract String devolverVisitas();

	/**
	 * Devuelve el n&uacute;mero de visitas que ha realizado al lugar sagrado una criatura.
	 * @param criatura Criatura que solicita el n&uacute;mero de visitas realizado.
	 * @return N&uacute;mero de visitas.
	 */
	public abstract int numVisitas(Criatura criatura);

	/**
	 *Devuelve una cadena con el tipo de lugar sagrado.
	 *@return  Tipo de lugar sagrado.
	 */
	public abstract String obtenerTipo();

	/**
	 *A&ntilde;ade una visita de la criatura al libro de visitas y le permite su uso.
	 *@param criatura Criatura que visita el lago sagrado.
	 */
	public abstract void visita(Criatura criatura);

	/**
	 *Visita al lugar sagrado.
	 *@param criatura Criatura que visita el lugar sagrado.
	 */
	public void visitarLugarSagrado(Criatura criatura) {
		visita(criatura);
	}

	
}