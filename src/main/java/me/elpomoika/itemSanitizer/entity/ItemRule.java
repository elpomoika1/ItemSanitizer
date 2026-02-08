package me.elpomoika.itemSanitizer.entity;

import me.elpomoika.itemSanitizer.entity.action.ActionRule;
import org.bukkit.Material;

public record ItemRule(
        Material material,
        MatchRule match,
        ActionRule action
) {}