package me.itsmas.dailyrewards.util;

import java.util.concurrent.TimeUnit;

public class UtilTime
{
    public static boolean elapsed(long time, long since)
    {
        return System.currentTimeMillis() - since > time;
    }

    public static String getRemaining(long time)
    {
        long hours = TimeUnit.MILLISECONDS.toHours(time);
        String hoursString = UtilString.plural(hours, "hour", "hours");

        long minutes = TimeUnit.MILLISECONDS.toMinutes(time - (hours * 3600000));
        String minutesString = UtilString.plural(minutes, "minute", "minutes");

        if (hours == 0 && minutes == 0)
        {
            int seconds = (int) TimeUnit.MILLISECONDS.toSeconds(time);

            return seconds + " " + UtilString.plural(seconds, "second", "seconds");
        }

        String timeString = "";

        if (hours != 0)
        {
            timeString += hours + " " + hoursString;
        }

        if (minutes != 0)
        {
            timeString += " and " + minutes + " " + minutesString;
        }

        return timeString;
    }
}
