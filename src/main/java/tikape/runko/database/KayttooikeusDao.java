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

    public List<Varusmies> AseeseenOikeutetut(int aseenNumero) throws SQLException {//Varusmiehet, jotka ovat oikeutettuja kyseiseen aseeseen

        ArrayList<Varusmies> vm = new ArrayList<>();
        String query = "SELECT nimi, hetu FROM Kayttooikeus INNER JOIN Varusmies ON hetu = varusmies_hetu WHERE ase_aseenNumero =?;";

        try (Connection conn = database.getConnection(); PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, aseenNumero);
            try (ResultSet rs = statement.executeQuery();) {
                while (rs.next()) {
                    vm.add(new Varusmies(rs.getString("nimi"), rs.getString("hetu")));
                }
            }
        }

        return vm;
    }

    public List<Ase> AseetJoihinLupa(String hetu) throws SQLException {//Aseet joihin kyseisella varusmiehella on lupa

        ArrayList<Ase> aseet = new ArrayList<>();
        String query = "SELECT aseenNumero, aseTyyppi FROM Kayttooikeus INNER JOIN Ase ON aseenNumero = ase_aseenNumero WHERE varusmies_hetu =?;";

        try (Connection conn = database.getConnection(); PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, hetu);
            try (ResultSet rs = statement.executeQuery();) {
                while (rs.next()) {
                    aseet.add(new Ase(rs.getString("aseTyyppi"), rs.getInt("aseenNumero")));
                }
            }
        }

        return aseet;
    }

    @Override
    public List<Kayttooikeus> findAll() throws SQLException {

        ArrayList<Kayttooikeus> oikeudet = new ArrayList<>();
        String query = "SELECT * FROM Kayttooikeus;";
        try (Connection conn = database.getConnection(); PreparedStatement statement = conn.prepareStatement(query); ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                oikeudet.add(new Kayttooikeus(rs.getString("varusmies_hetu"), rs.getInt("ase_aseenNumero")));
            }

        }
        return oikeudet;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void delete(String hetu, Integer aseenNumero) throws SQLException {
        String query = "DELETE FROM Kayttooikeus WHERE varusmies_hetu=? AND ase_aseenNumero=?";
        
        try (Connection conn = database.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, hetu);
            stmt.setInt(2, aseenNumero);
            stmt.executeUpdate();
            stmt.close();
        }
    }

    @Override
    public Kayttooikeus saveOrUpdate(Kayttooikeus object) throws SQLException {
        delete(object.getHetu(), object.getAseenNumero());
        String query = "INSERT INTO Kayttooikeus (varusmies_hetu, ase_aseenNumero) VALUES (?, ?)";

        try (Connection conn = database.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, object.getHetu());
            stmt.setInt(2, object.getAseenNumero());
            stmt.executeUpdate();
        }
        return object;
    }

    @Override
    public Kayttooikeus findOne(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
