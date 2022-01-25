package net.bearmine.nso_core;

import net.bearmine.nso_core.inventory.DeveloperCommand;
import net.bearmine.nso_core.inventory.InventoryCommand;
import net.bearmine.nso_core.lib.data_base.DataManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import redempt.redlib.commandmanager.CommandParser;

import java.sql.SQLException;

public final class NSO_core extends JavaPlugin {

    public static NSO_core getInstance() {return instance;}
    private static NSO_core instance;
    @Override
    public void onEnable() {  instance = this;
        saveDefaultConfig();
        Bukkit.getPluginManager().registerEvents(InventoryCommand.getInstance(),this);
        new CommandParser(this.getResource("command.rdcml")).parse().register("nso",
                InventoryCommand.getInstance(),
                new DeveloperCommand()
                );
    }

    @Override
    public void onDisable() {
        try {DataManager.getBase().getConnection().close();} catch ( SQLException ignored ) {}

    }
}
