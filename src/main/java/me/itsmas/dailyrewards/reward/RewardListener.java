package me.itsmas.dailyrewards.reward;

import me.itsmas.dailyrewards.DailyRewards;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class RewardListener implements Listener
{
    private final DailyRewards plugin;

    RewardListener(DailyRewards plugin)
    {
        this.plugin = plugin;

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event)
    {
        Player player = event.getPlayer();

        plugin.getRewardManager().insertData(player, plugin.getDataStorage().load(player));
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event)
    {
        plugin.getRewardManager().saveData(event.getPlayer());
    }
}
