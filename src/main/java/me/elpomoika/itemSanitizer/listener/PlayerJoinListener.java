package me.elpomoika.itemSanitizer.listener;

import lombok.RequiredArgsConstructor;
import me.elpomoika.itemSanitizer.config.MainConfig;
import me.elpomoika.itemSanitizer.entity.ItemRule;
import me.elpomoika.itemSanitizer.registry.ItemRuleRegistry;
import me.elpomoika.itemSanitizer.inventory.InventoryRuleProcessor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Collection;

@RequiredArgsConstructor
public class PlayerJoinListener implements Listener {
    private final MainConfig config;
    private final ItemRuleRegistry ruleRegistry;
    private final InventoryRuleProcessor ruleProcessor;

    @EventHandler(priority = EventPriority.MONITOR)
    public void onJoin(PlayerJoinEvent event) {
        if (!config.isCheck()) return;
        Player player = event.getPlayer();

        final Collection<ItemRule> rules = ruleRegistry.getRules();

        ruleProcessor.cleanInventory(player, player.getInventory(), rules);
        ruleProcessor.cleanInventory(player, player.getEnderChest(), rules);
    }
}
