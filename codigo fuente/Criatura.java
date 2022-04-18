
import java.util.*;

/**
 * Las criaturas habitan en el bosque. Se atacan y defienden entre ellas, tras
 * que lo que sus atributos decrementan Estan neutralizadas cuando su salud vale
 * <code>0</code>.
 * 
 * @author Laura Honrubia Baamonde y Alicia Meleiro Est&eacute;vez
 * 
 */
public abstract class Criatura implements Comparable<Criatura> {
	private String ID;
	private String nombre;
	protected int salud = SALUD_MAX;
	protected static final int SALUD_MAX = 10;
	protected static final int SALUD_MIN = 0;
	protected int poderOfensivo;
	protected int capacidadDefensiva;

	/**
	 * El ID de una criatura es unico para cada una
	 * 
	 * @param ID     Identificador de la criatura.
	 * @param nombre Nombre de la criatura.
	 */
	public Criatura(String ID, String nombre) {
		this.ID = ID;
		this.nombre = nombre;
	}

	/**
	 * Constructor vac&iacute;o, se utiliza para las instrucciones de crear
	 * criaturas en el modo de ejecuci&oacute;n por instrucciones. Se difiere la
	 * asignaci&oacute;n de atributos.
	 */
	public Criatura() {
	}

	/**
	 * Devuelve el identificador de la criatura.
	 * @return Cadena con el identificador de la criatura.
	 */
	public String getID() {
		return ID;
	}

	/**
	 * Devuelve el nombre de la criatura.
	 * @return Cadena con el nombre de la criatura.
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Devuelve la salud de una criatura.
	 * @return Valor de la salud de la criatura.
	 */
	public int getSalud() {
		return salud;
	}

	/**
	 * Devuelve el tipo de criatura que es cada una.
	 * 
	 * @return Cadena con el tipo de criatura
	 */
	public abstract String getTipo();

	/**
	 * Devuelve los atributos de la criatura sin Poder Ofensivo ni Capacidad defensiva.
	 * 
	 * @return String
	 */
	public abstract String getAtributos();

	/**
	 * Calcula el poder ofensivo de la criatura en funcion de su tipo.
	 */
	public abstract void calcularPoderOfensivo();

	/**
	 * Calcula la capacidad defensiva de la criatura en funcion de su tipo.
	 */
	public abstract void calcularCapacidadDefensiva();

	/**
	 * Crea un String con el formato especificado para la criatura, A1 + PO + CD +
	 * salud
	 * 
	 * @return String
	 */
	@Override
	public String toString() {
		return getTipo() + ":{" + getAtributos() + "," + poderOfensivo + "," + capacidadDefensiva + "," + salud + "}";
	}

	/**
	 * Realiza un ataque de esta criatura a la defensora, que resulta en la merma de la
	 * salud de ambas criaturas. Si la salud de una criatura es <code>0</code>, queda
	 * neutralizada.
	 * 
	 * @param criatura Criatura defensora
	 */
	public abstract void atacarA(Criatura criatura);

	/**
	 * La defensa va impl&iacute;cita en el proceso de ataque. La salud de la criatura
	 * defensora se reduce en un valor decremento, siempre que este sea positivo. Si
	 * no, no varia.
	 * 
	 * decremento = PO atacante - CD defensora
	 * 
	 * @param criatura Criatura atacante
	 */
	public abstract void defenderDe(Criatura criatura);

	/**
	 * MODO 2 DE EJECUCION (INSTRUCCION 12): muestra los datos de una criatura.
	 * Muestra por la salida el resultado de la operacion.
	 * <ul>
	 * <li>OK. Si se mostr&oacute; la criatura con &eacute;xito.</li>
	 * <li>FAIL. si no existe la criatura con ese ID.</li>
	 * </ul>
	 * 
	 * @param ID     ID de la criatura
	 * @param bosque mapa donde se hallan las criaturas
	 */
	public void mostrarCriatura(String ID, Bosque bosque) {
		if (bosque.existeCriatura(ID)) {
			System.out.println("MostrarCriatura" + ID + ": " + bosque.buscaCriatura(ID).toString());
		} else {
			System.out.println("MostrarCriatura " + ID + ": FAIL.");
		}
	}

	/**
	 * Ordena las criaturas por PODER OFENSIVO en orden decreciente
	 */
	public static Comparator<Criatura> POComparator = new Comparator<Criatura>() {
		@Override
		public int compare(Criatura c1, Criatura c2) {
			if (c1.poderOfensivo == c2.poderOfensivo) {
				return c1.compareTo(c2);
			} else {
				if (c1.poderOfensivo < c2.poderOfensivo) {
					return 1;
				} else {
					return -1;
				}
			}

		}
	};

	/**
	 * Ordena las criaturas por CAPACIDAD DEFENSIVA en orden decreciente
	 */
	public static Comparator<Criatura> CDComparator = new Comparator<Criatura>() {
		@Override
		public int compare(Criatura c1, Criatura c2) {
			if (c1.capacidadDefensiva == c2.capacidadDefensiva) {
				return c1.compareTo(c2);
			} else {
				if (c1.capacidadDefensiva < c2.capacidadDefensiva) {
					return 1;
				} else {
					return -1;
				}
			}

		}
	};

	/**
	 * Ordena las criaturas por nombre y en caso de nombres iguales por ID.
	 * 
	 * @param criatura criatura con la que this se compara
	 * @return <b>0</b> si son iguales, <b>1</b> si es mayor que <code>criatura</code> y <b>-1</b> si es menor que <code>criatura</code>.
	 */
	@Override
	public int compareTo(Criatura criatura) {
		if (this.getNombre().equals(criatura.getNombre())) {
			return this.getID().compareTo(criatura.getID());
		} else {
			return this.getNombre().compareTo(criatura.getNombre());
		}
	}

	/**
	 * Devuelve una cadena con el ID de la criatura y su salud
	 * 
	 * @return Cadena con el ID de la criatura y su salud.
	 */
	public String devolverIdSalud() {
		return "(" + this.ID + "," + this.salud + ")";
	}

	/**
	 * Devuelve una cadena con el tipo + atributos sin PO ni CD
	 * 
	 * @return String
	 */
	public String lineaA1() {
		return this.getTipo() + ":{" + this.getAtributos() + "}";
	}

}