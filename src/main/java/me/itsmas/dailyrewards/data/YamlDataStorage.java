package me.itsmas.dailyrewards.data;

import me.itsmas.dailyrewards.DailyRewards;
import me.itsmas.dailyrewards.reward.RewardData;
import me.itsmas.dailyrewards.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class YamlDataStorage implements DataStorage<RewardData>
{
    private final DailyRewards plugin;

    public YamlDataStorage(DailyRewards plugin)
    {
        this.plugin = plugin;

        init();
    }

    private File dataFile;
    private YamlConfiguration config;

    @Override
    public void init()
    {
        dataFile = new File(plugin.getDataFolder(), "data.yml");

        try
        {
            if (dataFile.createNewFile())
            {
                Util.log("Generated data.yml file");
            }

            if (new File(plugin.getDataFolder(), "tiers").mkdir())
            {
                plugin.saveResource("tiers" + File.separator + "1.yml", false);
                plugin.saveResource("tiers" + File.separator + "2.yml", false);

                Util.log("Generated tiers folder and sample tiers");
            }

            config = YamlConfiguration.loadConfiguration(dataFile);
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void close()
    {
        Bukkit.getOnlinePlayers().forEach(player ->
        {
            RewardData data = plugin.getRewardManager().getData(player);
            save(player, data);
        });

        try
        {
            config.save(dataFile);
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void save(Player player, RewardData data)
    {
        final String path = getPath(player);

        config.set(path + "last_claim", data.getLastClaim());
        config.set(path + "streak", data.getStreak());
        config.set(path + "tier", data.getTier());
    }

    @Override
    public RewardData load(Player player)
    {
        final String path = getPath(player);

        if (!config.contains(path))
        {
            RewardData data = new RewardData(plugin, System.currentTimeMillis() - plugin.getCooldownMillis(), 0, 1);
            save(player, data);

            return data;
        }

        long lastClaim = config.getLong(path + "last_claim");
        int streak = config.getInt(path + "streak");
        int tier = config.getInt(path + "tier");

        return new RewardData(plugin, lastClaim, streak, tier);
    }

    private String getPath(Player player)
    {
        return "players." + player.getUniqueId().toString() + ".";
    }
}
