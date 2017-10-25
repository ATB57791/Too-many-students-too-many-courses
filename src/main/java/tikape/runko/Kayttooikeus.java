package tikape.runko;

public class Kayttooikeus {
    private String id;
    private Varusmies varusmies;
    private Ase ase;
    
    public Kayttooikeus(String id, Varusmies varusmies, Ase ase) {
        this.varusmies = varusmies;
        this.ase = ase;
    }
    
    public String getId() {
        return id;
    }
    
    public Varusmies getVarusmies() {
        return varusmies;
    }
    
    public Ase getAse() {
        return ase;
    }
}
