package tikape.runko.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import tikape.runko.*;

public class AseDao implements Dao<Ase, Integer> {

    private Database database;

    public AseDao(Database database) {
        this.database = database;
    }

    @Override
    public List<Ase> findAll() throws SQLException {
        ArrayList<Ase> aseet = new ArrayList<>();
        String query = "SELECT * FROM Ase";
        Connection conn = database.getConnection();
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                aseet.add(new Ase(rs.getString("aseTyyppi"), Integer.parseInt(rs.getString("aseenNumero"))));
            }
            statement.close();
        }
        return aseet;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        String query = "DELETE FROM Ase WHERE aseenNumero=?";
        Connection conn = database.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, key);
            stmt.executeUpdate();
            stmt.close();
        }
    }

    @Override
    public Ase saveOrUpdate(Ase object) throws SQLException {
        delete(object.getNumero());
        String query = "INSERT INTO Ase (aseenNumero, Asetyyppi) VALUES (?, ?)";
        Connection conn = database.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, object.getNumero());
            stmt.setString(2, object.getNimi());
            stmt.executeUpdate();
            stmt.close();
        }
        return object;
    }

    @Override
    public Ase findOne(Integer key) throws SQLException {
        Ase o;
        try (Connection connection = database.getConnection(); 
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Ase WHERE aseenNumero = ?")) {
            stmt.setInt(1, key);
            try (ResultSet rs = stmt.executeQuery()) {
                boolean hasOne = rs.next();
                if (!hasOne) {
                    return null;
                }
                Integer id = rs.getInt("aseenNumero");
                String nimi = rs.getString("Asetyyppi");
                o = new Ase(nimi, id);

            } catch (SQLException e) {
                return null;
            }
        } catch (SQLException e) {
            return null;
        }
        return o;
    }

}
