package me.itsmas.dailyrewards.gui;

import me.itsmas.dailyrewards.DailyRewards;
import me.itsmas.dailyrewards.message.Message;
import me.itsmas.dailyrewards.reward.Reward;
import me.itsmas.dailyrewards.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class MenuManager implements Listener
{
    private final DailyRewards plugin;

    private final String menuName;
    private final String cooldownMessage;

    public MenuManager(DailyRewards plugin)
    {
        this.plugin = plugin;

        menuName = Util.colour(plugin.getConfig("gui.title"));
        cooldownMessage = Util.colour(plugin.getConfig("gui.cooldown"));

        Bukkit.getPluginManager().registerEvents(this, plugin);

        plugin.getCommand("rewards").setExecutor(new RewardsCommand(plugin));
    }

    String getMenuName(Player player)
    {
        int tier = plugin.getRewardManager().getData(player).getTier();

        return menuName.replace("%tier%", String.valueOf(tier));
    }

    String getCooldownMessage()
    {
        return cooldownMessage;
    }

    @EventHandler
    public void onClick(InventoryClickEvent event)
    {
        Inventory inventory = event.getInventory();
        Player player = (Player) event.getWhoClicked();

        if (inventory.getTitle().equalsIgnoreCase(getMenuName(player)))
        {
            event.setCancelled(true);

            ItemStack clicked = event.getCurrentItem();

            if (clicked != null && clicked.getItemMeta() != null && clicked.getItemMeta().hasDisplayName())
            {
                if (!clicked.getItemMeta().hasEnchant(Enchantment.DURABILITY))
                {
                    Util.message(player, Message.CANNOT_CLAIM);
                    return;
                }

                Reward reward = plugin.getRewardManager().getRewardFromStack(clicked);

                reward.apply(player);

                player.getOpenInventory().close();
            }
        }
    }

    void openRewards(Player player)
    {
        new RewardMenu(player, plugin);
    }
}
