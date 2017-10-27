package tikape.runko;

/**
 *
 * @author anton
 */
public class Kayttooikeus {
    private int aseenNumero;
    private String hetu;
    
    public Kayttooikeus(String hetu, int numero){
        this.aseenNumero=numero;
        this.hetu=hetu;
    }

    public int getAseenNumero() {
        return aseenNumero;
    }

    public String getHetu() {
        return hetu;
    }
    
}
