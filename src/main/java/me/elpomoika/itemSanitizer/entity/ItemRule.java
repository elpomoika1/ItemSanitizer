package me.elpomoika.itemSanitizer.entity;

import eu.okaeri.configs.schema.GenericsDeclaration;
import eu.okaeri.configs.serdes.DeserializationData;
import eu.okaeri.configs.serdes.SerializationData;
import eu.okaeri.configs.serdes.serializable.ConfigSerializable;
import eu.okaeri.configs.yaml.bukkit.serdes.itemstack.ItemStackFormat;
import eu.okaeri.configs.yaml.bukkit.serdes.itemstack.ItemStackSpec;
import lombok.*;
import me.elpomoika.itemSanitizer.entity.action.Action;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ItemRule implements ConfigSerializable {
    private Material material;
    @ItemStackSpec(format = ItemStackFormat.NATURAL)
    private ItemStack itemStack;
    private MatchRule match;
    private Action action;

    @Override
    public void serialize(@NonNull SerializationData data, @NonNull GenericsDeclaration generics) {
        if (material != null) {
            data.set("material", material.name());
        }
        if (itemStack != null) {
            data.set("itemStack", itemStack, ItemStack.class);
        }
        if (match != null) {
            data.set("match", match);
        }
        if (action != null) {
            data.set("action", action);
        }
    }

    public static ItemRule deserialize(DeserializationData data, GenericsDeclaration generics) {
        String matName = data.get("material", String.class);
        Material material = matName != null ? Material.valueOf(matName) : null;

        ItemStack itemStack = data.get("itemStack", ItemStack.class);

        MatchRule match = data.get("match", MatchRule.class);
        Action action = data.get("action", Action.class);

        return new ItemRule(material, itemStack, match, action);
    }
}
