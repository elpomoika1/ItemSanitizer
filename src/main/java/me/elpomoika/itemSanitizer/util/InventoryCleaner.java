package me.elpomoika.itemSanitizer.util;

import lombok.experimental.UtilityClass;
import me.elpomoika.itemSanitizer.entity.ItemRule;
import me.elpomoika.itemSanitizer.factory.ActionFactory;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;

@UtilityClass
public final class InventoryCleaner {
    public void cleanInventory(Player player, Inventory inventory, Collection<ItemRule> rules) {
        if (inventory == null) return;

        for (int slot = 0; slot < inventory.getSize(); slot++) {
            ItemStack item = inventory.getItem(slot);
            if (item == null) continue;

            ItemStack result = processItem(item, rules, player);
            if (result != item) {
                inventory.setItem(slot, result);
            }
        }
    }

    public ItemStack processItem(ItemStack item, Collection<ItemRule> rules, Player player) {
        for (ItemRule rule : rules) {
            if (!MatchUtil.matches(item, rule)) continue;

            return ActionFactory.applyAction(item, rule, player);
        }
        return item;
    }
}
