package me.elpomoika.itemSanitizer.listener;

import lombok.RequiredArgsConstructor;
import me.elpomoika.itemSanitizer.config.ConfigLoader;
import me.elpomoika.itemSanitizer.entity.ItemRule;
import me.elpomoika.itemSanitizer.entity.action.ActionRule;
import me.elpomoika.itemSanitizer.entity.action.ActionType;
import me.elpomoika.itemSanitizer.util.MatchUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

@RequiredArgsConstructor
public class PlayerJoinListener implements Listener {
    private final ConfigLoader config;

    @EventHandler(priority = EventPriority.MONITOR)
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        cleanInventory(player.getInventory());
        cleanInventory(player.getEnderChest());
    }

    private void cleanInventory(Inventory inventory) {
        if (inventory == null) return;

        for (int slot = 0; slot < inventory.getSize(); slot++) {
            ItemStack item = inventory.getItem(slot);
            if (item == null) continue;

            ItemStack result = processItem(item);
            if (result != item) {
                inventory.setItem(slot, result);
            }
        }
    }

    private ItemStack processItem(ItemStack item) {
        for (ItemRule rule : config.getRules().values()) {
            if (!MatchUtil.matches(item, rule)) continue;

            return applyAction(item, rule.action());
        }
        return item;
    }

    private ItemStack applyAction(ItemStack original, ActionRule action) {
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
