package net.bearmine.nso_core;

import org.bukkit.plugin.java.JavaPlugin;

public final class NSO_core extends JavaPlugin {

    public static NSO_core getInstance() {return instance;}
    private static NSO_core instance;
    @Override
    public void onEnable() {  instance = this;

    }

    @Override
    public void onDisable() {
    }
}
