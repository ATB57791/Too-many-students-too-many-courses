package tikape.runko;

public class Varusmies implements Tulos {
    private String nimi;
    private String hetu;
    
    Varusmies(String nimi, String hetu) {
        this.hetu = hetu;
        this.nimi = nimi;
    }
    
    public String getNimi() {
        return nimi;
    }
    
    public String getHetu() {
        return hetu;
    }

    @Override
    public String getId() {
        return hetu;
    }
    
    public String toString() {
        return nimi + ", " + hetu;
    }
   
}
