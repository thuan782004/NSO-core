package net.bearmine.nso_core.inventory;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class InventoryStorageCenter {
    private static InventoryStorageCenter instance;
    public static InventoryStorageCenter getInstance()
    {return instance==null?new InventoryStorageCenter():instance;}
    private InventoryStorageCenter(){instance=this;}
    private final HashMap<UUID, HashMap<Integer, ItemStack>> cache = new HashMap<>();
    private final HashMap<UUID, Integer> cache_max = new HashMap<>();
    public HashMap<Integer, ItemStack> getCache(Player p){
        if (!cache.containsKey(p.getUniqueId())) load(p);
        return cache.get(p.getUniqueId());
    }
    public int getMaxPage(Player p,int per){
        if (!cache_max.containsKey(p.getUniqueId())) load(p);
        return (int) Math.ceil((double)cache_max.get(p.getUniqueId())/per);
    }
    public void setMax(Player p,int max){
        cache_max.put(p.getUniqueId(),max);
        save(p);
    }
    private void load(Player p){
        cache.put(p.getUniqueId(),InventoryDataManager.getInstance().getItems(p));
        cache_max.put(p.getUniqueId(),InventoryDataManager.getInstance().getMax(p));
    }

    public void save(Player p){
        InventoryDataManager.getInstance().saveItems(p,getCache(p));
        InventoryDataManager.getInstance().setMax(p,cache_max.get(p.getUniqueId()));
    }

    public ItemStack[] gets(Player p,int page,int of){
        page = Math.max(page,1);
        HashMap<Integer, ItemStack> c = getCache(p);
        if (!cache_max.containsKey(p.getUniqueId())) load(p);
        ItemStack[] items = new ItemStack[Math.min(of,cache_max.get(p.getUniqueId())-(page-1)*of)];
        int root = of*(page-1);
        for (int i=0;i<items.length;i++) {
            if (c.containsKey(i+root))
            items[i]=c.get(i+root);
        }
        return items;
    }
    public void update(Inventory inv){
        long time = System.currentTimeMillis();
        NSOINVHolder holder = (NSOINVHolder) inv.getHolder();
        Player p = holder.getTarget();
        ItemStack[] item = inv.getContents();
        HashMap<Integer, ItemStack> it = getCache(p);
        int r = (holder.getPage()-1)*holder.getPer();
        for (int i = 0;i<item.length-9;i++){
            if (item[i]==null) it.remove(r+i);
            else it.put(r+i,item[i]);}
        save(p);
    }
}
