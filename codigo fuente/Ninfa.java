

/**
 * Las ninfas son un tipo de criatura que se baña en el Lago Sagrado al terminar una batalla
 * 
 * ATRIBUTOS
 * <ul>
 * <li>PO = divinidad * (velocidad + engaño) + varita/100</li>
 * <li>CD = divinidad * (velocidad + engaño) + armadura/200 </li>
 * </ul>
 * ATAQUE Y DEFENSA
 * Tras una lucha:
 * <ul>
 * <li>Al atacar: reducen su salud en 2 unidades y su varita en 5 unidades</li>
 * <li>Al defender: reducen su salud en 2 unidades y su armadura en 5 unidades</li>
 * </ul>
 * BAÑO EN EL LAGO
 * <ul>
 * <li>salud = salud + 2+nVisitas*2</li>
 * <li>varita = varita + nVisitas*3</li>
 * <li>armadura = armadura + nVisitas*3</li>
 * </ul>
 * @author Laura Honrubia Baamonde y Alicia Meleiro Est&eacute;vez
 */

public class Ninfa extends Criatura implements UsuarioLagoSagrado{

    private int divinidad;
    //private static final int DIVINIDAD_MAX = 2;
    //private static final int DIVINIDAD_MIN = 0;
    private int velocidad;
    //private static final int VELOCIDAD_MAX = 2;
    //private static final int VELOCIDAD_MIN = 0;
    private int engaño;
    //private static final int ENGAÑO_MAX = 1;
    //private static final int ENGAÑO_MIN = 0;
    private int varita;
    private static final int VARITA_MAX = 1000;
    private static final int VARITA_MIN = 0;
    private int armadura;
    private static final int ARMADURA_MAX = 1000;
    private static final int ARMADURA_MIN = 0;

    /**
     * Constructor completo de una ninfa
     * 
     * @param ID Identificador de la ninfa.
     * @param nombre Nombre de la ninfa.
     * @param divinidad Valor del atributo divinidad <code>(0-2)</code> de la ninfa.
     * @param velocidad Valor del atributo velocidad <code>(0-2)</code> de la ninfa.
     * @param engaño Valor del atributo enga&ntilde;o <code>(0-1)</code> de la ninfa.
     * @param varita Valor del atributo varita <code>(0-1000)</code> de la ninfa.
     * @param armadura Valor del atributo armadura <code>(0-1000)</code> de la ninfa.
     */
    public Ninfa (String ID, String nombre, int divinidad, int velocidad, int engaño, int varita, int armadura){
        super (ID, nombre);
        this.divinidad = divinidad;
        this.velocidad = velocidad;
        this.engaño = engaño;
        this.varita = varita;
        this.armadura = armadura;
        calcularPoderOfensivo();
        calcularCapacidadDefensiva();
    }
    /**
     * Constructor vacio de una ninfa
     */
    public Ninfa(){

    }
    
    /**
	 * Obtiene el tipo de criatura.
	 * 
	 * @return Cadena con el tipo, en este caso <b>N</b>
	 */
    @Override
	public String getTipo(){
        return "N";
    }
    
    /**
  	 * Obtiene los atributos de la criatura.
  	 * 
  	 * @return Cadena con el identificador y los atributos de la ninfa.
  	 */
    @Override
	public String getAtributos(){
        return super.getID() +","+ super.getNombre() +",D"+ divinidad +",V"+ varita +",R"+ velocidad +",E"+ engaño +",A"+ armadura;
    }

    /**
	 * Calcula el poder ofensivo de la ninfa y lo asigna a su atributo.
	 */
    @Override
	public void calcularPoderOfensivo(){
        super.poderOfensivo = (divinidad*(velocidad + engaño) + varita/100);
    }

    /**
	 * Calcula la capacidad defensiva de la ninfa y lo asigna a su atributo.
	 */
    @Override
	public void calcularCapacidadDefensiva(){
        super.capacidadDefensiva = (divinidad*(velocidad + engaño) + armadura/200);
    }
    
    /**
   	 * Implementa el ataque de la ninfa a otra criatura.
   	 * 
   	 * @param criatura Criatura a la que ataca la ninfa.
   	 */
    @Override
	public void atacarA(Criatura criatura){
        this.salud -= 2;
        if(salud < 0) this.salud = 0;
        if(varita - 5 >= VARITA_MIN) this.varita -= 5; else this.varita = VARITA_MIN;

        criatura.defenderDe(this);

        this.calcularPoderOfensivo();
        this.calcularCapacidadDefensiva();
    }

    /**
	 * Implementa la defensa dela ninfa del ataque de otra criatura
	 * 
	 * @param criatura Criatura de la que se defiende la ninfa
	 */
    @Override
	public void defenderDe(Criatura criatura){
        int decremento = criatura.poderOfensivo - this.capacidadDefensiva;

        if(decremento > 0) this.salud = this.salud - 2 - decremento;
        else this.salud -= 2;

        if(salud < 0) this.salud = 0;
        if(armadura - 5 >= ARMADURA_MIN) this.armadura -= 5; else this.armadura = ARMADURA_MIN;

        this.calcularPoderOfensivo();
        this.calcularCapacidadDefensiva();
    }

    /**
	 * Realiza los cambios que se producen en una ninfa al ba&ntilde;arse en el Lago Sagrado
	 * <p>Se incrementan sus salud en 2+2*nVisitas</p>
	 * <p>Se incrementan sus atributos en:</p>
	 * <ul>
	 * <li>varita: nVisitas*3</li>
	 * <li>armadura: nVisitas*3</li>
	 * </ul>
	 */
    @Override
	public void baño(LagoSagrado l) {
		int n = l.numVisitas(this);
		salud = Math.min(SALUD_MAX,salud + 2 + 2 * n);
		varita = Math.min(VARITA_MAX, varita + n * 3);
		armadura = Math.min(ARMADURA_MAX, armadura + n * 3);
		calcularPoderOfensivo();
		calcularCapacidadDefensiva();
    }
    
    /**
	 * MODO 2 DE EJECUCION (INSTRUCCION 6): crea una ninfa. Muestra por la salida
	 * el resultado de la operacion:
	 * <ul>
	 * <li>OK si tuvo &eacute;xito la instrucci&oacute;n.</li>
	 * <li>FAIL si ya existe una criatura con ese ID.</li>
	 * </ul>
	 * 
     * @param ID Identificador de la ninfa.
     * @param nombre Nombre de la ninfa.
     * @param divinidad Valor del atributo divinidad <code>(0-2)</code> de la ninfa.
     * @param velocidad Valor del atributo velocidad <code>(0-2)</code> de la ninfa.
     * @param engaño Valor del atributo enga&ntilde;o <code>(0-1)</code> de la ninfa.
     * @param varita Valor del atributo varita <code>(0-1000)</code> de la ninfa.
     * @param armadura Valor del atributo armadura <code>(0-1000)</code> de la ninfa.
     * @param bosque es el mapa con todas las criaturas de la partida
	 */
    public void crearNinfa(String ID, String nombre, int divinidad, int velocidad, int engaño, int varita, int armadura, Bosque bosque){

        System.out.print("CrearNinfa "+ID+ " " +nombre+ " " +divinidad+ " " +velocidad+ " " +engaño+ " " +varita+ " " +armadura);
        
        if (bosque.existeCriatura(ID)){
           System.out.println(": FAIL.");
        }
        else{
            Criatura ninfa = new Ninfa(ID, nombre, divinidad, velocidad, engaño, varita, armadura);
            bosque.guardaCriatura(ninfa);
            System.out.println(": OK.");
        }
    }

}