
package tikape.runko.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import tikape.runko.*;

public class AseDao implements Dao<Ase, Integer>{
    private Database database;
    
    public AseDao(Database database){
        this.database=database;
    }

    @Override
    public List<Ase> findAll() throws SQLException {
        return database.getAseet();
    }

    @Override
    public void delete(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Ase saveOrUpdate(Ase object) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Ase findOne(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Ase WHERE aseenNumero = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        Integer id = rs.getInt("aseenNumero");
        String nimi = rs.getString("Asetyyppi");

        Ase o = new Ase(nimi, id);

        rs.close();
        stmt.close();
        connection.close();

        return o;
    }
    
}
