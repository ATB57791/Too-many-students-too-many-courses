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
import java.util.List;
import tikape.runko.*;

/**
 *
 * @author jessicakoski
 */
public class KayttooikeusDao implements Dao<Kayttooikeus, Integer> {
    private Database database;
    
    public KayttooikeusDao(Database database){
        this.database=database;
    }

    
    public List<Varusmies> findKo(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Kayttooikeus WHERE ase_aseenNumero = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }
        
        List<Varusmies> vm = new ArrayList<>();
        
        while (rs.next()) {
            Varusmies varusmies = (Varusmies) rs.getObject("varusmies_hetu");
            
            vm.add(varusmies);
        }
        
//        Ase ase = (Ase) rs.getObject("kurssi");
//        Varusmies varusmies = (Varusmies) rs.getObject("varusmies");
//        
//        Kayttooikeus ks = new Kayttooikeus(ase.getAsenumero(), varusmies.getHetu());
        
        rs.close();
        stmt.close();
        connection.close();
        
        return vm;
    }
    

    public List<Varusmies> aseeseenOikeutetut(Ase ase) throws SQLException {
        List<Varusmies> varusmiehet = new ArrayList<>();
        VarusmiesDao varusmiesDao = new VarusmiesDao(database);
        for(Kayttooikeus oikeus: database.getKayttooikeudet()){
            if(oikeus.getAseenNumero() == ase.getNumero())
            varusmiehet.add(varusmiesDao.findOne(oikeus.getHetu()));
        }
        return varusmiehet;
    }

    @Override
    public List<Kayttooikeus> findAll() throws SQLException {
        return database.getKayttooikeudet();
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
