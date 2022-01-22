package net.bearmine.nso_core.lib.data_base;

import java.sql.ResultSet;
import java.sql.SQLException;

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
            return dataBase.getConnection().createStatement().executeQuery("PRAGMA table_info( "+name+" );");
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
            if (s!=null) return s;
        }
        return null;
        } catch ( SQLException e ) {
            logger.error(e);
            return null;
        }
    }
    public void setString(String key,String column,String value){
        if (hasColumn(column)) dataBase.execute("ALTER TABLE "+name+" ADD COLUMN "+column+" VARCHAR(255);");
        dataBase.execute("INSERT OR IGNORE INTO "+name+" (ikey) VALUES ('"+key+"');");
        dataBase.execute("UPDATE "+name+" set "+column+" = '"+value+"' WHERE ikey = '"+key+"';");
    }
}
