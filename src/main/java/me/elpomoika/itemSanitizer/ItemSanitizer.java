package me.elpomoika.itemSanitizer;

import co.aikar.commands.PaperCommandManager;
import lombok.Getter;
import me.elpomoika.itemSanitizer.command.SanitizerCommand;
import me.elpomoika.itemSanitizer.config.ConfigLoader;
import me.elpomoika.itemSanitizer.listener.InventoryClickListener;
import me.elpomoika.itemSanitizer.listener.PickupListener;
import me.elpomoika.itemSanitizer.listener.PlayerJoinListener;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class ItemSanitizer extends JavaPlugin {
    private ConfigLoader configLoader;
    private PaperCommandManager commandManager;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        initConfigs();
        this.commandManager = new PaperCommandManager(this);

        registerBukkitListeners();

        commandManager.registerCommand(new SanitizerCommand(configLoader));
    }

    private void registerBukkitListeners() {
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(configLoader), this);
        getServer().getPluginManager().registerEvents(new PickupListener(configLoader), this);
        getServer().getPluginManager().registerEvents(new InventoryClickListener(configLoader), this);
    }

    private void initConfigs() {
        this.configLoader = new ConfigLoader(this);
        this.configLoader.setup();
    }
}
