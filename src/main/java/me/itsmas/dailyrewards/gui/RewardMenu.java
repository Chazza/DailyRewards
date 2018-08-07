package me.itsmas.dailyrewards.gui;

import me.itsmas.dailyrewards.DailyRewards;
import me.itsmas.dailyrewards.reward.Reward;
import me.itsmas.dailyrewards.reward.RewardData;
import me.itsmas.dailyrewards.util.CustomItem;
import me.itsmas.dailyrewards.util.UtilTime;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.List;

class RewardMenu
{
    private final Inventory inventory;

    RewardMenu(Player player, DailyRewards plugin)
    {
        this.inventory = Bukkit.createInventory(null, 27, plugin.getMenuManager().getMenuName(player));

        int index = 9;

        RewardData data = plugin.getRewardManager().getData(player);
        data.resetIfExpired();

        int streak = data.getStreak();

        for (Reward reward : plugin.getRewardManager().getRewards(player))
        {
            index++;

            CustomItem stack = reward.getStack().dupe();

            if (reward == plugin.getRewardManager().getRewards(player).get(streak))
            {
                if (data.canClaim())
                {
                    // They can claim this, make it glow
                    stack.addGlow();
                }
                else
                {
                    List<String> lore = stack.getItemMeta().getLore();

                    lore.add("");

                    String time = UtilTime.getRemaining((data.getLastClaim() + plugin.getCooldownMillis()) - System.currentTimeMillis());
                    lore.add(plugin.getMenuManager().getCooldownMessage().replace("%time%", time));

                    stack.setLore(lore);
                }
            }

            inventory.setItem(index, stack);
        }

        player.openInventory(inventory);
    }
}
