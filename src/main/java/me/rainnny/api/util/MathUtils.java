package me.rainnny.api.util;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/**
 * @author Braydon
 */
public class MathUtils {
    public static double roundDecimal(int degree, double decimal) {
        StringBuilder format = new StringBuilder("#.#");
        for (int i = 1; i < degree; i++)
            format.append("#");
        DecimalFormatSymbols sym = new DecimalFormatSymbols(Locale.CANADA);
        DecimalFormat formatD = new DecimalFormat(format.toString(), sym);
        return Double.parseDouble(formatD.format(decimal));
    }
}