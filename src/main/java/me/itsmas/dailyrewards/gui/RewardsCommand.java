package me.itsmas.dailyrewards.gui;

import me.itsmas.dailyrewards.DailyRewards;
import me.itsmas.dailyrewards.message.Message;
import me.itsmas.dailyrewards.util.Util;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

class RewardsCommand implements CommandExecutor
{
    private final DailyRewards plugin;

    RewardsCommand(DailyRewards plugin)
    {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if (!(sender instanceof Player))
        {
            Util.message(sender, Message.NO_CONSOLE);
            return true;
        }

        handleArgs((Player) sender);
        return true;
    }

    private void handleArgs(Player player)
    {
        plugin.getMenuManager().openRewards(player);
    }
}
