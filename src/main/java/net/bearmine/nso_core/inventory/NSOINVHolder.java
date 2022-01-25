package net.bearmine.nso_core.inventory;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

public class NSOINVHolder implements InventoryHolder {
    private Inventory inventory;
    private final Player target;
    private final int page;
    private final int per;

    public NSOINVHolder(Player target, int page, int per) {
        this.target = target;
        this.page = page;
        this.per = per;
    }

    @Override
    public @NotNull Inventory getInventory() {
        return inventory;
    }

    public int getPer() {
        return per;
    }

    public int getPage() {
        return page;
    }

    public Player getTarget() {
        return target;
    }
}
