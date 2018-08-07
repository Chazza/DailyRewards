package me.itsmas.dailyrewards.reward;

import me.itsmas.dailyrewards.DailyRewards;
import me.itsmas.dailyrewards.util.UtilTime;

public class RewardData
{
    private final DailyRewards plugin;

    public RewardData(DailyRewards plugin, long lastClaim, int streak, int tier)
    {
        this.plugin = plugin;

        this.lastClaim = lastClaim;
        this.streak = streak;
        this.tier = tier;
    }

    private long lastClaim;
    private int streak;
    private int tier;

    public long getLastClaim()
    {
        return lastClaim;
    }

    public int getStreak()
    {
        return streak;
    }

    void update()
    {
        lastClaim = System.currentTimeMillis();
        streak++;

        if (streak == 7)
        {
            streak = 0;

            if (++tier > plugin.getRewardManager().getHighestTier())
            {
                // They passed the max tier, reset
                tier = 1;
            }
        }
    }

    public int getTier()
    {
        return tier;
    }

    public void resetIfExpired()
    {
        if (streak != 0 && UtilTime.elapsed(plugin.getMaxMillis(), lastClaim))
        {
            streak = 0;
        }
    }

    public boolean canClaim()
    {
        return UtilTime.elapsed(plugin.getCooldownMillis(), lastClaim);
    }
}
