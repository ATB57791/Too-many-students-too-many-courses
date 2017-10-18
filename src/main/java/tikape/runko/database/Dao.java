package tikape.runko.database;

import java.sql.*;
import java.util.*;

public interface Dao<Class, Key> {

    List<Class> findAll() throws SQLException;

    void delete(Key key) throws SQLException;

    Class saveOrUpdate(Class object) throws SQLException;

    Class findOne(Key key) throws SQLException;
}
