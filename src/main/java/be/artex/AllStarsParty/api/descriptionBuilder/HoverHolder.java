package be.artex.AllStarsParty.api.descriptionBuilder;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class HoverHolder {
    private final String baseText;
    private final String hoverText;

    public HoverHolder(String baseText, String hoverText) {
        this.baseText = baseText;
        this.hoverText = hoverText;
    }

    /**
     * Makes a TextComponent of the baseText with the HoverEvent.Action.SHOW_TEXT of hoverText.
     * @return the TextComponent with the hoverText.
     */
    public TextComponent toComponent() {
        TextComponent comp = new TextComponent(this.baseText);
        TextComponent hoverText = new TextComponent(this.hoverText);
        comp.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new BaseComponent[]{hoverText}));

        return comp;
    }
}
