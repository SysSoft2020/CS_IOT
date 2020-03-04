package iotserver;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.*;
import org.json.simple.parser.*;

public class Database {
    final boolean DEBUG = true;
    private Connection openDB() {
        try {
            Class.forName("org.sqlite.JDBC");
            String dbURL = "jdbc:sqlite:database";
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
            if (this.DEBUG) {
                System.out.println("Closed connection to the database");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean authUser(String username, String password) {

        try {
            Connection db = openDB();
            String sql = "SELECT password FROM 'users' WHERE username = '" + username + "' ;";
            Statement sqlQuery = db.createStatement();
            ResultSet results = sqlQuery.executeQuery(sql);

            while (results.next()) {
                if (results.getString("password").equals(password)) return true;
            }
            closeDB(db);
            return false;
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public JSONObject addField(String fieldName, double latitude, double longitude) {
        try {
            Connection db = openDB();
            String sql = "INSERT INTO field ('name','latitude','longitude') VALUES (?,?,?)";
            PreparedStatement statement = db.prepareStatement(sql);
            statement.setString(1, fieldName);
            statement.setDouble(2, latitude);
            statement.setDouble(3, longitude);
            statement.executeUpdate();
            closeDB(db);
            return getFieldData(fieldName);
            //return null;
        } catch (SQLException ex) {

            if (ex.getErrorCode() == 19) {
                JSONObject err = new JSONObject();
                err.put("ERROR", "Field already exists in a database");
                return err;
            }
        }

        return null;
    }

    public JSONObject getFieldData(String fieldName) {
        try {
            Connection db = openDB();
            String sql = "SELECT * FROM 'field' WHERE name = '" + fieldName + "' ;";
            System.out.println(sql);
            Statement sqlQuery = db.createStatement();
            ResultSet results = sqlQuery.executeQuery(sql);
            JSONObject newFieldData = new JSONObject();
            newFieldData.put("fieldId", results.getInt("id"));
            newFieldData.put("name", results.getString("name"));
            newFieldData.put("latitude", results.getDouble("latitude"));
            newFieldData.put("longitude", results.getDouble("longitude"));
  

            closeDB(db);
            return newFieldData;

        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }

    public JSONArray getFieldData() {
        try {
            Connection db = openDB();
            String sql = "SELECT * FROM 'field' ;";
            System.out.println(sql);
            Statement sqlQuery = db.createStatement();
            ResultSet results = sqlQuery.executeQuery(sql);
            JSONArray data = new JSONArray();
            while (results.next()) {
                JSONObject newFieldData = new JSONObject();
                newFieldData.put("fieldId", results.getInt("id"));
                newFieldData.put("name", results.getString("name"));
                newFieldData.put("latitude", results.getDouble("latitude"));
                newFieldData.put("longitude", results.getDouble("longitude"));
                data.add(newFieldData);
            }
            closeDB(db);
            return data;
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }

}