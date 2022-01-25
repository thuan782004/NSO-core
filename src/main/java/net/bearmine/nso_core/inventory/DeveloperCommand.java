package net.bearmine.nso_core.inventory;

import net.bearmine.nso_core.lib.other.Color;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import redempt.redlib.commandmanager.CommandHook;

public class DeveloperCommand {
    @CommandHook("setSize")
    public void size(CommandSender sender, Player target, int size){
        if (size>0) {
            InventoryStorageCenter.getInstance().setMax(target,size);
            InventoryDataManager.getInstance().setMax(target,size);
            sender.sendMessage(Color.component("&a"+target.getName()+" inventory size was set to "+size));
            target.sendMessage(Color.component("&aKích thước hành trang hiện tại của bạn là "+size));
        }
    }
    @CommandHook("addSize")
    public void add(CommandSender sender, Player target, int size){
        if (size>0) {
            int newSize = InventoryDataManager.getInstance().getMax(target)+size;
            InventoryStorageCenter.getInstance().setMax(target,newSize);
            InventoryDataManager.getInstance().setMax(target,newSize);
            sender.sendMessage(Color.component("&a"+target.getName()+" inventory size was set to "+newSize));
            target.sendMessage(Color.component("&aKích thước hành trang hiện tại của bạn là "+newSize));
        }
    }
}
