package me.elpomoika.itemSanitizer.inventory;

import me.elpomoika.itemSanitizer.entity.ItemRule;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public final class ItemRuleApplier {
    public ItemStack apply(ItemStack item, ItemRule rule, Player player) {
        switch (rule.action().type()) {
            case REMOVE -> {
                return null;
            }

            case REPLACE -> {
                Material replaceMaterial = rule.action().replaceMaterial();
                String replaceCommand = rule.action().replaceCommand();
                if (replaceMaterial != null && !replaceMaterial.isEmpty()) {
                    return new ItemStack(replaceMaterial, item.getAmount());
                }

                if (replaceCommand != null && !replaceCommand.isBlank()) {
                    String command = replaceCommand.replace("%player%", player.getName());
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
                    return null;
                }
            }
        }

        return item;
    }
}
