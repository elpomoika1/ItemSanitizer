package me.elpomoika.itemSanitizer;

import co.aikar.commands.PaperCommandManager;
import lombok.Getter;
import me.elpomoika.itemSanitizer.command.SanitizerCommand;
import me.elpomoika.itemSanitizer.config.ConfigLoader;
import me.elpomoika.itemSanitizer.listener.PlayerJoinListener;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class ItemSanitizer extends JavaPlugin {
    private ConfigLoader configLoader;

    private PaperCommandManager commandManager;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        this.configLoader = new ConfigLoader(this);
        this.configLoader.setup();

        this.commandManager = new PaperCommandManager(this);

        getServer().getPluginManager().registerEvents(new PlayerJoinListener(configLoader), this);

        commandManager.registerCommand(new SanitizerCommand(configLoader));
    }
}
