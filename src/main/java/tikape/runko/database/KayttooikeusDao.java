/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static javassist.CtMethod.ConstParameter.integer;
import tikape.runko.*;

/**
 *
 * @author jessicakoski
 */
public class KayttooikeusDao implements Dao<Kayttooikeus, Integer> {

    private Database database;

    public KayttooikeusDao(Database database) {
        this.database = database;
    }

    public List<Varusmies> findKo(Ase ase) throws SQLException {
        List<Varusmies> vm = new ArrayList<>();

        try (Connection conn = database.getConnection(); PreparedStatement stmt = conn.prepareStatement("SELECT Varusmies.nimi, Varusmies.hetu FROM Kayttooikeus INNER JOIN Varusmies ON varusmies.hetu = Kayttooikeus.varusmies_hetu WHERE Kayttooikeus.ase_aseenNumero = ?;")) {
            stmt.setInt(1, ase.getNumero());
            try (ResultSet result = stmt.executeQuery()) {
                while (result.next()) {
                    vm.add(new Varusmies(result.getString("nimi"), result.getString("hetu")));
                }
                return vm;

            }
        }
    }

    public Map<String, ArrayList> pala(int a) throws SQLException {
        Map<String, ArrayList> map = new HashMap<>();
        ArrayList<String> s = new ArrayList<>();
        ArrayList<Varusmies> vm = new ArrayList<>();
    
    
        try (Connection conn = database.getConnection(); PreparedStatement statement = conn.prepareStatement(
                "SELECT nimi, hetu, aseTyyppi FROM Kayttooikeus INNER JOIN Varusmies ON hetu = varusmies_hetu INNER JOIN Ase ON aseenNumero = ase_aseenNumero WHERE ase_aseenNumero =?;")) {
            statement.setInt(1, a);
            try (ResultSet rs = statement.executeQuery();) {
                s.add(rs.getString("aseTyyppi"));
                map.put("1", s);
                while (rs.next()) {
                    vm.add(new Varusmies(rs.getString("nimi"), rs.getString("hetu")));
                }

            }
        }

        map.put("2", vm);
        return map;
    }

//        Ase ase = (Ase) rs.getObject("kurssi");
//        Varusmies varusmies = (Varusmies) rs.getObject("varusmies");
//        
//        Kayttooikeus ks = new Kayttooikeus(ase.getAsenumero(), varusmies.getHetu());
    /*
    public List<Varusmies> aseeseenOikeutetut(Ase ase) throws SQLException {
        
        List<Varusmies> varusmiehet = new ArrayList<>();
        List<Kayttooikeus> oikeudet = database.getKayttooikeudet();
        VarusmiesDao varusmiesDao = new VarusmiesDao(database);
        
        for(Kayttooikeus oikeus: oikeudet){
            if(Integer.parseInt(oikeus.getAseenNumero()) == ase.getNumero() and varusmiehet.contains())
            varusmiehet.add(varusmiesDao.findOne(oikeus.getHetu()));
        }
        return varusmiehet;
   
     */
    @Override
    public List<Kayttooikeus> findAll() throws SQLException {
      
        ArrayList<Kayttooikeus> oikeudet = new ArrayList<>();
        String query = "SELECT * FROM Kayttooikeus;";
        try (Connection conn = database.getConnection(); PreparedStatement statement = conn.prepareStatement(query); ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                oikeudet.add(new Kayttooikeus(rs.getString("varusmies_hetu"), rs.getString("ase_aseenNumero")));
            }

        }
        return oikeudet;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Kayttooikeus saveOrUpdate(Kayttooikeus object) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Kayttooikeus findOne(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
