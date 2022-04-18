/**
 * Interfaz con el m&eacute;todo a implementar por todas las criaturas que visitan el Templo Maldito
 * @author Laura Honrubia Baamonde y Alicia Meleiro Est&eacute;vez
 *
 */
public interface UsuarioTemploMaldito  {

	/**
	 * Todos los usuarios deben saber orar en el templo.
	 * @param templo Referencia al Templo maldito.
	 */
	public abstract void orar(TemploMaldito templo);

}