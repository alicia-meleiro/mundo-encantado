

/**
 * Los elfos son un tipo de criatura que se baña en el Lago Sagrado al terminar una batalla
 * 
 * ATRIBUTOS
 * <ul>
 * <li>PO = (inteligencia * arco^2)/5 </li>
 * <li>CD = (inteligencia * coraza^2) / 10 </li>
 * </ul>
 * ATAQUE Y DEFENSA
 * Tras una lucha:
 * <ul>
 *<li>Al atacar: reducen su salud en 3 unidades, su arco en 1 unidad y su coraza en 1 unidad</li>
 *<li>Al defender: reducen su salud en 3 unidades, su arco en 1 unidad y su coraza en 1 unidad</li>
 * </ul>
 * BAÑO EN EL LAGO
 * <ul>
 * <li>salud = salud + 3*nVisitas</li>
 * <li>arco = arco + nVisitas/2</li>
 * <li>coraza = coraza + nVisitas/2</li>
 * </ul>  
 * 
 * @author Laura Honrubia Baamonde y Alicia Meleiro Est&eacute;vez
 */

public final class Elfo extends Criatura implements UsuarioLagoSagrado{
    int inteligencia;
    //private static final int INTELIGENCIA_MAX = 5;
    //private static final int INTELIGENCIA_MIN = 1;
    int arco; 
    private static final int ARCO_MAX = 5;
    private static final int ARCO_MIN = 2;
    int coraza;
    private static final int CORAZA_MAX = 5;
    private static final int CORAZA_MIN = 1;

    /**
     * Costructor completo de un elfo
     * 
	 * @param ID Identificador del elfo.
     * @param nombre Nombre del elfo.
     * @param inteligencia Valor del atributo inteligencia<code>(1-5)</code> del elfo.
     * @param arco Valor del atributo arco <code>(2-5)</code> del elfo.
     * @param coraza Valor del atributo coraza <code>(1-5)</code> del elfo.
     */
    public Elfo(String ID, String nombre, int inteligencia, int arco, int coraza){
        super(ID, nombre);
        this.inteligencia = inteligencia;
        this.arco = arco;
        this.coraza = coraza;
        calcularPoderOfensivo();
        calcularCapacidadDefensiva();
    }
    /**
     * Constructor vacio de un elfo se utiliza para las instrucciones de crear
	 * criaturas en el modo de ejecuci&oacute;n por instrucciones. Se difiere la
	 * asignaci&oacute;n de atributos.
     */
    public Elfo(){

    }

    /**
	 * Obtiene el tipo de criatura.
	 * 
	 * @return Cadena con el tipo, en este caso <b>E</b>
	 */
    @Override
	public String getTipo(){
        return "E";
    }
    
    /**
	 * Obtiene los atributos de la criatura.
	 * 
	 * @return Cadena con el identificador y los atributos del elfo.
	 */
    @Override
	public String getAtributos(){
        return super.getID() +","+ super.getNombre() +",I"+ inteligencia +",A"+ arco +",C"+ coraza;
    }

    /**
	 * Calcula el poder ofensivo del elfo y lo asigna a su atributo.
	 */
    @Override
	public void calcularPoderOfensivo(){
        super.poderOfensivo = (inteligencia * arco * arco)/5;
    }
    
    /**
	 * Calcula la capacidad defensiva del elfo y lo asigna a su atributo.
	 */
    @Override
	public void calcularCapacidadDefensiva(){
        super.capacidadDefensiva = (inteligencia * coraza * coraza)/10;
    }
    
    /**
	 * Implementa el ataque del elfo a otra criatura.
	 * 
	 * @param criatura Criatura a la que ataca el elfo.
	 */
    @Override
	public void atacarA(Criatura criatura){
        this.salud -= 3;
        if(salud < 0) this.salud = 0;
        if(arco - 1 >= ARCO_MIN) this.arco -= 1; else this.arco = ARCO_MIN;
        if(coraza - 1 >= CORAZA_MIN) this.coraza -= 1; else this.coraza = CORAZA_MIN;

        criatura.defenderDe(this);

        this.calcularPoderOfensivo();
        this.calcularCapacidadDefensiva();
    }

    /**
	 * Implementa la defensa del elfo del ataque de otra criatura
	 * 
	 * @param criatura Criatura de la que se defiende el elfo
	 */
    @Override
	public void defenderDe(Criatura criatura){
        int decremento = criatura.poderOfensivo - this.capacidadDefensiva;

        if(decremento > 0) this.salud = this.salud - 3 - decremento;
        else this.salud = this.salud - 3;

        if(salud < 0) this.salud = 0;
        if(arco - 1 >= ARCO_MIN) this.arco -= 1; else this.arco = ARCO_MIN;
        if(coraza - 1 >= CORAZA_MIN) this.coraza -= 1; else this.coraza = CORAZA_MIN;

        this.calcularPoderOfensivo();
        this.calcularCapacidadDefensiva();
    }

    /**
	 * Realiza los cambios que se producen en un elfo al ba&ntilde;arse en el Lago Sagrado
	 * <p>Se incrementan sus salud en 2*nVisitas</p>
	 * <p>Se incrementan sus atributos en:</p>
	 * <ul>
	 * <li>arco: nVisitas/2</li>
	 * <li>coraza: nVisitas/2</li>
	 * </ul>
	 */
    @Override
	public void baño(LagoSagrado l) {
		int n = l.numVisitas(this);
		salud = Math.min(SALUD_MAX,salud + 3 * n);
		arco = Math.min(ARCO_MAX, arco + n / 2);
		coraza = Math.min(CORAZA_MAX, coraza + n / 2);
		calcularPoderOfensivo();
		calcularCapacidadDefensiva();
    }
    
    /**
	 * MODO 2 DE EJECUCION (INSTRUCCION 9): crea un elfo. Muestra por la salida
	 * el resultado de la operacion:
	 * <ul>
	 * <li>OK si tuvo &eacute;xito la instrucci&oacute;n.</li>
	 * <li>FAIL si ya existe una criatura con ese ID.</li>
	 * </ul>
	 * 
	 * @param ID Identificador del elfo.
     * @param nombre Nombre del elfo.
     * @param inteligencia Valor del atributo inteligencia<code>(1-5)</code> del elfo.
     * @param arco Valor del atributo arco <code>(2-5)</code> del elfo.
     * @param coraza Valor del atributo coraza <code>(1-5)</code> del elfo.
     * @param bosque es el mapa con todas las criaturas de la partida.
	 */
    public void crearElfo(String ID, String nombre, int inteligencia, int arco, int coraza, Bosque bosque){

        System.out.print("CrearElfo " +ID+ " " +nombre+ " " +inteligencia+ " " +arco+ " " +coraza);

        if(bosque.existeCriatura(ID)){
            System.out.println(": FAIL.");
        }
        else{
            Criatura elfo = new Elfo(ID, nombre, inteligencia, arco, coraza);
            bosque.guardaCriatura(elfo);
            System.out.println(": OK.");
        }
    }

}