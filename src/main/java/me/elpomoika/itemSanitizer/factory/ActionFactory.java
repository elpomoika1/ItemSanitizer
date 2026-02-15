package me.elpomoika.itemSanitizer.factory;

import lombok.experimental.UtilityClass;
import me.elpomoika.itemSanitizer.entity.ItemRule;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@UtilityClass
public final class ActionFactory {
    public ItemStack applyAction(ItemStack item, ItemRule rule, Player player) {
        switch (rule.action().type()) {
            case REMOVE -> {
                return null;
            }

            case REPLACE -> {
                Material replaceMaterial = rule.action().replaceMaterial();
                String replaceCommand = rule.action().replaceCommand();
                if (replaceMaterial == null || replaceMaterial.isEmpty()) return null;

                if (replaceCommand != null && !replaceCommand.isBlank()) {
                    String command = replaceCommand.replace("%player%", player.getName());
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
                }

                return new ItemStack(replaceMaterial, item.getAmount());
            }
        }

        return item;
    }
}
