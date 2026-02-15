package me.elpomoika.itemSanitizer.listener;

import lombok.RequiredArgsConstructor;
import me.elpomoika.itemSanitizer.config.MainConfig;
import me.elpomoika.itemSanitizer.registry.ItemRuleRegistry;
import me.elpomoika.itemSanitizer.util.InventoryCleaner;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

@RequiredArgsConstructor
public class InventoryClickListener implements Listener {
    private final MainConfig config;
    private final ItemRuleRegistry ruleRegistry;

    @EventHandler(ignoreCancelled = true)
    public void onInventoryClick(InventoryClickEvent event) {
        if (!config.isCheck()) return;
        if (!(event.getWhoClicked() instanceof Player player)) return;

        Inventory clicked = event.getClickedInventory();
        if (clicked == null) return;

        if (clicked.equals(event.getView().getTopInventory())) {
            ItemStack item = event.getCurrentItem();
            if (item == null) return;

            ItemStack result = InventoryCleaner.processItem(item, ruleRegistry.getRules(), player);

            if (result == null) {
                clicked.setItem(event.getSlot(), null);
                event.setCancelled(true);
                return;
            }

            if (result != item) {
                clicked.setItem(event.getSlot(), result);
            }
        }
    }
}
