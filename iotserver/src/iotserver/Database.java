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
    /**
     * Function that returns opened database object
     * 
     * This function is called before making any call to a 
     * sqlite3 database.After successful database operation, 
     * instance that is created should be closed with closeDB()
     * method.
     * 
     * @return database object 
     */
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
            if (conn != null) {
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
    /**
     * Function that closes database object passed to it
     * 
     * @param conn Database object 
     */
    private void closeDB(Connection conn) {
        try {
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Function that accesses database in order to verify combination of username and password
     * 
     * @param username Username for the user, as string
     * @param password Password for the user, as string
     * @return boolean value, true if username and password combination is correct
     */
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
    /**
     * Function that accesses database in order to add field to the system
     * 
     * @param fieldName Name of the field, as string
     * @param latitude Geographical latitude, as double number
     * @param longitude Geographical longitude, as double number
     * @return JSONObject containing data about entered field 
     */
    public JSONObject addField(String fieldName, double latitude, double longitude) {
        try {
            Connection db = openDB();
            String sql = "INSERT INTO field ('name','latitude','longitude') VALUES (?,?,?)";
            PreparedStatement statement = db.prepareStatement(sql);
            statement.setString(1, fieldName);
            statement.setDouble(2, latitude);
            statement.setDouble(3, longitude);
            statement.execute();
            closeDB(db);
            //return null;
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            if (ex.getErrorCode() == 19) {
                JSONObject err = new JSONObject();
                err.put("ERROR", "Field already exists in a database");
                return err;
            }
        }
        return getFieldData(fieldName);
    }
    /**
     * Function that accesses database in order to retreive information about field based on field name
     * 
     * @param fieldName Name of the field, as string
     * @return JSONObject containing data about entered field 
     */
    public JSONObject getFieldData(String fieldName) {
        JSONObject newFieldData = new JSONObject();
        try {
            Connection db = openDB();
            String sql = "SELECT * FROM 'field' WHERE name = '" + fieldName + "' ;";
            System.out.println(sql);
            Statement sqlQuery = db.createStatement();
            ResultSet results = sqlQuery.executeQuery(sql);
            newFieldData.put("fieldId", results.getInt("id"));
            newFieldData.put("name", results.getString("name"));
            newFieldData.put("latitude", results.getDouble("latitude"));
            newFieldData.put("longitude", results.getDouble("longitude"));
            closeDB(db);

        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return newFieldData;

    }
    /**
     * Function that accesses the database in order to return information about all fields stored in database
     * 
     * @return JSONArray ,containing JSONObject with data for each field stored in database 
     */
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