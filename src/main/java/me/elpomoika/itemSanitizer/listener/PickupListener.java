package me.elpomoika.itemSanitizer.listener;

import lombok.RequiredArgsConstructor;
import me.elpomoika.itemSanitizer.config.ConfigLoader;
import me.elpomoika.itemSanitizer.util.InventoryCleaner;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.inventory.ItemStack;

@RequiredArgsConstructor
public class PickupListener implements Listener {
    private final ConfigLoader configLoader;

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPickup(EntityPickupItemEvent event) {
        if (!configLoader.isCheck()) return;
        if (!(event.getEntity() instanceof Player player)) return;
        ItemStack item = event.getItem().getItemStack();

        ItemStack replaced = InventoryCleaner.processItem(item, configLoader.getRules().values());

        event.setCancelled(true);
        event.getItem().remove();
        if (replaced != null) {
            player.getInventory().addItem(replaced);
        }
    }
}
