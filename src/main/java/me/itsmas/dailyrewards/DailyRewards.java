package me.itsmas.dailyrewards;

import me.itsmas.dailyrewards.data.DataStorage;
import me.itsmas.dailyrewards.data.YamlDataStorage;
import me.itsmas.dailyrewards.gui.MenuManager;
import me.itsmas.dailyrewards.message.Message;
import me.itsmas.dailyrewards.reward.RewardData;
import me.itsmas.dailyrewards.reward.RewardManager;
import org.bukkit.plugin.java.JavaPlugin;

public class DailyRewards extends JavaPlugin
{
    private DataStorage<RewardData> dataStorage;
    private RewardManager rewardManager;
    private MenuManager menuManager;

    private long cooldownMillis;

    public long getCooldownMillis()
    {
        return cooldownMillis;
    }

    private long maxMillis;

    public long getMaxMillis()
    {
        return maxMillis;
    }

    @Override
    public void onEnable()
    {
        saveDefaultConfig();

        cooldownMillis = getConfig().getLong("claims.cooldown") * 1000;
        maxMillis = getConfig().getLong("claims.max") * 1000;

        Message.init(this);

        dataStorage = new YamlDataStorage(this);
        getDataStorage().init();

        rewardManager = new RewardManager(this);

        menuManager = new MenuManager(this);
    }

    @Override
    public void onDisable()
    {
        dataStorage.close();
    }

    public <T> T getConfig(String path)
    {
        return getConfig(path, null);
    }

    @SuppressWarnings("unchecked")
    public <T> T getConfig(String path, Object def)
    {
        return (T) getConfig().get(path, def);
    }

    public DataStorage<RewardData> getDataStorage()
    {
        return dataStorage;
    }

    public RewardManager getRewardManager()
    {
        return rewardManager;
    }

    public MenuManager getMenuManager()
    {
        return menuManager;
    }
}
