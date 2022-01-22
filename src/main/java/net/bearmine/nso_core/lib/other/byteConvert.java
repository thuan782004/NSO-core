package net.bearmine.nso_core.lib.other;

import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class byteConvert {

    public static List<String> inventoryToString(Inventory inventory) {
        if (inventory==null) return null;
        int size = inventory.getSize();
        ItemStack[] items = inventory.getContents();
        FileConfiguration config = new YamlConfiguration();
        StringBuilder sb = new StringBuilder();
        if (items!=null)
        for (int i=0;i<items.length;i++){
            if (items[i]==null) continue;
            config.set("item#"+i,items[i]);
        }
        sb.append(config.saveToString());
        sb.append("GildedUpgradeConfigPredator");
        sb.append(size);
        String source = Base64.getEncoder().encodeToString(sb.toString().getBytes());
        return split(source,100);
    }

    public static List<String> split(String s,int w){
        List<String> l = new ArrayList<>();
        for (int i=0;i*w<s.length();i++){
            if ((i+1)*w<s.length())
                l.add(s.substring(i*w,(i+1)*w));
            else
                l.add(s.substring(i*w));
        }
        return l;
    }

    public static Inventory stringToInventory(String inventoryData) {
        if (inventoryData==null) return null;
        String data = new String(Base64.getDecoder().decode(inventoryData));
        String[] spliced = data.split("GildedUpgradeConfigPredator");
        if (spliced.length<2) {
            Bukkit.getLogger().warning( Color.vanilla("&cGildedUpgrade: inv-data is invalid"));
            return null;
        }
        FileConfiguration config = new YamlConfiguration();
            int size = Integer.parseInt(spliced[1]);
        try {
            config.loadFromString(spliced[0]);
        } catch ( InvalidConfigurationException e ) {
            e.printStackTrace();
        }
        ItemStack[] item = new ItemStack[size];
            for (int i=0;i<item.length;i++){
                if (!config.contains("item#"+i)) continue;
                item[i] = config.getItemStack("item#"+i);
            }
            Inventory inv = Bukkit.createInventory(null,size,"no name");
            inv.setContents(item);
            return inv;

    }
}
