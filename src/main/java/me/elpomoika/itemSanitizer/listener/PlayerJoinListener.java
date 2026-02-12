package me.elpomoika.itemSanitizer.listener;

import lombok.RequiredArgsConstructor;
import me.elpomoika.itemSanitizer.config.ConfigLoader;
import me.elpomoika.itemSanitizer.entity.ItemRule;
import me.elpomoika.itemSanitizer.entity.action.ActionRule;
import me.elpomoika.itemSanitizer.entity.action.ActionType;
import me.elpomoika.itemSanitizer.util.InventoryCleaner;
import me.elpomoika.itemSanitizer.util.MatchUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;

@RequiredArgsConstructor
public class PlayerJoinListener implements Listener {
    private final ConfigLoader config;

    @EventHandler(priority = EventPriority.MONITOR)
    public void onJoin(PlayerJoinEvent event) {
        if (!config.isCheck()) return;
        Player player = event.getPlayer();

        final Collection<ItemRule> rules = config.getRules().values();

        InventoryCleaner.cleanInventory(player.getInventory(), rules);
        InventoryCleaner.cleanInventory(player.getEnderChest(), rules);
    }
}
