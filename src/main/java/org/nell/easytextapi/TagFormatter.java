package org.nell.easytextapi;

import net.minecraft.text.ClickEvent;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The TagFormatter class is responsible for parsing and applying formatting to tagged text.
 * It supports various formatting options such as colors, styles, click events, hover events, and gradients.
 */
public class TagFormatter {

    private static final Logger LOGGER = LoggerFactory.getLogger(TagFormatter.class);

    private static final Map<String, Formatting> TAG_TO_FORMATTING = new HashMap<>();

    static {
        TAG_TO_FORMATTING.put("black", Formatting.BLACK);
        TAG_TO_FORMATTING.put("dark_blue", Formatting.DARK_BLUE);
        TAG_TO_FORMATTING.put("dark_green", Formatting.DARK_GREEN);
        TAG_TO_FORMATTING.put("dark_aqua", Formatting.DARK_AQUA);
        TAG_TO_FORMATTING.put("dark_red", Formatting.DARK_RED);
        TAG_TO_FORMATTING.put("dark_purple", Formatting.DARK_PURPLE);
        TAG_TO_FORMATTING.put("gold", Formatting.GOLD);
        TAG_TO_FORMATTING.put("gray", Formatting.GRAY);
        TAG_TO_FORMATTING.put("dark_gray", Formatting.DARK_GRAY);
        TAG_TO_FORMATTING.put("blue", Formatting.BLUE);
        TAG_TO_FORMATTING.put("green", Formatting.GREEN);
        TAG_TO_FORMATTING.put("aqua", Formatting.AQUA);
        TAG_TO_FORMATTING.put("red", Formatting.RED);
        TAG_TO_FORMATTING.put("light_purple", Formatting.LIGHT_PURPLE);
        TAG_TO_FORMATTING.put("yellow", Formatting.YELLOW);
        TAG_TO_FORMATTING.put("white", Formatting.WHITE);
        TAG_TO_FORMATTING.put("bold", Formatting.BOLD);
        TAG_TO_FORMATTING.put("italic", Formatting.ITALIC);
        TAG_TO_FORMATTING.put("underline", Formatting.UNDERLINE);
        TAG_TO_FORMATTING.put("strikethrough", Formatting.STRIKETHROUGH);
        TAG_TO_FORMATTING.put("obfuscated", Formatting.OBFUSCATED);
    }

    /**
     * Applies formatting to the given content based on the tags present in the text.
     *
     * @param content The text content with formatting tags.
     * @return A formatted Text object.
     */
    public static Text applyFormatting(String content) {
        Stack<Object> formattingStack = new Stack<>();
        Text result = Text.literal("");

        Pattern pattern = Pattern.compile("<(/?)(\\w+)(\\s+[^>]+)?>([^<]*)");
        Matcher matcher = pattern.matcher(content);

        int lastEnd = 0;

        while (matcher.find()) {
            String tagType = matcher.group(1);
            String tagName = matcher.group(2).toLowerCase();
            String tagAttributes = matcher.group(3);
            String text = matcher.group(4);

            if (matcher.start() > lastEnd) {
                result = result.copy().append(Text.literal(content.substring(lastEnd, matcher.start())));
            }

            if (tagType.isEmpty()) {
                handleOpeningTag(tagName, tagAttributes, formattingStack);
            } else if (tagType.equals("/") && !formattingStack.isEmpty()) {
                formattingStack.pop();
            }

            if (!text.isEmpty()) {
                result = result.copy().append(formatText(text, formattingStack));
            }

            lastEnd = matcher.end();
        }

        if (lastEnd < content.length()) {
            result = result.copy().append(Text.literal(content.substring(lastEnd)));
        }

        return result;
    }

    private static void handleOpeningTag(String tagName, String tagAttributes, Stack<Object> formattingStack) {
        switch (tagName) {
            case "click":
                handleClickTag(tagAttributes, formattingStack);
                break;
            case "hover":
                handleHoverTag(tagAttributes, formattingStack);
                break;
            case "hex":
                handleHexTag(tagAttributes, formattingStack);
                break;
            case "gradient":
                handleGradientTag(tagAttributes, formattingStack);
                break;
            default:
                handleDefaultTag(tagName, formattingStack);
        }
    }

    private static void handleClickTag(String tagAttributes, Stack<Object> formattingStack) {
        if (tagAttributes != null) {
            Map<String, String> attributes = parseAttributes(tagAttributes);
            String action = attributes.get("action");
            String value = attributes.get("value");

            if (action != null && value != null) {
                ClickEvent.Action clickAction = EventHandlers.getClickAction(action);
                if (clickAction != null) {
                    ClickEvent clickEvent = new ClickEvent(clickAction, value);
                    Style style = Style.EMPTY.withClickEvent(clickEvent);
                    formattingStack.push(style);
                } else {
                    LOGGER.warn("Unsupported click action: {}. Please check the action value.", action);
                }
            } else {
                LOGGER.warn("Click tag missing action or value attributes. Attributes: {}", attributes);
            }
        } else {
            LOGGER.warn("Click tag attributes are null.");
        }
    }

    private static void handleHoverTag(String tagAttributes, Stack<Object> formattingStack) {
        if (tagAttributes != null) {
            Map<String, String> attributes = parseAttributes(tagAttributes);
            String hoverText = attributes.get("text");

            if (hoverText != null) {
                HoverEvent hoverEvent = new HoverEvent(HoverEvent.Action.SHOW_TEXT, Text.literal(hoverText));
                Style style = Style.EMPTY.withHoverEvent(hoverEvent);
                formattingStack.push(style);
            } else {
                LOGGER.warn("Hover tag is missing 'text' attribute. Attributes: {}", attributes);
            }
        } else {
            LOGGER.warn("Hover tag attributes are null.");
        }
    }

    private static void handleHexTag(String tagAttributes, Stack<Object> formattingStack) {
        if (tagAttributes != null) {
            String hexColor = tagAttributes.trim();
            hexColor = hexColor.startsWith("#") ? hexColor.substring(1) : hexColor;

            if (hexColor.matches("^[A-Fa-f0-9]{6}$")) {
                try {
                    int color = Integer.parseInt(hexColor, 16);
                    Style style = Style.EMPTY.withColor(color);
                    formattingStack.push(style);
                } catch (NumberFormatException e) {
                    LOGGER.warn("Invalid HEX color format detected: {}. Error: {}", hexColor, e.getMessage());
                }
            } else {
                LOGGER.warn("Invalid HEX color format: {}. Expected format: RRGGBB.", hexColor);
            }
        } else {
            LOGGER.warn("Hex tag attributes are null.");
        }
    }

    private static void handleGradientTag(String tagAttributes, Stack<Object> formattingStack) {
        if (tagAttributes != null) {
            Map<String, String> attributes = parseAttributes(tagAttributes);
            String startColor = attributes.get("from");
            String endColor = attributes.get("to");
            if (startColor != null && endColor != null) {
                formattingStack.push(new GradientInfo(startColor, endColor));
            } else {
                LOGGER.warn("Gradient tag missing 'from' or 'to' attributes. Attributes: {}", attributes);
            }
        } else {
            LOGGER.warn("Gradient tag attributes are null.");
        }
    }

    private static void handleDefaultTag(String tagName, Stack<Object> formattingStack) {
        Formatting formatting = TAG_TO_FORMATTING.get(tagName);
        if (formatting != null) {
            formattingStack.push(formatting);
        } else {
            LOGGER.warn("Unrecognized tag: <{}>. Please ensure the tag is correctly defined.", tagName);
        }
    }

    private static Text formatText(String text, Stack<Object> formattingStack) {
        if (!formattingStack.isEmpty() && formattingStack.peek() instanceof GradientInfo) {
            GradientInfo gradientInfo = (GradientInfo) formattingStack.peek();
            return GradientHandler.createGradientText(text, gradientInfo.startColor, gradientInfo.endColor);
        } else {
            Text formattedText = Text.literal(text);
            for (Object format : formattingStack) {
                if (format instanceof Formatting) {
                    formattedText = formattedText.copy().formatted((Formatting) format);
                } else if (format instanceof Style) {
                    formattedText = formattedText.copy().setStyle(formattedText.getStyle().withParent((Style) format));
                }
            }
            return formattedText;
        }
    }

    private static Map<String, String> parseAttributes(String attributes) {
        Map<String, String> map = new HashMap<>();
        Pattern attrPattern = Pattern.compile("(\\w+)=\"([^\"]*)\"");
        Matcher matcher = attrPattern.matcher(attributes);

        while (matcher.find()) {
            map.put(matcher.group(1), matcher.group(2));
        }

        return map;
    }

    private static class GradientInfo {
        String startColor;
        String endColor;

        GradientInfo(String startColor, String endColor) {
            this.startColor = startColor;
            this.endColor = endColor;
        }
    }
}
