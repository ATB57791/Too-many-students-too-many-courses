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
import java.util.List;
import tikape.runko.domain.Kurssi;
import tikape.runko.domain.Kurssisuoritus;
import tikape.runko.domain.Opiskelija;

/**
 *
 * @author jessicakoski
 */
public class KurssisuoritusDao implements Dao<Kurssisuoritus, Integer> {
    private Database database;
    
    public KurssisuoritusDao(Database database){
        this.database=database;
    }

    @Override
    public Kurssisuoritus findOne(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Kurssi WHERE id = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }
        
        Kurssi kurssi = (Kurssi) rs.getObject("kurssi");
        Opiskelija opiskelija = (Opiskelija) rs.getObject("opiskelija");
        
        Kurssisuoritus ks = new Kurssisuoritus(kurssi, opiskelija);
        
        rs.close();
        stmt.close();
        connection.close();
        
        return ks;
    }

    @Override
    public List<Kurssisuoritus> findAll() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
}
