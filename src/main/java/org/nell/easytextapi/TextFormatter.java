package org.nell.easytextapi;

import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TextFormatter {
    private static final Logger LOGGER = LoggerFactory.getLogger(TextFormatter.class);

    public static Text createStyledText(String content, Formatting... formattings) {
        try {
            Text text = Text.literal(content);
            for (Formatting formatting : formattings) {
                if (formatting == null) {
                    LOGGER.warn("Null formatting passed for text: {}", content);
                    continue;
                }
                text = text.copy().formatted(formatting);
            }
            return text;
        } catch (Exception e) {
            LOGGER.error("Error creating styled text for content: {}", content, e);
            return Text.literal(content);
        }
    }
}