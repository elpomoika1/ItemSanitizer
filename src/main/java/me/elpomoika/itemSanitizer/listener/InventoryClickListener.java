package me.elpomoika.itemSanitizer.listener;

import lombok.RequiredArgsConstructor;
import me.elpomoika.itemSanitizer.config.ConfigLoader;
import me.elpomoika.itemSanitizer.util.InventoryCleaner;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

@RequiredArgsConstructor
public class InventoryClickListener implements Listener {
    private final ConfigLoader configLoader;

    @EventHandler(ignoreCancelled = true)
    public void onInventoryClick(InventoryClickEvent event) {
        if (!configLoader.isCheck()) return;
        if (!(event.getWhoClicked() instanceof Player)) return;

        Inventory clicked = event.getClickedInventory();
        if (clicked == null) return;

        if (clicked.equals(event.getView().getTopInventory())) {
            ItemStack item = event.getCurrentItem();
            if (item == null) return;

            ItemStack result = InventoryCleaner.processItem(item, configLoader.getRules().values());

            if (result == null) {
                event.setCancelled(true);
                clicked.setItem(event.getSlot(), null);
                return;
            }

            if (result != item) {
                clicked.setItem(event.getSlot(), result);
            }
        }
    }
}
