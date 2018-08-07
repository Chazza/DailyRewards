package me.itsmas.dailyrewards.reward;

import me.itsmas.dailyrewards.DailyRewards;
import me.itsmas.dailyrewards.message.Message;
import me.itsmas.dailyrewards.util.CustomItem;
import me.itsmas.dailyrewards.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

public class Reward
{
    private final DailyRewards plugin;

    private final String name;
    private final int tier;
    private final List<String> commands;

    private final CustomItem stack;

    public Reward(DailyRewards plugin, String name, int tier, Material material, int data, List<String> lore, List<String> commands)
    {
        this.plugin = plugin;

        this.name = name;
        this.tier = tier;
        this.commands = commands;

        this.stack =
                new CustomItem(material)
                .setDamage((short) data)
                .setName(Util.colour(name))
                .setLore(lore.stream().map(Util::colour).collect(Collectors.toList()));
    }

    String getName()
    {
        return name;
    }

    public int getTier()
    {
        return tier;
    }

    public CustomItem getStack()
    {
        return stack;
    }

    public void apply(Player player)
    {
        player.sendMessage(Message.CLAIMED_REWARD.value().replace("%reward%", getName()));

        commands.forEach(cmd -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd.replace("%player%", player.getName())));

        plugin.getRewardManager().getData(player).update();
    }
}
