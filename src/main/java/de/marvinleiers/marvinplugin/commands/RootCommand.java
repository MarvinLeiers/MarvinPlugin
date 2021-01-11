package de.marvinleiers.marvinplugin.commands;

import de.marvinleiers.marvinplugin.MarvinPlugin;
import de.marvinleiers.marvinplugin.utils.Messages;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public abstract class RootCommand implements CommandExecutor
{
    private ArrayList<Subcommand> subcommands = new ArrayList<>();
    private String name;

    public RootCommand(String name)
    {
        this.name = name;

        MarvinPlugin.getInstance().getCommand(this.name).setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if (!(sender instanceof Player))
        {
            sender.sendMessage(Messages.get("only-for-players"));
            return true;
        }

        Player player = (Player) sender;

        if (args.length == 0)
        {
            sendHelp(sender);
            return true;
        }

        for (Subcommand subcommand : subcommands)
        {
            if (subcommand.getName().equalsIgnoreCase(args[0]))
            {
                subcommand.execute(player, args);
                return true;
            }
        }

        sendHelp(sender);
        return true;
    }

    public String getName()
    {
        return name;
    }

    public void addSubcommand(Subcommand subcommand)
    {
        for (Subcommand command : subcommands)
        {
            if (command.getName().equalsIgnoreCase(subcommand.getName()))
                return;
        }

        subcommand.setRootCommand(this);
        subcommands.add(subcommand);
    }

    public ArrayList<Subcommand> getSubcommands()
    {
        return subcommands;
    }

    private void sendHelp(CommandSender sender)
    {
        sender.sendMessage(" ");
        sender.sendMessage(" ");

        for (Subcommand subcommand : subcommands)
        {
            sender.sendMessage(subcommand.getUsage());
            sender.sendMessage(" ");
        }

        sender.sendMessage(" ");
        sender.sendMessage(" ");
    }
}
