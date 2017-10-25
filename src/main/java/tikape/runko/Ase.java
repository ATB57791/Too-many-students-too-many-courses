package tikape.runko;

public class Ase {
    private String nimi;
    private int asenumero;
    
    public Ase(String nimi, int asenumero) {
        this.nimi = nimi;
        this.asenumero = asenumero;
    }
    
    public String getNimi() {
        return nimi;
    }
    
    public int getAsenumero() {
        return asenumero;
    }
}
