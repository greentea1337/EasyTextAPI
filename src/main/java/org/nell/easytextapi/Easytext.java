package org.nell.easytextapi;

import net.fabricmc.api.ModInitializer;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Easytext class serves as the main entry point for the EasyText API.
 * It provides methods for creating formatted text using a simple tag-based syntax.
 */
public class Easytext implements ModInitializer {

    private static final Logger LOGGER = LoggerFactory.getLogger(Easytext.class);

    @Override
    public void onInitialize() {
        try {
            // Initialization logic here
            LOGGER.info("EasyTextAPI initialized successfully!");
        } catch (Exception e) {
            LOGGER.error("Failed to initialize EasyTextAPI!", e);
        }
    }

    /**
     * Creates a Text object with specified formatting.
     *
     * @param content The text content to format.
     * @param formattings The Formatting options to apply.
     * @return A formatted Text object.
     */
    public static Text createStyledText(String content, Formatting... formattings) {
        return TextFormatter.createStyledText(content, formattings);
    }

    /**
     * Creates a simple Text object without any formatting.
     *
     * @param content The text content.
     * @return A simple Text object.
     */
    public static Text createSimpleText(String content) {
        return Text.literal(content);
    }

    /**
     * Creates a Text object with formatting applied based on the tags in the content.
     *
     * @param content The text content with formatting tags.
     * @return A formatted Text object.
     */
    public static Text createTaggedText(String content) {
        return TagFormatter.applyFormatting(content);
    }

    /**
     * Creates a Text object with a gradient effect.
     *
     * @param content The text content to apply the gradient to.
     * @param startColor The starting color of the gradient in hexadecimal format.
     * @param endColor The ending color of the gradient in hexadecimal format.
     * @return A Text object with the gradient effect applied.
     */
    public static Text createGradientText(String content, String startColor, String endColor) {
        return GradientHandler.createGradientText(content, startColor, endColor);
    }
}