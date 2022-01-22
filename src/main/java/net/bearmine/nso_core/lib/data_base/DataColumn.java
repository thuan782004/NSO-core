package net.bearmine.nso_core.lib.data_base;

public class DataColumn {
    private final String columnName;
    private final DataTable dataTable;

    protected DataColumn(String name, DataTable table){
        this.columnName = name;
        this.dataTable  = table;
    }
    public String getString(String key){
        if (key==null) return null;
        return this.dataTable.getString(key,columnName);
    }
    public void  setString(String key,String value){
        this.dataTable.setString(key,this.columnName,value);
    }
    public DataTable getDataTable() {
        return dataTable;
    }
    public String getColumnName() {
        return columnName;
    }
}
