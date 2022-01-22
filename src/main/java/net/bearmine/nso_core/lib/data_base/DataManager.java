package net.bearmine.nso_core.lib.data_base;

import net.bearmine.nso_core.NSO_core;

import java.io.File;
import java.util.HashMap;

public class DataManager {
    private final HashMap<String,DataBase> dataBase = new HashMap<>();
    private final HashMap<String,DataTable> dataTable = new HashMap<>();

    private final String defaultDataBase = "data";
    private final File defaultDataFolder = new File(NSO_core.getInstance().getDataFolder(),"data");
    private final DataBase def = new DataBase(defaultDataBase,defaultDataFolder);
    private static DataManager ins;
    private DataManager(){ins=this;}
    public static DataManager getInstance() {
        return ins==null?new DataManager():ins;
    }
    public static DataBase getBase(){
        return getInstance().def;
    }
    public DataBase getBase(String baseName){
        if (dataBase.containsKey(baseName)) return dataBase.get(baseName);
        else {
            DataBase base = new DataBase(baseName,defaultDataFolder);
            dataBase.put(baseName,base);
            return base;
        }
    }
    public DataTable getTable(String tableName){
        if (dataTable.containsKey(tableName)) return dataTable.get(tableName);
        DataTable table = getBase().getTable(tableName);
        dataTable.put(tableName,table);
        return table;
    }
}
