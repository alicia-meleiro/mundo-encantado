

/**
 * Los orcos son un tipo de criatura que ora en el Templo Maldito al terminar una batalla
 * 
 * ATRIBUTOS
 * <ul>
 * <li>PO = (fuerza + garrote)/5</li>
 * <li>CD = (fuerza + escudo)/20</li>
 * </ul>
 * ATAQUE Y DEFENSA
 * Tras una lucha:
 * <ul>
 * <li>Al atacar: reducen su salud en 1 unidad y su garrote en 3 unidades</li>
 * <li>Al defender: reducen su salud en 3 unidades y escudo en 3 unidades</li>
 * </ul>
 * ORACION EN EL TEMPLO
 * <ul>
 * <li>garrote = garrote + 2*nVisitas</li>
 * <li>escudo = escudo + nVisitas</li>
 * </ul>
 * @author Laura Honrubia Baamonde y Alicia Meleiro Est&eacute;vez
 */

public class Orco extends Criatura implements UsuarioTemploMaldito{

    private int garrote;
    private static final int GARROTE_MAX = 90;
    private static final int GARROTE_MIN = 0;
    private int fuerza;
    //private static final int FUERZA_MAX = 10;
    //private static final int FUERZA_MIN = 1;
    private int escudo;
    private static final int ESCUDO_MAX = 90;
    private static final int ESCUDO_MIN = 0;

    /**
     * Constructor completo de un orco
     * 
     * @param ID Identificador del orco.
     * @param nombre Nombre del orco.
     * @param fuerza Valor del atributo fuerza <b>(0-10)</b> del orco.
     * @param garrote Valor del atributo garrote <b>(0-90)</b> del orco.
     * @param escudo Valor del atributo escudo <b>(0-90)</b> del orco.
     */
    public Orco (String ID, String nombre,int fuerza, int garrote, int escudo){
        super(ID, nombre);
        this.fuerza = fuerza;
        this.garrote = garrote;
        this.escudo = escudo;
        calcularPoderOfensivo();
        calcularCapacidadDefensiva();
    }

    /**
     * Constructor vacio de un orco
     */
    public Orco(){
        super();

    }

    /**
	 * Obtiene el tipo de criatura.
	 * 
	 * @return Cadena con el tipo, en este caso <b>O</b>
	 */
    public String getTipo(){
        return "O";
    }

    /**
	 * Obtiene los atributos de la criatura.
	 * 
	 * @return Cadena con el identificador y los atributos del orco.
	 */
    public  String getAtributos(){
        return super.getID() +","+ super.getNombre() +",F"+ fuerza +",G"+ garrote +",E"+ escudo;
    }

    /**
	 * Calcula el poder ofensivo del orco y lo asigna a su atributo.
	 */
    public void calcularPoderOfensivo(){
        super.poderOfensivo = (fuerza + garrote)/5;
    }

    /**
	 * Calcula la capacidad defensiva del orco y lo asigna a su atributo.
	 */
    public void calcularCapacidadDefensiva(){
        super.capacidadDefensiva = (fuerza + escudo)/20;
    }

    /**
	 * Implementa el ataque del orco a otra criatura.
	 * 
	 * @param criatura Criatura a la que ataca el orco.
	 */
    public void atacarA(Criatura criatura){
        this.salud -= 1;
        if(salud < 0) this.salud = 0;
        if(garrote - 3 >= GARROTE_MIN) this.garrote -= 3; else this.garrote = GARROTE_MIN;

        criatura.defenderDe(this);

        this.calcularPoderOfensivo();
        this.calcularCapacidadDefensiva();
    }

    /**
	 * Implementa la defensa del orco del ataque de otra criatura
	 * 
	 * @param criatura Criatura de la que se defiende el orco
	 */
    public void defenderDe(Criatura criatura){
        int decremento = criatura.poderOfensivo - this.capacidadDefensiva;

        if(decremento > 0) this.salud = this.salud - 3 - decremento;
        else this.salud = this.salud - 3;

        if(salud < 0) this.salud = 0;
        if(escudo - 3 >= ESCUDO_MIN) this.escudo -= 3; else this.escudo = ESCUDO_MIN;

        this.calcularPoderOfensivo();
        this.calcularCapacidadDefensiva();
    }
    
	/**
	 * Realiza los cambios que se producen en un orco al orar en el Templo Maldito
	 * Se incrementan sus atributos en:
	 * <ul>
	 * <li>garrote m&aacute;gico: 2*nVisitas</li>
	 * <li>escudo: nVisitas</li>
	 * </ul>
	 */
    public void orar(TemploMaldito t) {
		int n = t.numVisitas(this);
		garrote = Math.min(GARROTE_MAX, garrote + 2 * n);
		escudo = Math.min(ESCUDO_MAX, escudo + n);
		calcularPoderOfensivo();
		calcularCapacidadDefensiva();

    }
    
    /**
	 * MODO 2 DE EJECUCION (INSTRUCCION 7): crea un orco.Muestra por la salida
	 * el resultado de la operacion:
	 * <ul>
	 * <li>OK si tuvo &eacute;xito la instrucci&oacute;n.</li>
	 * <li>FAIL si ya existe una criatura con ese ID.</li>
	 * </ul>
	 * 
     * @param ID Identificador del orco.
     * @param nombre Nombre del orco.
     * @param fuerza Valor del atributo fuerza <b>(0-10)</b> del orco.
     * @param garrote Valor del atributo garrote <b>(0-90)</b> del orco.
     * @param escudo Valor del atributo escudo <b>(0-90)</b> del orco.
     * @param bosque Mapa con todas las criaturas de la partida.
	 */
    public void crearOrco(String ID, String nombre,int fuerza, int garrote, int escudo, Bosque bosque){

        System.out.print("CrearOrco " +ID+ " " +nombre+ " " +fuerza+ " " +garrote+ " " +escudo);

        if(bosque.existeCriatura(ID)){
            System.out.println(": FAIL.");
        }
        else{
            Criatura orco = new Orco(ID, nombre, fuerza, garrote, escudo);
            bosque.guardaCriatura((Criatura)orco);
            System.out.println(": OK.");
        }

    }

}

