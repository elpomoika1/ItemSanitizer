package me.elpomoika.itemSanitizer.util;

import lombok.experimental.UtilityClass;
import me.elpomoika.itemSanitizer.entity.ItemRule;
import me.elpomoika.itemSanitizer.entity.action.ActionRule;
import me.elpomoika.itemSanitizer.entity.action.ActionType;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

@UtilityClass
public final class InventoryCleaner {
    public void cleanInventory(Inventory inventory, Collection<ItemRule> rules) {
        if (inventory == null) return;

        for (int slot = 0; slot < inventory.getSize(); slot++) {
            ItemStack item = inventory.getItem(slot);
            if (item == null) continue;

            ItemStack result = processItem(item, rules);
            if (result != item) {
                inventory.setItem(slot, result);
            }
        }
    }

    public ItemStack processItem(ItemStack item, Collection<ItemRule> rules) {
        for (ItemRule rule : rules) {
            if (!MatchUtil.matches(item, rule)) continue;

            return applyAction(item, rule.action());
        }
        return item;
    }


    private @Nullable ItemStack applyAction(ItemStack original, ActionRule action) {
        if (action.type() == ActionType.REMOVE) {
            return null;
        }

        if (action.type() == ActionType.REPLACE) {
            Material replace = action.replaceMaterial();
            if (replace == null) {
                return null;
            }

            return new ItemStack(replace, original.getAmount());
        }

        return original;
    }
}
