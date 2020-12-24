package club.rigox.cherry.utils;

import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.command.CommandSender;

import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

import static club.rigox.cherry.utils.Config.getLangString;
import static club.rigox.cherry.utils.Logger.sendMessage;

public class Number {
    public static boolean isANumber(CommandSender sender, String str) {
        if (NumberUtils.isNumber(str)) {
            return false;
        }
        sendMessage(sender, getLangString("NOT-A-NUMBER"));
        return true;
    }

    private static final NavigableMap<Double, String> suffixes = new TreeMap<>();
    static {
        suffixes.put(1_000d, "k");
        suffixes.put(1_000_000d, "M");
        suffixes.put(1_000_000_000d, "G");
        suffixes.put(1_000_000_000_000d, "T");
        suffixes.put(1_000_000_000_000_000d, "P");
        suffixes.put(1_000_000_000_000_000_000d, "E");
    }

    public static String format(double value) {
        //Long.MIN_VALUE == -Long.MIN_VALUE so we need an adjustment here
        if (value == Long.MIN_VALUE) return format(Long.MIN_VALUE + 1);
        if (value < 0) return "-" + format(-value);
        if (value < 1000) return Double.toString(value); //deal with easy case

        Map.Entry<Double, String> e = suffixes.floorEntry(value);
        Double divideBy = e.getKey();
        String suffix = e.getValue();

        double truncated = value / (divideBy / 10); //the number part of the output times 10
        boolean hasDecimal = truncated < 100 && (truncated / 10d) != (truncated / 10);
        return hasDecimal ? (truncated / 10d) + suffix : (truncated / 10) + suffix;
    }
}
