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

public class VarusmiesDao implements Dao<Varusmies, String> {

    private Database database;

    public VarusmiesDao(Database database) {
        this.database = database;
    }

    @Override
    public Varusmies findOne(String hetu) throws SQLException {
        Varusmies o;
        try (Connection connection = database.getConnection(); PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Varusmies WHERE hetu = ?")) {
            stmt.setString(1, hetu);
            try (ResultSet rs = stmt.executeQuery()) {

                if (!rs.next()) {
                    return null;
                } else {
                    String id = rs.getString("hetu");
                    String nimi = rs.getString("nimi");
                    o = new Varusmies(nimi, id);
                    return o;
                }
            }
        }
        
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
    public void delete(String hetu) throws SQLException {
        String query="DELETE FROM Varusmies WHERE hetu=?";
        Connection conn = database.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, hetu);
            stmt.executeUpdate();
            stmt.close();
        }
    }

    @Override
    public Varusmies saveOrUpdate(Varusmies object) throws SQLException {
        delete(object.getHetu());
        String query = "INSERT INTO Varusmies (hetu, nimi) VALUES (?, ?)";
        Connection conn = database.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, object.getHetu());
            stmt.setString(2, object.getNimi());
            stmt.executeUpdate();
            stmt.close();
        }
        return object;
    }

}
