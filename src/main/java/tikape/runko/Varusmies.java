package tikapeWebSovellus;

public class Varusmies {
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
   
}
