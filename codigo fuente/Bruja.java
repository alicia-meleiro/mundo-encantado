
/**
 * Las brujass son un tipo de criatura que que ora en el Templo Maldito al
 * terminar una batalla
 * 
 * ATRIBUTOS 
 * <ul>
 * <li>PO = (sabiduría + magia) * bastón / 5  </li>
 * <li>CD = (sabiduría + magia) * vestido / 10 </li>
 * </ul>
 * 
 * ATAQUE Y DEFENSA Tras una lucha: 
 * <ul>
 * <li> Al atacar: reducen su salud en 2 unidades y su bastón en 1 unidad </li>
 * <li> Al defender: reducen su salud en 2 unidades y su vestido en 1 unidad </li>
 * </ul>
 * 
 * ORACION EN EL TEMPLO 
 * <ul>
 * <li>baston = (2 + nVisitas) / 2 </li>
 * <li>vestido = nVisitas/2 </li>
 * </ul>
 * 
 * @author Laura Honrubia Baamonde y Alicia Meleiro Estévez
 */

public final class Bruja extends Criatura implements UsuarioTemploMaldito {
	private int sabiduria;
	// private static final int SABIDURA_MAX = 5;
	// private static final int SABIDURIA_MIN = 1;
	private int magia;
	// private static final int MAGIA_MAX = 5;
	// private static final int MAGIA_MIN = 1;
	private int vestido;
	private static final int VESTIDO_MAX = 10;
	private static final int VESTIDO_MIN = 1;
	private int baston;
	private static final int BASTON_MAX = 10;
	private static final int BASTON_MIN = 1;

	/**
	 * Constructor completo de una bruja
	 * 
	 * @param ID        Identificador de la bruja.
	 * @param nombre    Nombre de la bruja.
	 * @param sabiduria Valor del atributo sabidur&iacute;a <code>(1-5)</code> de la bruja.
	 * @param magia     Valor del atributo magia <code>(1-5)</code> de la bruja.
	 * @param baston    Valor del atributo bast&oacute;n <code>(1-10)</code> de la bruja.
	 * @param vestido   Valor del atributo vestido <code>(1-10)</code>de la bruja.
	 */
	public Bruja(String ID, String nombre, int sabiduria, int magia, int baston, int vestido) {
		super(ID, nombre);
		this.sabiduria = sabiduria;
		this.magia = magia;
		this.baston = baston;
		this.vestido = vestido;
		calcularPoderOfensivo();
		calcularCapacidadDefensiva();
	}

	/**
	 * Constructor vacio de una bruja.se utiliza para las instrucciones de crear
	 * criaturas en el modo de ejecuci&oacute;n por instrucciones. Se difiere la
	 * asignaci&oacute;n de atributos.
	 */
	public Bruja() {
	}

	/**
	 * Obtiene el tipo de criatura.
	 * 
	 * @return Cadena con el tipo, en este caso <b>B</b>
	 */
	@Override
	public String getTipo() {
		return "B";
	}

	/**
	 * Obtiene los atributos de la criatura.
	 * 
	 * @return Cadena con el identificador y los atributos de la bruja.
	 */
	@Override
	public String getAtributos() {
		return super.getID() + "," + super.getNombre() + ",S" + sabiduria + ",M" + magia + ",B" + baston + ",V"
				+ vestido;
	}

	/**
	 * Calcula el poder ofensivo de la bruja y lo asigna a su atributo.
	 */
	@Override
	public void calcularPoderOfensivo() {
		super.poderOfensivo = (sabiduria + magia) * baston / 5;
	}

	/**
	 * Calcula la capacidad defensiva de la bruja y lo asigna a su atributo.
	 */
	@Override
	public void calcularCapacidadDefensiva() {
		super.capacidadDefensiva = (sabiduria + magia) * vestido / 10;
	}

	/**
	 * Implementa el ataque de la bruja a otra criatura.
	 * 
	 * @param criatura Criatura a la que ataca la bruja.
	 */
	@Override
	public void atacarA(Criatura criatura) {
		this.salud -= 2;
		if (salud < 0)
			this.salud = 0;
		if (baston - 1 >= BASTON_MIN)
			this.baston -= 1;
		else
			this.baston = BASTON_MIN;

		criatura.defenderDe(this);

		this.calcularPoderOfensivo();
		this.calcularCapacidadDefensiva();
	}

	/**
	 * Implementa la defensa de la bruja del ataque de otra criatura
	 * 
	 * @param criatura Criatura de la que se defiende la bruja
	 */
	@Override
	public void defenderDe(Criatura criatura) {
		int decremento = criatura.poderOfensivo - this.capacidadDefensiva;

		if (decremento > 0)
			this.salud = this.salud - 2 - decremento;
		else
			this.salud = this.salud - 2;

		if (salud < 0)
			this.salud = 0;
		if (vestido - 1 >= VESTIDO_MIN)
			this.vestido -= 1;
		else
			this.vestido = VESTIDO_MIN;

		this.calcularPoderOfensivo();
		this.calcularCapacidadDefensiva();
	}

	/**
	 * Realiza los cambios que se producen en una bruja al orar en el Templo Maldito
	 * Se incrementan sus atributos en:
	 * <ul>
	 * <li>bast&oacute;n m&aacute;gico: (2 + nVisitas)/2</li>
	 * <li>vestido: nVisitas/2</li>
	 * </ul>
	 */
	@Override
	public void orar(TemploMaldito t) {
		int n = t.numVisitas(this);
		baston = Math.min(BASTON_MAX, baston + (2 + n) / 2);
		vestido = Math.min(VESTIDO_MAX, vestido + n / 2);
		calcularPoderOfensivo();
		calcularCapacidadDefensiva();
	}

	/**
	 * MODO 2 DE EJECUCION (INSTRUCCION 8): crear una bruja. Muestra por la salida
	 * el resultado de la operacion:
	 * <ul>
	 * <li>OK si tuvo &eacute;xito la instrucci&oacute;n.</li>
	 * <li>FAIL si ya existe una criatura con ese ID.</li>
	 * </ul>
	 * 
	 * @param ID        Identificador de la bruja.
	 * @param nombre    Nombre de la bruja.
	 * @param sabiduria Valor del atributo sabidur&iacute;a <code>(1-5)</code> de la bruja.
	 * @param magia     Valor del atributo magia <code>(1-5)</code> de la bruja.
	 * @param baston    Valor del atributo bast&oacute;n <code>(1-10)</code> de la bruja.
	 * @param vestido   Valor del atributo vestido <code>(1-10)</code>de la bruja.
	 * @param bosque    Mapa con todas las criaturas de la partida.
	 */
	public void crearBruja(String ID, String nombre, int sabiduria, int magia, int baston, int vestido, Bosque bosque) {

		System.out.print(
				"CrearBruja " + ID + " " + nombre + " " + sabiduria + " " + magia + " " + baston + " " + vestido);

		if (bosque.existeCriatura(ID)) {
			System.out.println(": FAIL.");
		} else {
			Criatura bruja = new Bruja(ID, nombre, sabiduria, magia, baston, vestido);
			bosque.guardaCriatura(bruja);
			System.out.println(": OK.");
		}

	}

}