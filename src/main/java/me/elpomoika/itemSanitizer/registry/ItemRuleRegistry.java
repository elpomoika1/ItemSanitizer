package me.elpomoika.itemSanitizer.registry;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.elpomoika.itemSanitizer.config.MainConfig;
import me.elpomoika.itemSanitizer.entity.ItemRule;
import me.elpomoika.itemSanitizer.entity.MatchRule;
import me.elpomoika.itemSanitizer.entity.action.Action;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Getter
public final class ItemRuleRegistry {
    private final List<ItemRule> rules = new ArrayList<>();

    public void load(MainConfig config) {
        rules.clear();

        for (MainConfig.ItemsConfig item : config.getItems().values()) {
            Material material = Material.valueOf(item.getTargetMaterial());

            MatchRule match = parseMatch(item.getMatch());
            Action action = parseAction(item.getAction());

            rules.add(new ItemRule(material, match, action));
        }
    }

    private static MatchRule parseMatch(MainConfig.ItemsConfig.MatchConfig match) {
        if (match == null) return null;

        String displayName = match.getDisplayName();
        List<String> loreContains = match.getLoreContains();
        Integer customModelData = match.getCustomModalData();

        return new MatchRule(displayName, loreContains, customModelData);
    }

    private static Action parseAction(MainConfig.ItemsConfig.ActionConfig action) {
        Material replace = action.getReplaceMaterial() == null || action.getReplaceMaterial().isBlank()
                ? null
                : Material.valueOf(action.getReplaceMaterial());

        return new Action(action.getType(), replace, action.getReplaceCommand());
    }
}
