package me.elpomoika.itemSanitizer.entity;

import eu.okaeri.configs.schema.GenericsDeclaration;
import eu.okaeri.configs.serdes.DeserializationData;
import eu.okaeri.configs.serdes.SerializationData;
import eu.okaeri.configs.serdes.serializable.ConfigSerializable;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MatchRule implements ConfigSerializable {
    private String displayName;
    private List<String> lore;
    private Integer customModelData;

    @Override
    public void serialize(@NonNull SerializationData data, @NonNull GenericsDeclaration generics) {
        if (displayName != null) {
            data.set("displayname", displayName);
        }
        if (lore != null && !lore.isEmpty()) {
            data.set("lore", lore);
        }
        if (customModelData != null) {
            data.set("custom-model-data", customModelData);
        }
    }

    public static MatchRule deserialize(@NonNull DeserializationData data, @NonNull GenericsDeclaration generics) {
        String displayName = data.get("displayname", String.class);
        List<String> lore = data.getAsList("lore", String.class);
        Integer customModelData = data.get("custom-model-data", Integer.class);
        return new MatchRule(displayName, lore, customModelData);
    }
}
