package org.nell.easytextapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The ColorUtils class provides utility methods for color manipulation and conversion.
 */
public class ColorUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(ColorUtils.class);

    /**
     * Parses a hexadecimal color string into an integer representation.
     *
     * @param color The hexadecimal color string (with or without '#').
     * @return The integer representation of the color.
     */
    public static int parseHexColor(String color) {
        try {
            return Integer.parseInt(color.replace("#", ""), 16);
        } catch (NumberFormatException e) {
            LOGGER.error("Invalid hexadecimal color format: {}. Error: {}", color, e.getMessage());
            throw e; // Rethrow the exception after logging
        }
    }

    /**
     * Interpolates between two colors based on a given ratio.
     *
     * @param startColor The starting color as an integer.
     * @param endColor The ending color as an integer.
     * @param ratio The interpolation ratio (0.0 to 1.0).
     * @return The interpolated color as an integer.
     */
    public static int interpolateColor(int startColor, int endColor, float ratio) {
        if (ratio < 0 || ratio > 1) {
            LOGGER.warn("Interpolation ratio out of bounds: {}. Clamping to range [0, 1].", ratio);
            ratio = Math.max(0, Math.min(1, ratio)); // Clamp ratio to [0, 1]
        }

        int r = interpolateComponent((startColor >> 16) & 0xFF, (endColor >> 16) & 0xFF, ratio);
        int g = interpolateComponent((startColor >> 8) & 0xFF, (endColor >> 8) & 0xFF, ratio);
        int b = interpolateComponent(startColor & 0xFF, endColor & 0xFF, ratio);
        return (r << 16) | (g << 8) | b;
    }

    /**
     * Interpolates a single color component.
     *
     * @param start The start value of the component.
     * @param end The end value of the component.
     * @param ratio The interpolation ratio.
     * @return The interpolated component value.
     */
    private static int interpolateComponent(int start, int end, float ratio) {
        return Math.round(start + (end - start) * ratio);
    }
}
