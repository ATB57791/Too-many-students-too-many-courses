
package tikape.runko.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import tikape.runko.*;

public class Database {

    private Connection connection;

    public Database(String databaseAddress) {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + databaseAddress);
            System.out.println("Opened database successfully");
            System.out.println("****************************");
        } catch (ClassNotFoundException | SQLException e) {
        }
        init();
    }

    public Connection getConnection() throws SQLException {
        return connection;
    }

    public void init() {
        try {
            if (!getVarusmiehet().isEmpty()) {
                return;
            }
        } catch (SQLException ex) {
        }
 
        try {
            createTable();
        } catch (SQLException e) {
            System.out.println("Unable to create table. Maybe it already exists.");
            return;
        }
 
        try {
            createData();
        } catch (SQLException ex) {
        }
    }
    
    public void createTable() throws SQLException {
        Statement stmt = connection.createStatement();
        String sql = "CREATE TABLE Varusmies "
                + "(hetu varchar(10) PRIMARY KEY,"
                + " nimi varchar(50))";
        stmt.executeUpdate(sql);
        sql = "CREATE TABLE Ase "
                + "(aseenNumero integer PRIMARY KEY,"
                + " Asetyyppi varchar(20))";
        stmt.executeUpdate(sql);
        sql = "CREATE TABLE Kayttooikeus "
                + "(varusmies_hetu varchar(10),"
                + " ase_aseenNumero integer,,"
                + " FOREIGN KEY (varusmies_hetu) REFERENCES Varusmies(hetu),"
                + " FOREIGN KEY (ase_aseenNumero) REFERENCES Ase(aseenNumero))";
        stmt.executeUpdate(sql);
        stmt.close();
        System.out.println("Tables created successfully");
    }
 
    public void createData() throws SQLException {
        connection.setAutoCommit(false);
        Statement stmt = connection.createStatement();
        String sql = "INSERT INTO Varusmies(hetu, nimi) VALUES ('060606A666R','Lucia Beelzebub');";
        stmt.executeUpdate(sql);
 
        sql = "INSERT INTO Varusmies(hetu, nimi) VALUES ('070707A7771','Hessus Nassez');";
        stmt.executeUpdate(sql);
 
        sql = "INSERT INTO Ase(aseenNumero, asetyyppi) VALUES (615667,'RK-62');";
        stmt.executeUpdate(sql);
 
        sql = "INSERT INTO Kayttooikeus(varusmies_hetu, ase_aseenNumero) VALUES ('060606A666R',615667);";
        stmt.executeUpdate(sql);
        
        sql = "INSERT INTO Kayttooikeus(varusmies_hetu, ase_aseenNumero) VALUES ('070707A7771',615667);";
        stmt.executeUpdate(sql);
 
        stmt.close();
        connection.commit();
    }
    
    public List<Varusmies> getVarusmiehet() throws SQLException {
        return queryAndCollect("SELECT * FROM Varusmies;", rs -> {
            return new Varusmies(rs.getString("nimi"), rs.getString("hetu"));
        });
    }
    
    public List<Ase> getAseet() throws SQLException {
        return queryAndCollect("SELECT * FROM Ase;", rs -> {
            return new Ase(rs.getString("Asetyyppi"), rs.getInt("aseenNumero"));
        });
    }
    
    public List<Kayttooikeus> getKayttooikeudet() throws SQLException {
        return queryAndCollect("SELECT * FROM Kayttooikeus;", rs -> {
            return new Kayttooikeus(rs.getString("ase_aseenNumero"), rs.getString("varusmies_hetu"));
        });
    }
    
    public <T> List<T> queryAndCollect(String query, Collector col) throws SQLException {
        List<T> rows = new ArrayList<>();
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        while (rs.next()) {
            rows.add((T) col.collect(rs));
        }
        rs.close();
        stmt.close();
        return rows;
    }
}
