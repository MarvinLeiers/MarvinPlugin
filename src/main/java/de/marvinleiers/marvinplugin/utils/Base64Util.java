package de.marvinleiers.marvinplugin.utils;

import de.marvinleiers.customconfig.CustomConfig;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

public class Base64Util
{
    private static Plugin plugin;
    private static CustomConfig customConfig;

    public static void setUp(Plugin plugin)
    {
        Base64Util.plugin = plugin;
        Base64Util.customConfig = new CustomConfig(plugin.getDataFolder().getPath() + "/internal/serialized.yml");
    }

    public static void base64Items(String path, ItemStack... items)
    {
        try
        {
            ByteArrayOutputStream io = new ByteArrayOutputStream();
            BukkitObjectOutputStream os = new BukkitObjectOutputStream(io);

            for (ItemStack item : items)
                os.writeObject(item);

            os.flush();

            byte[] rawData = io.toByteArray();
            String encodedData = Base64.getEncoder().encodeToString(rawData);

            customConfig.set(path, encodedData);

            os.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
