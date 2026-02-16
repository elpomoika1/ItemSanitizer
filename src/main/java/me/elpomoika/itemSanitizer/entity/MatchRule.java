package me.elpomoika.itemSanitizer.entity;

import javax.annotation.Nullable;
import java.util.List;

public record MatchRule(
        @Nullable String displayName,
        @Nullable List<String> lore,
        @Nullable Integer customModelData
) {}

