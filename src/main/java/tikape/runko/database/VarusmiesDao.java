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

    
    public Varusmies findOne(String hetu) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Varusmies WHERE hetu = ?");
        stmt.setString(1, hetu);

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

        ArrayList<Varusmies> varusmiehet = new ArrayList<>();
        String query = "SELECT * FROM Varusmies;";
        try (Connection conn = database.getConnection(); PreparedStatement statement = conn.prepareStatement(query); ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                varusmiehet.add(new Varusmies(rs.getString("nimi"), rs.getString("hetu")));
            }

        }
        return varusmiehet;
   
    }

    @Override
    public void delete(Integer hetu) throws SQLException {
        // ei toteutettu
    }

    @Override
    public Varusmies saveOrUpdate(Varusmies object) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Varusmies findOne(Integer hetu) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
