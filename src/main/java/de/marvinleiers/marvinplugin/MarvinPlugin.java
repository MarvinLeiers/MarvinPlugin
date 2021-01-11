package de.marvinleiers.marvinplugin;

import de.marvinleiers.marvinplugin.utils.Base64Util;
import de.marvinleiers.marvinplugin.utils.DataClient;
import de.marvinleiers.marvinplugin.utils.Messages;
import de.marvinleiers.menuapi.MenuAPI;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class MarvinPlugin extends JavaPlugin
{
    private static ConsoleCommandSender console = Bukkit.getConsoleSender();

    public void onEnable()
    {
        console.sendMessage("§7==========§9§lPlugin by Marvin Leiers§7==========");
        Messages.setUp(this);
        console.sendMessage("§7Loaded messages");
        Base64Util.setUp(this);
        console.sendMessage("§7Loaded Base64Util by Marvin Leiers...");
        MenuAPI.setUp(this);
        console.sendMessage("§7Loaded MenuAPI by Marvin Leiers...");
        console.sendMessage("§9If you want more plugins like this (free and paid), visit §9§lhttps://www.marvinleiers.de");

        Messages.add("only-for-players", "&cThis command is only for players!");

        new DataClient(this);
    }

    public static MarvinPlugin getInstance()
    {
        return getPlugin(MarvinPlugin.class);
    }
}
