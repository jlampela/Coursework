package com.viikko4;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import org.json.JSONObject;

public class CoordinateDB {

    private Connection conn = null;
    private static CoordinateDB dbInstance = null;

    private CoordinateDB(){
        try {
            open("CoordDB.db");
        } catch (SQLException e) {
            System.out.println("er");
        }
    }

    public static synchronized CoordinateDB getInstance(){
        if(dbInstance == null){
            dbInstance = new CoordinateDB();
        }
        return dbInstance;
    }

    public void open(String dbName) throws SQLException{

        File f = new File(dbName);

        if(!f.exists()){
            String url = "jdbc:sqlite:" + dbName;
            conn = DriverManager.getConnection(url);
            //System.out.println("database not found");
            initializeDatabase();
        }

    }

    public void closeDB() throws SQLException{
        if(conn != null){
            conn.close();
            conn = null;
            System.out.println("Connection closed");
        }
    }

    private boolean initializeDatabase() throws SQLException {
        if(conn != null){
            String createUserTable = "create table users (username varchar(50) NOT NULL, password varchar(50) NOT NULL, email varchar(50), primary key(username))";
            String createCoordTable = "create table coord (username varchar(50) NOT NULL, longitude decimal(18,15) NOT NULL, latitude decimal(15,13) NOT NULL, sent varchar(50), primary key(sent))";
            Statement st = conn.createStatement();;
            st.executeUpdate(createUserTable);
            st.executeUpdate(createCoordTable);
            st.close();
            System.out.println("tables created");
            return true;
        }
        return false;
    }

    public boolean addUser(JSONObject user) throws SQLException {
        if(checkIfUserExists(user.getString("username"))){
            return false;
        }

        String setUser = "insert into users " +
					"VALUES('" + user.getString("username") + "','" + user.getString("password") + "','" + user.getString("email") + "')";
        Statement createStatement;
        createStatement = conn.createStatement();
        createStatement.executeUpdate(setUser);
        createStatement.close();
        return true; 
    }
    
    public boolean checkIfUserExists(String username) throws SQLException{

        //System.out.println("checking if user in db");
        String checkUser = "SELECT username FROM users WHERE username = '" + username + "'";
        
        Statement queryStatement = conn.createStatement();
        ResultSet rs = queryStatement.executeQuery(checkUser);

        if(rs.next()){
            System.out.println("user found");
            return true;
        } else {
            System.out.println("not found");
            return false;
        }
    }

    public boolean authUser(String un, String pw) throws SQLException{
        Statement queryStatement = null;
        ResultSet rs;

        String getMSG = "select username, password from users where username = '" + un + "'";

        //System.out.println(un + " authissa " + getMSG);

        queryStatement = conn.createStatement();
        rs = queryStatement.executeQuery(getMSG);

        if(!rs.next()){
            System.out.println("user not found");
            return false;
        }else{
            String pass = rs.getString("password");
            if(pass.equals(pw)){
                System.out.println("name and pw correct");
                return true;
            }else{
                System.out.println("wrong password");
                return false;
            }
        }
    }

    public boolean addMessage(JSONObject msg) throws SQLException{
        String setMsg = "insert into coord " +
					"VALUES('" + msg.getString("username") + "','" + msg.getString("longitude") + "','" + msg.getString("latitude") + "','" + msg.getLong("sent") + "')";
        //System.out.println(setMsg);
        Statement createStatement;
        createStatement = conn.createStatement();
        createStatement.executeUpdate(setMsg);
        createStatement.close();
        return true; 
    }

    public boolean checkIsEmpty() throws SQLException{
        Statement s = conn.createStatement();
        ResultSet rs = s.executeQuery("select * from coord");
        if(rs.next() == false || rs.getMetaData().getColumnCount() < 1){
            System.out.println("No messages found");
            return true;
        }
        return false;
    }

    public JSONObject getMessages() throws SQLException{
        Statement st = null;
        JSONObject obj = new JSONObject();

        String getter = "select * from coord";

        st = conn.createStatement();
        ResultSet rs = st.executeQuery(getter);

        while(rs.next()){
            UserCoordinate tmp = new UserCoordinate();
            tmp.setSend(rs.getLong("sent"));
            ZonedDateTime zdt = ZonedDateTime.ofInstant(tmp.getTime(), ZoneOffset.UTC);
            obj.put("username", rs.getString("username"));
            obj.put("longitude", rs.getString("longitude"));
            obj.put("latitude", rs.getString("latitude"));
            obj.put("sent", zdt);
        }
        return obj;
    }
}
