package net.bearmine.nso_core.inventory;

import net.bearmine.nso_core.NSO_core;
import net.bearmine.nso_core.lib.other.Color;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import redempt.redlib.commandmanager.CommandHook;
import redempt.redlib.itemutils.ItemBuilder;

import java.util.Objects;

public class InventoryCommand implements Listener {
    private static InventoryCommand instance;
    public InventoryCommand(){instance=this;}
    public static InventoryCommand getInstance() {
        return instance!=null?instance:new InventoryCommand();
    }
    private static final ItemStack filler =  new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE)
            .setName(" ")
            .setCustomModelData(999).toItemStack();
    private static final ItemStack previous = new ItemBuilder(Material.ORANGE_STAINED_GLASS_PANE)
            .setName(Color.vanilla("&eTrang trước"))
            .setCustomModelData(1).toItemStack();
    private static final ItemStack after = new ItemBuilder(Material.ORANGE_STAINED_GLASS_PANE)
            .setName(Color.vanilla("&eTrang sau"))
            .setCustomModelData(2).toItemStack();
    @CommandHook("openShelf")
    public void openShelf(Player sender){sender.openInventory(getInv(sender,1));}
    private Inventory getInv(Player p,int page){
        Inventory inv = Bukkit.createInventory(new NSOINVHolder(p,page,45),6*9, Color.component("&eInventory page: "+page));
        ItemStack[] content = InventoryStorageCenter.getInstance().gets(p, page,45);//get item data
        for (int i = 0; i < 45; i++) inv.setItem(i,i<content.length?content[i]:filler);
        for (int i = 45; i <54; i++) inv.setItem(i,filler);
        if (page!=1) inv.setItem(45,previous);
        if (page<InventoryStorageCenter.getInstance().getMaxPage(p,45))
            inv.setItem(53,after);
        return inv;
    }
    private boolean isEmpty(ItemStack i){
        return i==null||i.getType().isEmpty()||i.getAmount()==0;
    }
    @EventHandler
    public void onClick(InventoryClickEvent e){
        if (!(e.getInventory().getHolder() instanceof NSOINVHolder)) return;
        if (isEmpty(e.getCursor())&&isEmpty(e.getCurrentItem())) return;
        if (!isEmpty(e.getCurrentItem())) {
            if (Objects.requireNonNull(e.getCurrentItem()).isSimilar(filler)) {
                e.setCancelled(true);
                return;
            }
            if (Objects.requireNonNull(e.getCurrentItem()).isSimilar(previous)) {
                e.setCancelled(true);
                openOffset(e,-1);
                return;
            }
            if (Objects.requireNonNull(e.getCurrentItem()).isSimilar(after)) {
                e.setCancelled(true);
                openOffset(e,1);
            }
        }
    }
    @EventHandler
    public void onClose(InventoryCloseEvent e){
        if (!(e.getInventory().getHolder() instanceof NSOINVHolder)) return;
        InventoryStorageCenter.getInstance().update(e.getInventory());
    }
    public void openOffset(InventoryClickEvent e,int offset){
        e.getWhoClicked().openInventory(
                getInv(((NSOINVHolder) Objects.requireNonNull(e.getInventory().getHolder())).getTarget(),
                        ((NSOINVHolder) e.getInventory().getHolder()).getPage() + offset));
    }
}
