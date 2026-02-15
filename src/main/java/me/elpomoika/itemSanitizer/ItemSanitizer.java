package me.elpomoika.itemSanitizer;

import co.aikar.commands.PaperCommandManager;
import eu.okaeri.configs.ConfigManager;
import eu.okaeri.configs.serdes.okaeri.SerdesOkaeriBukkit;
import eu.okaeri.configs.yaml.bukkit.YamlBukkitConfigurer;
import lombok.Getter;
import me.elpomoika.itemSanitizer.command.SanitizerCommand;
import me.elpomoika.itemSanitizer.config.MainConfig;
import me.elpomoika.itemSanitizer.listener.InventoryClickListener;
import me.elpomoika.itemSanitizer.listener.PickupListener;
import me.elpomoika.itemSanitizer.listener.PlayerJoinListener;
import me.elpomoika.itemSanitizer.registry.ItemRuleRegistry;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

@Getter
public final class ItemSanitizer extends JavaPlugin {
    private PaperCommandManager commandManager;
    private MainConfig mainConfig;
    private ItemRuleRegistry ruleRegistry;

    @Override
    public void onEnable() {
        initConfig();
        this.commandManager = new PaperCommandManager(this);

        registerBukkitListeners();

        commandManager.registerCommand(new SanitizerCommand(mainConfig, ruleRegistry));
    }

    private void registerBukkitListeners() {
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(mainConfig, ruleRegistry), this);
        getServer().getPluginManager().registerEvents(new PickupListener(mainConfig, ruleRegistry), this);
        getServer().getPluginManager().registerEvents(new InventoryClickListener(mainConfig, ruleRegistry), this);
    }

    private void initConfig() {
        this.mainConfig = ConfigManager.create(MainConfig.class, it -> {
            it.configure(opt -> {
                opt.configurer(new YamlBukkitConfigurer(), new SerdesOkaeriBukkit());
                opt.bindFile(new File(this.getDataFolder(), "config.yml"));
                opt.removeOrphans(true);
                opt.resolvePlaceholders();
            });
            it.saveDefaults();
            it.load(false);
        });

        this.ruleRegistry = new ItemRuleRegistry();
        ruleRegistry.load(mainConfig);
    }
}
