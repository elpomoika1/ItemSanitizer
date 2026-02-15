package me.elpomoika.itemSanitizer.listener;

import lombok.RequiredArgsConstructor;
import me.elpomoika.itemSanitizer.config.MainConfig;
import me.elpomoika.itemSanitizer.registry.ItemRuleRegistry;
import me.elpomoika.itemSanitizer.util.InventoryCleaner;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.inventory.ItemStack;

@RequiredArgsConstructor
public class PickupListener implements Listener {
    private final MainConfig config;
    private final ItemRuleRegistry ruleRegistry;

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPickup(EntityPickupItemEvent event) {
        if (!config.isCheck()) return;
        if (!(event.getEntity() instanceof Player player)) return;
        ItemStack item = event.getItem().getItemStack();

        ItemStack replaced = InventoryCleaner.processItem(item, ruleRegistry.getRules(), player);

        event.getItem().remove();
        event.setCancelled(true);
        if (replaced != null) {
            player.getInventory().addItem(replaced);
        }
    }
}
