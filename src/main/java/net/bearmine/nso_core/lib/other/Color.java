package net.bearmine.nso_core.lib.other;


import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.md_5.bungee.api.ChatColor;

public class Color {
    public static String vanilla(String message) {
        if (message==null) return null;
        return ChatColor.translateAlternateColorCodes('&', message);
    }
    public static Component component(String message){
        return LegacyComponentSerializer.legacyAmpersand().deseializeOrNull(message);
    }
}
