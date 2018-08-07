package me.itsmas.dailyrewards.util;

import me.itsmas.dailyrewards.DailyRewards;
import me.itsmas.dailyrewards.message.Message;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public class Util
{
    private static final DailyRewards plugin = JavaPlugin.getPlugin(DailyRewards.class);

    public static String colour(String input)
    {
        return ChatColor.translateAlternateColorCodes('&', input);
    }

    public static void message(CommandSender sender, Message message)
    {
        sender.sendMessage(message.value());
    }

    public static void log(String msg)
    {
        plugin.getLogger().info(msg);
    }

    public static void logErr(String msg)
    {
        plugin.getLogger().log(Level.WARNING, msg);
    }

    public static void logFatal(String msg)
    {
        plugin.getLogger().severe(msg);
    }
}

