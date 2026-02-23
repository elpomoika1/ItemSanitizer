package me.elpomoika.itemSanitizer.config;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.ToString;
import me.elpomoika.itemSanitizer.entity.ItemRule;
import me.elpomoika.itemSanitizer.entity.MatchRule;
import me.elpomoika.itemSanitizer.entity.action.Action;
import me.elpomoika.itemSanitizer.entity.action.ActionType;
import org.bukkit.Material;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class MainConfig extends OkaeriConfig {
    @Comment("Is enable checks")
    private boolean check = true;

    private Map<String, ItemRule> items = new HashMap<>(Map.of(
            "illegal_helmet", new ItemRule(
                    Material.NETHERITE_SWORD,
                    null,
                    new MatchRule("tsundere", List.of(), null),
                    new Action(ActionType.REPLACE, Material.STONE, ""))));
}