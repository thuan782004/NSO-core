package net.bearmine.nso_core.lib.other;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.util.Base64;

public class EncodeItems {

    public static String itemToString(ItemStack[] content) {
        if (content==null) return null;
        FileConfiguration config = new YamlConfiguration();
        for (int i=0;i<content.length;i++){
            if (content[i]==null) continue;
            config.set("item#"+i,content[i]);}
        return Base64.getEncoder().encodeToString(config.saveToString().getBytes());
    }
    public static ItemStack[] stringToItem(String inventoryData) {
        if (inventoryData==null) return null;
        String data = new String(Base64.getDecoder().decode(inventoryData));
        FileConfiguration config = new YamlConfiguration();
        try{config.loadFromString(data);
        }catch(InvalidConfigurationException e)
        {e.printStackTrace();}
        ItemStack[] item = new ItemStack[6*9];
        for (int i=0;i<item.length;i++){
            if (!config.contains("item#"+i)) continue;
            item[i] = config.getItemStack("item#"+i);}
        return item;
    }
}
