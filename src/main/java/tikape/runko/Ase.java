package tikapeWebSovellus;

public class Ase {
    private String nimi;
    private String asenumero;
    
    Ase(String nimi, String asenumero) {
        this.nimi = nimi;
        this.asenumero = asenumero;
    }
    
    public String getNimi() {
        return nimi;
    }
    
    public String getAsenumero() {
        return asenumero;
    }
}
