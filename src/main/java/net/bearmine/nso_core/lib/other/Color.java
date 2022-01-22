package net.bearmine.nso_core.lib.other;


import net.md_5.bungee.api.ChatColor;

public class Color {
    public static String vanilla(String message) {
        if (message==null) return null;
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}
