package net.bearmine.nso_core.inventory;

import net.bearmine.nso_core.lib.data_base.DataManager;
import net.bearmine.nso_core.lib.data_base.DataTable;
import net.bearmine.nso_core.lib.other.Print;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class InventoryDataManager extends DataTable {
    private static InventoryDataManager instance;
    public static InventoryDataManager getInstance()
    {return instance==null?new InventoryDataManager():instance;}
    private InventoryDataManager() {super("inventory", DataManager.getBase());instance=this;}
    public int getMax(Player p){
        String uuid = p.getUniqueId().toString();
        String s = getString(uuid,"max");
        if (s==null) {setMax(p,45);return 45;}
        return Integer.parseInt(s);
    }
    public void setMax(Player p,int max){
        String uuid = p.getUniqueId().toString();
        setString(uuid,"max", String.valueOf(max));
    }
    public HashMap<Integer,ItemStack> getItems(Player p){
        String uuid = p.getUniqueId().toString();
        String data = getString(uuid,"items");
        if (data==null) return new HashMap<>();
        FileConfiguration config = new YamlConfiguration();
        try {config.loadFromString(data);
        } catch ( InvalidConfigurationException e )
        {Print.ln("An error was caught when try to load inventory data for "+p.getName());}
        HashMap<Integer,ItemStack> items = new HashMap<>();
        config.getKeys(false).forEach(key->items.put(Integer.valueOf(key),config.getItemStack(key)));
        return items;
    }
    public void saveItems(Player p,HashMap<Integer,ItemStack> i){
        FileConfiguration config = new YamlConfiguration();
        i.forEach((slot, item) -> config.set(String.valueOf(slot),item));
        setString(p.getUniqueId().toString(),"items",config.saveToString());
    }




}
