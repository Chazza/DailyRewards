package me.itsmas.dailyrewards.message;

import me.itsmas.dailyrewards.DailyRewards;
import me.itsmas.dailyrewards.util.Util;

import java.util.Arrays;

public enum Message
{
    NO_CONSOLE,
    CLAIMED_REWARD,
    CANNOT_CLAIM;

    private String msg;

    public String value()
    {
        return msg;
    }

    private void setValue(String msg)
    {
        this.msg = msg;
    }

    /**
     * Initialise the plugin messages
     * @param plugin The plugin instance
     */
    public static void init(DailyRewards plugin)
    {
        Arrays.stream(values()).forEach(message ->
        {
            String raw = plugin.getConfig( "messages." + message.name().toLowerCase());

            if (raw == null)
            {
                Util.logErr("Unable to find message value for message '" + message.name() + "'");

                raw = message.name();
            }

            message.setValue(Util.colour(raw));
        });
    }
}
