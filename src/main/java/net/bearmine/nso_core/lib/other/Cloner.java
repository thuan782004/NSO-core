package net.bearmine.nso_core.lib.other;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class Cloner {
    public static ItemStack[] contentClone(ItemStack[] raw){
        ItemStack[] clone = new ItemStack[raw.length];
        for (int i=0;i<raw.length;i++) {
            if (raw[i]==null||raw[i].getType()==Material.AIR) continue;
            clone[i] = raw[i].clone();
        }
        return clone;
    }
}
