package de.marvinleiers.marvinplugin.utils;

import de.marvinleiers.customconfig.CustomConfig;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;

public class Messages
{
    private static CustomConfig customConfig;

    public static void setUp(Plugin plugin)
    {
        customConfig = new CustomConfig(plugin.getDataFolder().getPath() + "/messages.yml");
    }

    public static void add(String path, String value)
    {
        customConfig.set(path, value);
    }

    public static String get(String path, String... parameters)
    {
        if (!customConfig.isSet(path))
            return null;

        String msg = ChatColor.translateAlternateColorCodes('&', customConfig.getString(path));

        if (parameters == null || parameters.length == 0)
        {
            return msg;
        }

        for (int i = 0; i < parameters.length; i++)
        {
            if (msg.contains("<v>"))
            {
                msg = msg.replaceFirst("<v>", parameters[i]);
            }
            else
            {
                break;
            }
        }

        return msg;
    }
}
