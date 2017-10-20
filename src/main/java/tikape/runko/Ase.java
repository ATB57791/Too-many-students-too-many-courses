package tikape.runko;

public class Ase implements Tulos {
    private String nimi;
    private String numero;
    
    Ase(String nimi, String asenumero) {
        this.nimi = nimi;
        this.numero = asenumero;
    }
    
    public String getNimi() {
        return nimi;
    }
    
    public String getNumero() {
        return numero;
    }
    
    public String toString() {
        return nimi + ", " + numero;
    }

    @Override
    public String getId() {
        return numero;
    }
}
