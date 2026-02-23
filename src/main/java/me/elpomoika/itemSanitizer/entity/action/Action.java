package me.elpomoika.itemSanitizer.entity.action;

import eu.okaeri.configs.schema.GenericsDeclaration;
import eu.okaeri.configs.serdes.DeserializationData;
import eu.okaeri.configs.serdes.SerializationData;
import eu.okaeri.configs.serdes.serializable.ConfigSerializable;
import lombok.*;
import org.bukkit.Material;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Action implements ConfigSerializable {
    private ActionType type;
    private Material replaceMaterial;
    private String replaceCommand;

    @Override
    public void serialize(@NonNull SerializationData data, @NonNull GenericsDeclaration generics) {
        if (type != null) {
            data.set("type", type);
        }
        if (replaceMaterial != null) {
            data.set("replaceMaterial", replaceMaterial.name());
        }
        if (replaceCommand != null) {
            data.set("replaceCommand", replaceCommand);
        }
    }

    public static Action deserialize(@NonNull DeserializationData data, @NonNull GenericsDeclaration generics) {
        ActionType type = data.get("type", ActionType.class);

        String materialName = data.get("replaceMaterial", String.class);
        Material replaceMaterial = materialName != null ? Material.getMaterial(materialName) : null;

        String replaceCommand = data.get("replaceCommand", String.class);

        return new Action(type, replaceMaterial, replaceCommand);
    }
}
