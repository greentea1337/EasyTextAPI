package org.nell.easytextapi;

import net.minecraft.text.Style;
import net.minecraft.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The GradientHandler class manages the creation of gradient text effects.
 */
public class GradientHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(GradientHandler.class);

    /**
     * Creates a Text object with a gradient effect applied to the given content.
     *
     * @param content The text content to apply the gradient to.
     * @param startColor The starting color of the gradient in hexadecimal format.
     * @param endColor The ending color of the gradient in hexadecimal format.
     * @return A Text object with the gradient effect applied.
     */
    public static Text createGradientText(String content, String startColor, String endColor) {
        try {
            return applyGradient(content, startColor, endColor);
        } catch (Exception e) {
            LOGGER.error("Error creating gradient text for content: {}", content, e);
            return Text.literal(content);
        }
    }

    /**
     * Applies the gradient effect to the given text.
     *
     * @param text The text to apply the gradient to.
     * @param startColor The starting color of the gradient.
     * @param endColor The ending color of the gradient.
     * @return A Text object with the gradient applied.
     */
    private static Text applyGradient(String text, String startColor, String endColor) {
        int startRGB = ColorUtils.parseHexColor(startColor);
        int endRGB = ColorUtils.parseHexColor(endColor);

        Text result = Text.literal("");
        for (int i = 0; i < text.length(); i++) {
            float ratio = (float) i / (text.length() - 1);
            int color = ColorUtils.interpolateColor(startRGB, endRGB, ratio);

            result = result.copy().append(Text.literal(String.valueOf(text.charAt(i)))
                    .setStyle(Style.EMPTY.withColor(color)));
        }
        return result;
    }
}