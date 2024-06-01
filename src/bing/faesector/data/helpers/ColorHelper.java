package bing.faesector.data.helpers;

import bing.faesector.data.Statics;

import java.awt.*;

public class ColorHelper {

    /**
     * turns a Color into hex <br>
     * as example, for Color.WHITE it will return #FFFFFFFF if it haves isTransparent true, if not #FFFFFF
     *
     * @param color         the color that will be turned into hex
     * @param isTransparent if the target hex is transparent this need to be true, otherwise it wont return a color that haves alpha value
     * @return the hex
     */
    public static String ColorToHexString(Color color, boolean isTransparent) {
        if (!isTransparent) {
            return String.format("#%02X%02X%02X", color.getRed(), color.getGreen(), color.getBlue());
        } else {
            return String.format("#%02X%02X%02X%02X", color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
        }
    }

    /**
     * gets value from a string sequence <br>
     * as example for Sw2foskv$sp241Fds string if the key is $ and lenght is 3 it will return sp2
     *
     * @param string the hex string that will get turned into color
     * @return the color
     */
    public static Color getColorFromHexString(String string) {
        return StringHelper.getColorFromHexString(string);
    }

    public static Color randomColor(boolean isAlpha) {
        if (isAlpha)
            return new Color(Statics.random.nextInt(255), Statics.random.nextInt(255), Statics.random.nextInt(255), Statics.random.nextInt(255));
        return new Color(Statics.random.nextInt(255), Statics.random.nextInt(255), Statics.random.nextInt(255));
    }

    public static Color randomiseAlpha(Color color) {
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), Statics.random.nextInt(255));
    }

    static public Color hslColor(float h, float s, float l) {//https://stackoverflow.com/a/33947547/21149029
        float q, p, r, g, b;//https://hslpicker.com/#f00

        if (s == 0) {
            r = g = b = l; // achromatic
        } else {
            q = l < 0.5 ? (l * (1 + s)) : (l + s - l * s);
            p = 2 * l - q;
            r = hue2rgb(p, q, h + 1.0f / 3);
            g = hue2rgb(p, q, h);
            b = hue2rgb(p, q, h - 1.0f / 3);
        }
        return new Color(Math.round(r * 255), Math.round(g * 255), Math.round(b * 255));
    }

    private static float hue2rgb(float p, float q, float h) {
        if (h < 0) {
            h += 1;
        }

        if (h > 1) {
            h -= 1;
        }

        if (6 * h < 1) {
            return p + ((q - p) * 6 * h);
        }

        if (2 * h < 1) {
            return q;
        }

        if (3 * h < 2) {
            return p + ((q - p) * 6 * ((2.0f / 3.0f) - h));
        }

        return p;
    }

}
