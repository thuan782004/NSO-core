package net.bearmine.nso_core.lib.data_base;

import java.nio.charset.StandardCharsets;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;

public class DataTable {

    private final DataBase dataBase;
    private final String name;

    protected DataTable(String name,DataBase base){
        this.name = name;
        dataBase = base;
        dataBase.execute("CREATE TABLE IF NOT EXISTS "+name+" (ikey VARCHAR(255) PRIMARY KEY NOT NULL);");
    }

    public ResultSet get(String key){
        try {
            return dataBase.statement.executeQuery("SELECT * FROM "+name+" WHERE ikey = '"+key+"';");
        } catch ( SQLException e ) {
            logger.error(e);
            return null;
        }
    }
    public ResultSet getInfo(){
        try {
            return dataBase
                    .getConnection()
                    .createStatement()
                    .executeQuery("PRAGMA table_info( "+
                            name +" );");
        } catch ( SQLException e ) {
            logger.error(e);
            return null;
        }
    }
    public boolean hasColumn(String column){
        ResultSet info = getInfo();
        try {
            while (info.next()) {
                if (info.getString(2).equals(column)) return false;
            }
            return true;
        } catch ( SQLException e ) {
                logger.error(e);
            return true;
        }
    }
    public DataColumn getColumn(String column){
        return new DataColumn(column,this);
    }
    public String getString(String key,String column){
        if (hasColumn(column)) return null;
        ResultSet result = get(key);
        try {while (result.next()) {
            String s = result.getString(column);
            if (s!=null&&!s.equals(""))
            return isInt(s)?s:new String(decoder.decode(s));
        }
        return null;
        } catch ( SQLException e ) {
            logger.error(e);
            return null;
        }
    }
    public static final Base64.Encoder encoder = Base64.getEncoder();
    public static final Base64.Decoder decoder = Base64.getDecoder();
    public void setString(String key,String column,String value){
        if (value!=null&&!isInt(value))
        value = encoder.encodeToString(value.getBytes(StandardCharsets.UTF_8));
        if (hasColumn(column)) dataBase.execute("ALTER TABLE "+name+" ADD COLUMN "+column+" VARCHAR(255);");
        dataBase.execute("INSERT OR IGNORE INTO "+name+" (ikey) VALUES ('"+key+"');");
        dataBase.execute("UPDATE "+name+" set "+column+" = '"+value+"' WHERE ikey = '"+key+"';");
    }
    public boolean isInt(String s){
        try {
            Integer.parseInt(s);
            return true;
        }catch ( NumberFormatException e ){
            return false;
        }
    }
}
