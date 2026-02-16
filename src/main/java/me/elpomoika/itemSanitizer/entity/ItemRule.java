package me.elpomoika.itemSanitizer.entity;

import me.elpomoika.itemSanitizer.entity.action.Action;
import org.bukkit.Material;

public record ItemRule(
        Material material,
        MatchRule match,
        Action action
) {}