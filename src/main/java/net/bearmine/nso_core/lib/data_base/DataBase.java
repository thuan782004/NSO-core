package net.bearmine.nso_core.lib.data_base;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBase {
    public static File folder;
    private boolean connected = false;
    private Connection connection;
    public Statement statement;
    protected DataBase(String name,File folder){
        File base = new File(folder,name+".db");
        try {
            base.getParentFile().mkdirs();
            if (!base.exists()) if (!base.createNewFile()) {
                System.out.println("Couldn't create data base file");
                return;
            }
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + base);
            if (connection==null) {
                System.out.println("connection is null");
                return;
            }
            statement = connection.createStatement();
            connected = true;
            System.out.println("Connection to "+name+".db"+" was has been established");
        } catch ( ClassNotFoundException | SQLException | IOException e ) {
            logger.error(e);
        }
    }
    public void execute(String cmd){
        if (!connected) return;
        try { statement.execute(cmd);
        } catch ( SQLException e ) {
            logger.error(e);
        }
    }
    public DataTable getTable(String name){
        if (!connected) return null;
        return new DataTable(name,this);
    }
    public boolean isConnected() {
        return connected;
    }
    public Connection getConnection() {
        if (!connected) return null;
        return connection;
    }
}
