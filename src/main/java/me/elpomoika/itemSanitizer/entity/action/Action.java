package me.elpomoika.itemSanitizer.entity.action;

import org.bukkit.Material;

import javax.annotation.Nullable;

public record Action(
        ActionType type,
        @Nullable Material replaceMaterial,
        @Nullable String replaceCommand
) {}
