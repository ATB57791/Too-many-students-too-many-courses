package tikape.runko;

/**
 *
 * @author anton
 */
public class Kayttooikeus {
    private String aseenNumero;
    private String hetu;
    
    public Kayttooikeus(String numero, String hetu){
        this.aseenNumero=numero;
        this.hetu=hetu;
    }

    public String getAseenNumero() {
        return aseenNumero;
    }

    public String getHetu() {
        return hetu;
    }
    
}
