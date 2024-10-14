package org.nell.easytextapi;

import net.minecraft.text.ClickEvent;

/**
 * The EventHandlers class manages click and hover events for text formatting.
 */
public class EventHandlers {

    /**
     * Retrieves the ClickEvent.Action based on the given action string.
     *
     * @param action The action string (e.g., "run_command", "open_url", etc.).
     * @return The corresponding ClickEvent.Action, or null if not found.
     */
    public static ClickEvent.Action getClickAction(String action) {
        switch (action.toLowerCase()) {
            case "run_command":
                return ClickEvent.Action.RUN_COMMAND;
            case "open_url":
                return ClickEvent.Action.OPEN_URL;
            case "suggest_command":
                return ClickEvent.Action.SUGGEST_COMMAND;
            case "copy_to_clipboard":
                return ClickEvent.Action.COPY_TO_CLIPBOARD;
            default:
                return null;
        }
    }
}