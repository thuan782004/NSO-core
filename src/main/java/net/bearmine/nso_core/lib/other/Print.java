package net.bearmine.nso_core.lib.other;

import org.bukkit.Bukkit;

public class Print {
    public static void ln(String s){
        if (s==null) return;
        Bukkit.getConsoleSender().sendMessage(Color.component(s));
    }
}
