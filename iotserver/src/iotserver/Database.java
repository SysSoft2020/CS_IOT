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
import org.sqlite.SQLiteConfig;

public class Database {
    /**
     * Function that returns opened database object.
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
                SQLiteConfig dbConfig = new SQLiteConfig();
                dbConfig.enforceForeignKeys(true);
                conn = DriverManager.getConnection(dbURL,dbConfig.toProperties());
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
     * Function that closes database object passed to it.
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
     * Function that accesses database in order to verify combination of username and password.
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
     * Function that accesses database in order to add field to the system.
     * 
     * @param fieldName Name of the field, as string
     * @param latitude Geographical latitude, as double number
     * @param longitude Geographical longitude, as double number
     * @return JSONObject containing data about entered field 
     */
    public JSONObject addField(String fieldName, double latitude, double longitude) {
        Connection db = openDB();
        try {
            String sql = "INSERT INTO field ('name','latitude','longitude') VALUES (?,?,?)";
            PreparedStatement statement = db.prepareStatement(sql);
            statement.setString(1, fieldName);
            statement.setDouble(2, latitude);
            statement.setDouble(3, longitude);
            statement.execute();
            //return null;
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            if (ex.getErrorCode() == 19) {
                JSONObject err = new JSONObject();
                err.put("ERROR", "Field already exists in a database");
                return err;
            }
        }
        JSONObject obj = getFieldData(db,fieldName);
        closeDB(db);
        return obj;
    }
    
    /**
     * Function that enters weather station data to a weatherStationData table.
     * 
     * Data entered to a table depends on the arguments provided to this method.
     * Function returns true if data is inserted successfully. First parameter,
     * weatherStationId must be a valid ID number of previously present weather
     * station in weatherStation table, otherwise insert will fail (FOREIGN KEY
     * CONSTRAINT).
     * 
     * @param weatherStationId ID number of previously existing weather station
     * @param temperature 
     * @param barometricPressure
     * @param windSpeed
     * @param relativeHumidity
     * @param airQualityIndex
     * @return Boolean value, true if data is inserted successfully, otherwise false
     */
    public boolean addWeatherStationData(long weatherStationId , double temperature, double barometricPressure, double windSpeed, double relativeHumidity, long airQualityIndex){
        
        Connection db = openDB();
        String sql = "INSERT INTO weatherStationData ('weatherStation','temperature','barometricPressure','windSpeed','relativeHumidity','airQualityIndex') VALUES (?,?,?,?,?,?)";
        try {
            PreparedStatement statement = db.prepareStatement(sql);
            statement.setLong(1, weatherStationId);
            statement.setDouble(2,temperature);
            statement.setDouble(3,barometricPressure);
            statement.setDouble(4,windSpeed);
            statement.setDouble(5, relativeHumidity);
            statement.setLong(6,airQualityIndex);
            int executeUpdate = statement.executeUpdate();
            return true;
        } catch (SQLException ex) {
            System.out.println("Error is: " + ex.getErrorCode());
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    /**
     * Function that accesses database in order to retrieve information about field based on field name.
     * 
     * Use this function when you want to return only one row from any given
     * table in the database, i.e. SELECT * FROM field WHERE id = 1;.
     * 
     * @param fieldName Name of the field, as string
     * @return JSONObject containing data about specified field 
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
     * Function that accesses database in order to retrieve information about field based on field name.
     * 
     * Use this function if you want to return data after you insert it into
     * a database and when you want to return only one row from any given
     * table in the database, i.e. SELECT * FROM field WHERE id = 1;.
     * 
     * @param db Database object, must be previously open
     * @param fieldName Name of the field, as string
     * @return JSONObject containing data about specified field
     */
    public JSONObject getFieldData(Connection db,String fieldName) {
        JSONObject newFieldData = new JSONObject();
        try {
            String sql = "SELECT * FROM 'field' WHERE name = '" + fieldName + "' ;";
            System.out.println(sql);
            Statement sqlQuery = db.createStatement();
            ResultSet results = sqlQuery.executeQuery(sql);
            newFieldData.put("fieldId", results.getInt("id"));
            newFieldData.put("name", results.getString("name"));
            newFieldData.put("latitude", results.getDouble("latitude"));
            newFieldData.put("longitude", results.getDouble("longitude"));

        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return newFieldData;

    }
    /**
     * Function that accesses the database in order to return information about all fields stored in database.
     * 
     * Use this function when you want to obtain more that one row from the table,
     * i.e. on SELECT * FROM field;.
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