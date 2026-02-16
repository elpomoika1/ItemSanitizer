package me.elpomoika.itemSanitizer.registry;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.elpomoika.itemSanitizer.config.MainConfig;
import me.elpomoika.itemSanitizer.entity.ItemRule;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Getter
public final class ItemRuleRegistry {
    private final List<ItemRule> rules = new ArrayList<>();

    public void load(MainConfig config) {
        rules.clear();
        rules.addAll(config.getItems().values());
    }
}
