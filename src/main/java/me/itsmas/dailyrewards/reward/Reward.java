package me.itsmas.dailyrewards.reward;

import io.samdev.actionutil.ActionUtil;
import me.itsmas.dailyrewards.DailyRewards;
import me.itsmas.dailyrewards.message.Message;
import me.itsmas.dailyrewards.util.CustomItem;
import me.itsmas.dailyrewards.util.Util;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

public class Reward
{
    private final DailyRewards plugin;

    private final String name;
    private final int tier;
    private final List<String> actions;

    private final CustomItem stack;

    Reward(DailyRewards plugin, String name, int tier, Material material, int data, List<String> lore, List<String> actions)
    {
        this.plugin = plugin;

        this.name = name;
        this.tier = tier;
        this.actions = actions;

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

    int getTier()
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
        ActionUtil.executeActions(player, actions);

        plugin.getRewardManager().getData(player).update();
    }
}
