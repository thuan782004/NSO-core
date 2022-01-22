package net.bearmine.nso_core.inventory;

import net.bearmine.nso_core.lib.other.Color;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import redempt.redlib.commandmanager.CommandHook;
import redempt.redlib.inventorygui.InventoryGUI;
import redempt.redlib.inventorygui.ItemButton;
import redempt.redlib.itemutils.ItemBuilder;

import java.util.Objects;

public class InventoryCommand implements Listener {
    @CommandHook("openShelf")
    public void openShelf(Player sender){getInv(sender,1).open(sender);}
    private InventoryGUI getInv(Player p,int page){
        Inventory inv = Bukkit.createInventory(null,6*9, Color.component("&eInventory page: "+page));
        inv.setContents(InventoryDataFilter.getInstance().getData(p,page));
        InventoryGUI gui = new InventoryGUI(inv);
        ItemButton filler = ItemButton.create(
                new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE)
                        .setName(" ")
                        .setCustomModelData(999), e->{});
        for (int i = 46; i <53; i++) gui.addButton(filler,i);
        gui.addButton(45,page==1?filler:ItemButton.create(
                new ItemBuilder(Material.ORANGE_STAINED_GLASS_PANE)
                        .setName(Color.vanilla("&eTrang trước"))
                        .setCustomModelData(1),e-> getInv(p,getPage(e)-1).open(p)
        ));
        gui.addButton(53,page==InventoryDataFilter.getInstance().getMax(p)?filler:ItemButton.create(
                new ItemBuilder(Material.ORANGE_STAINED_GLASS_PANE)
                        .setName(Color.vanilla("&eTrang sau"))
                        .setCustomModelData(2),e-> getInv(p,getPage(e)+1).open(p)
        ));
        gui.setDestroyOnClose(true);
        gui.setReturnsItems(false);
        gui.setOnClickOpenSlot((e) -> InventoryDataFilter.getInstance().saveData(p,
                Objects.requireNonNull(e.getClickedInventory()).getContents(),
                getPage(e)
                ));
        return gui;
    }
    private int getPage(InventoryClickEvent e){
        return Integer.parseInt(e.getView().title().toString().split(": ")[1]);
    }
}
