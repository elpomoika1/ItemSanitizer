package me.elpomoika.itemSanitizer.entity;

import javax.annotation.Nullable;

public record MatchRule(
        @Nullable String displayName,
        @Nullable String lore,
        @Nullable Integer customModelData
) {}

