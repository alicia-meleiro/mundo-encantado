
import java.util.*;

/**
 * Cada lucha es un ataque de un jugador a otro donde:
 * <ul>
 * <li>Criatura atacante: la de mayor PO no neutralizada, la de menor ID si hay empate</li>
 * <li>Criatura defensora: la de mayor CD no neutralizada, la de menor ID si hay empate</li>
 * </ul>
 * La lucha provoca decremento en la salud y los atributos de las criaturas atacante y defensora
 * @author Laura Honrubia Baamonde y Alicia Meleiro Est&eacute;vez
 */

public class Lucha{
    private Jugador jugadorAtacante;
    private Jugador jugadorDefensor;

    /**
     * Constructor de una lucha, que consta de un jugador atacante y un jugador defensor
     * 
     * @param jugadorAtacante Jugador que realiza el ataque.
     * @param jugadorDefensor Jugador que se defiende.
     */
    public Lucha(Jugador jugadorAtacante, Jugador jugadorDefensor){
        this.jugadorAtacante = jugadorAtacante;
        this.jugadorDefensor = jugadorDefensor;
    }

    /**
     * Ejecucion de una lucha entre las criaturas elegidas entre las del jugador atacante y la del jugador defensor
     * 
     * @param numLucha N&uacute;mero secuencial de lucha.
     * @see eligeCriaturaAtacante
     * @see eligeCriaturaDefensora
     */
    public void ejecutaLucha(int numLucha){
        Criatura criaturaAtacante = eligeCriaturaAtacante(jugadorAtacante);
        Criatura criaturaDefensora = eligeCriaturaDefensora(jugadorDefensor);

        System.out.print("  LUCHA " +numLucha+ ": " +jugadorAtacante.getId()+ "-" +criaturaAtacante.lineaA1());
        System.out.println(" --> " +jugadorDefensor.getId()+ "-" +criaturaDefensora.lineaA1());

        criaturaAtacante.atacarA(criaturaDefensora);
    }

    
    /** 
     * Calcula la criatura que debe atacar en la lucha
     * 
     * @param atacante es el jugador atacante
     * @return Criatura atacante
     */
    private Criatura eligeCriaturaAtacante(Jugador atacante){
        List<Criatura> lc = new ArrayList<Criatura>(atacante.obtenerCriaturasActivas());
        Collections.sort(lc, Criatura.POComparator);
        
        return lc.get(0);
    }

    
    /** 
     * Calcula la criatura que debe defender en la lucha
     * 
     * @param defensor es el jugador defensor
     * @return Criatura defensora
     */
    private Criatura eligeCriaturaDefensora(Jugador defensor){
        List<Criatura> lc = new ArrayList<Criatura>(defensor.obtenerCriaturasActivas());
        Collections.sort(lc, Criatura.CDComparator);
        
        return lc.get(0);
    }
    

    
}