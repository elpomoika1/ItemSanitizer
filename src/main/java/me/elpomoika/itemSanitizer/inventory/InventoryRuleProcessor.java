package me.elpomoika.itemSanitizer.inventory;

import lombok.RequiredArgsConstructor;
import me.elpomoika.itemSanitizer.entity.ItemRule;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;

@RequiredArgsConstructor
public final class InventoryRuleProcessor {
    private final ItemRuleMatcher ruleMatcher;
    private final ItemRuleApplier ruleApplier;

    public void cleanInventory(Player player, Inventory inventory, Collection<ItemRule> rules) {
        if (inventory == null) return;

        for (int slot = 0; slot < inventory.getSize(); slot++) {
            ItemStack item = inventory.getItem(slot);
            if (item == null) continue;

            ItemStack result = applyRules(item, rules, player);
            if (result != item) {
                inventory.setItem(slot, result);
            }
        }
    }

    public ItemStack applyRules(ItemStack item, Collection<ItemRule> rules, Player player) {
        for (ItemRule rule : rules) {
            if (!ruleMatcher.matchesRule(item, rule)) continue;

            return ruleApplier.apply(item, rule, player);
        }
        return item;
    }
}
