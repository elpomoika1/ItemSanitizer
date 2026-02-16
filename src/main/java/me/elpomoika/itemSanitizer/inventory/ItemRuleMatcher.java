package me.elpomoika.itemSanitizer.inventory;

import me.elpomoika.itemSanitizer.entity.ItemRule;
import me.elpomoika.itemSanitizer.entity.MatchRule;
import me.elpomoika.itemSanitizer.util.ComponentTextUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public final class ItemRuleMatcher {
    public boolean matchesRule(ItemStack item, ItemRule rule) {
        if (item == null) return false;

        if (rule.getItemStack() != null)
            return item.isSimilar(rule.getItemStack());

        if (item.getType() != rule.getMaterial()) return false;

        MatchRule match = rule.getMatch();
        if (match == null) return true;

        ItemMeta meta = item.getItemMeta();
        if (meta == null) return false;

        if (!matchDisplayName(match, meta)) return false;
        if (!matchLore(match, meta)) return false;

        return matchCustomModelData(match, meta);
    }

    private boolean matchDisplayName(MatchRule rule, ItemMeta meta) {
        String expected = rule.getDisplayName();
        if (expected == null) return true;

        Component name = meta.displayName();
        if (name == null) return false;

        String actual = ComponentTextUtil.toPlain(name);
        return actual.equalsIgnoreCase(expected);
    }

    private boolean matchLore(MatchRule rule, ItemMeta meta) {
        List<String> expected = rule.getLore();
        if (expected == null || expected.isEmpty()) return true;

        if (meta == null || !meta.hasLore()) return false;

        List<Component> actualLore = meta.lore();
        if (actualLore == null) return false;

        List<String> plainLore = actualLore.stream()
                .map(ComponentTextUtil::toPlain)
                .toList();

        return expected.stream().allMatch(expectedLine ->
                plainLore.stream().anyMatch(actualLine ->
                        actualLine.contains(expectedLine)
                )
        );
    }

    private boolean matchCustomModelData(MatchRule rule, ItemMeta meta) {
        Integer cmd = rule.getCustomModelData();
        if (cmd == null) return true;

        return meta.hasCustomModelData()
                && meta.getCustomModelData() == cmd;
    }
}
