package net.bearmine.nso_core.lib.data_base;

import me.funayd.funaydsystem.FunaydSystem;

import java.io.File;
import java.util.HashMap;

public class DataManager {
    private static final HashMap<String,DataBase> dataBase = new HashMap<>();
    private static final HashMap<String,DataTable> dataTable = new HashMap<>();

    private static final String defaultDataBase = "data";
    private static final File defaultDataFolder = new File(FunaydSystem.instant.getDataFolder(),"data");

    public static DataBase getBase(){
        return getBase(defaultDataBase);
    }
    public static DataBase getBase(String baseName){
        if (dataBase.containsKey(baseName)) return dataBase.get(baseName);
        else {
            DataBase base = new DataBase(baseName,defaultDataFolder);
            dataBase.put(baseName,base);
            return base;
        }
    }
    public static DataTable getTable(String tableName){
        if (dataTable.containsKey(tableName)) return dataTable.get(tableName);
        DataTable table = getBase().getTable(tableName);
        dataTable.put(tableName,table);
        return table;
    }
}
