package me.elpomoika.itemSanitizer.util;

import lombok.experimental.UtilityClass;
import me.elpomoika.itemSanitizer.entity.ItemRule;
import me.elpomoika.itemSanitizer.entity.MatchRule;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

@UtilityClass
public class MatchUtil {
    private static final PlainTextComponentSerializer PLAIN = PlainTextComponentSerializer.plainText();

    public boolean matches(ItemStack item, ItemRule rule) {
        if (item == null) return false;
        if (item.getType() != rule.material()) return false;

        MatchRule match = rule.match();
        if (match == null) return true;

        ItemMeta meta = item.getItemMeta();
        if (meta == null) return false;

        if (!matchDisplayName(match, meta)) return false;
        if (!matchLore(match, meta)) return false;

        return matchCustomModelData(match, meta);
    }

    private boolean matchDisplayName(MatchRule rule, ItemMeta meta) {
        String expected = rule.displayName();
        if (expected == null) return true;

        Component name = meta.displayName();
        if (name == null) return false;

        String actual = PLAIN.serialize(name);
        return actual.equalsIgnoreCase(expected);
    }

    private boolean matchLore(MatchRule rule, ItemMeta meta) {
        String expected = rule.lore();
        if (expected == null) return true;

        if (!meta.hasLore() || meta.lore() == null) return false;

        return meta.lore().stream()
                .map(PLAIN::serialize)
                .anyMatch(line -> line.contains(expected));
    }

    private boolean matchCustomModelData(MatchRule rule, ItemMeta meta) {
        Integer cmd = rule.customModelData();
        if (cmd == null) return true;

        return meta.hasCustomModelData()
                && meta.getCustomModelData() == cmd;
    }
}
