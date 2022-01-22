package net.bearmine.nso_core.inventory;

import net.bearmine.nso_core.lib.data_base.DataManager;
import net.bearmine.nso_core.lib.data_base.DataTable;
import net.bearmine.nso_core.lib.other.EncodeItems;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class InventoryDataFilter extends DataTable {
    private static InventoryDataFilter instance;
    public static InventoryDataFilter getInstance()
    {return instance==null?new InventoryDataFilter():instance;}
    private InventoryDataFilter() {super("inventory", DataManager.getBase());instance=this;}
    public int getMax(Player p){
        String uuid = p.getUniqueId().toString();
        String s = getString(uuid,"max");
        if (s==null) {setMax(p,1);return 1;}
        return Integer.parseInt(s);
    }
    public void setMax(Player p,int max){
        String uuid = p.getUniqueId().toString();
        setString(uuid,"max", String.valueOf(max));
    }
    public ItemStack[] getData(Player p,int page){
        String uuid = p.getUniqueId().toString();
        page = Math.max(page, 1);
        page = Math.min(page, getMax(p));
        String data = getString(uuid,"page#"+page);
        if (data==null) return new ItemStack[5*9];
        return EncodeItems.stringToItem(data);
    }
    public void saveData(Player p,ItemStack[] content,int page){
        String uuid = p.getUniqueId().toString();
        setString(uuid,"page#"+page,
        EncodeItems.itemToString(
        Arrays.copyOfRange(content,0,5*9-1))
        );
    }


}
