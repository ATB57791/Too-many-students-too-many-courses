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

public class VarusmiesDao implements Dao<Varusmies, Integer> {

    private Database database;

    public VarusmiesDao(Database database) {
        this.database = database;
    }

    
    public Varusmies findOne(String key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Varusmies WHERE hetu = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        String id = rs.getString("hetu");
        String nimi = rs.getString("nimi");

        Varusmies o = new Varusmies(nimi, id);

        rs.close();
        stmt.close();
        connection.close();

        return o;
    }

    @Override
    public List<Varusmies> findAll() throws SQLException {

//        Connection connection = database.getConnection();
//        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Varusmies");
//
//        ResultSet rs = stmt.executeQuery();
//        List<Varusmies> varusmiehet = new ArrayList<>();
//        while (rs.next()) {
//            String id = rs.getString("hetu");
//            String nimi = rs.getString("nimi");
//
//            varusmiehet.add(new Varusmies(nimi, id));
//        }
//
//        rs.close();
//        stmt.close();
//        connection.close();
//
//        return varusmiehet;
        return database.getVarusmiehet();
    }

    @Override
    public void delete(Integer key) throws SQLException {
        // ei toteutettu
    }

    @Override
    public Varusmies saveOrUpdate(Varusmies object) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Varusmies findOne(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
