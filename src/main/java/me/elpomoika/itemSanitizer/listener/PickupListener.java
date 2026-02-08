package me.elpomoika.itemSanitizer.listener;

import lombok.RequiredArgsConstructor;
import me.elpomoika.itemSanitizer.config.ConfigLoader;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;

@RequiredArgsConstructor
// todo
public class PickupListener implements Listener {
    private ConfigLoader configLoader;

    @EventHandler
    public void onPickup(EntityPickupItemEvent event) {

    }
}
