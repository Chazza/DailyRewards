package me.itsmas.dailyrewards.util;

public class UtilString
{
    public static String plural(long amount, String singular, String plural)
    {
        return amount == 1 ? singular : plural;
    }
}
