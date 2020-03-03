package iotserver;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Database {
    final boolean DEBUG = true;
    private Connection openDB() {
        try {
            Class.forName("org.sqlite.JDBC");
            String dbURL = "jdbc:sqlite:database.db";
            Connection conn = null;
            try {
                conn = DriverManager.getConnection(dbURL);
                return conn;
            } catch (SQLException ex) {
                Logger.getLogger(Iotserver.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (conn != null && this.DEBUG) {
                System.out.println("Connected to the database");
                DatabaseMetaData dm = (DatabaseMetaData) conn.getMetaData();
                System.out.println("Driver name: " + dm.getDriverName());
                System.out.println("Driver version: " + dm.getDriverVersion());
                System.out.println("Product name: " + dm.getDatabaseProductName());
                System.out.println("Product version: " + dm.getDatabaseProductVersion());
            }
        } catch (ClassNotFoundException | SQLException ex) {}
        return null;
    }

    private void closeDB(Connection conn) {
        try {
            conn.close();
            if(this.DEBUG){
                System.out.println("Closed connection to the database");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}