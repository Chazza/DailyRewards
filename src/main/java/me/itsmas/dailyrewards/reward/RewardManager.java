package me.itsmas.dailyrewards.reward;

import me.itsmas.dailyrewards.DailyRewards;
import me.itsmas.dailyrewards.util.Util;
import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class RewardManager
{
    private final DailyRewards plugin;

    public RewardManager(DailyRewards plugin)
    {
        this.plugin = plugin;

        new RewardListener(plugin);

        parseRewards();
    }

    private final Map<UUID, RewardData> rewards = new HashMap<>();

    public RewardData getData(Player player)
    {
        return rewards.get(player.getUniqueId());
    }

    void insertData(Player player, RewardData data)
    {
        rewards.put(player.getUniqueId(), data);
    }

    void saveData(Player player)
    {
        RewardData data = rewards.remove(player.getUniqueId());

        plugin.getDataStorage().save(player, data);
    }

    private final String REWARDS_DIR = "rewards";
    private final List<Reward> baseRewards = new ArrayList<>();

    public List<Reward> getRewards(Player player)
    {
        int tier = getData(player).getTier();

        return baseRewards.stream().filter(reward -> reward.getTier() == tier).collect(Collectors.toList());
    }

    public Reward getRewardFromStack(ItemStack stack)
    {
        String itemName = stack.getItemMeta().getDisplayName();

        for (Reward reward : baseRewards)
        {
            if (reward.getName().equals(itemName))
            {
                return reward;
            }
        }

        return null;
    }

    private int highestTier = 1;

    int getHighestTier()
    {
        return highestTier;
    }

    private void parseRewards()
    {
        for (String tier : plugin.getConfig().getConfigurationSection(REWARDS_DIR + ".tiers").getKeys(false))
        {
            if (!NumberUtils.isNumber(tier))
            {
                continue;
            }

            int tierNum = Integer.parseInt(tier);

            if (tierNum > highestTier)
            {
                highestTier = tierNum;
            }

            for (String reward : plugin.getConfig().getConfigurationSection(REWARDS_DIR + ".tiers." + tier).getKeys(false))
            {
                String base = REWARDS_DIR + ".tiers." + tier + "." + reward + ".";

                String rawMaterial = plugin.getConfig(base + "material");

                try
                {
                    Material material = Material.valueOf(rawMaterial);
                    int data = plugin.getConfig(base + "data");

                    String name = Util.colour(plugin.getConfig(base + "name"));

                    List<String> lore = plugin.getConfig(base + "lore");
                    lore = lore.stream().map(Util::colour).collect(Collectors.toList());

                    List<String> commands = plugin.getConfig(base + "commands");

                    baseRewards.add(new Reward(plugin, name, tierNum, material, data, lore, commands));
                }
                catch (IllegalArgumentException ex)
                {
                    Util.log("Invalid material " + rawMaterial);
                }
            }
        }
    }
}
