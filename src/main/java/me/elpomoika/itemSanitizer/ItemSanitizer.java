package me.elpomoika.itemSanitizer;

import co.aikar.commands.PaperCommandManager;
import eu.okaeri.configs.ConfigManager;
import eu.okaeri.configs.yaml.bukkit.YamlBukkitConfigurer;
import eu.okaeri.configs.yaml.bukkit.serdes.SerdesBukkit;
import eu.okaeri.configs.yaml.bukkit.serdes.itemstack.ItemStackFailsafe;
import eu.okaeri.configs.yaml.bukkit.serdes.serializer.ItemStackSerializer;
import me.elpomoika.itemSanitizer.command.SanitizerCommand;
import me.elpomoika.itemSanitizer.config.MainConfig;
import me.elpomoika.itemSanitizer.inventory.InventoryRuleProcessor;
import me.elpomoika.itemSanitizer.inventory.ItemRuleApplier;
import me.elpomoika.itemSanitizer.inventory.ItemRuleMatcher;
import me.elpomoika.itemSanitizer.listener.InventoryClickListener;
import me.elpomoika.itemSanitizer.listener.PickupListener;
import me.elpomoika.itemSanitizer.listener.PlayerJoinListener;
import me.elpomoika.itemSanitizer.registry.ItemRuleRegistry;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class ItemSanitizer extends JavaPlugin {
    private MainConfig mainConfig;
    private ItemRuleRegistry ruleRegistry;

    private PaperCommandManager commandManager;

    @Override
    public void onEnable() {
        initConfig();
        initCommands();
        registerBukkitListeners();
    }

    private void registerBukkitListeners() {
        InventoryRuleProcessor ruleProcessor = createRuleProcessor();

        getServer().getPluginManager().registerEvents(new PlayerJoinListener(mainConfig, ruleRegistry, ruleProcessor), this);
        getServer().getPluginManager().registerEvents(new PickupListener(mainConfig, ruleRegistry, ruleProcessor), this);
        getServer().getPluginManager().registerEvents(new InventoryClickListener(mainConfig, ruleRegistry, ruleProcessor), this);
    }

    private void initCommands() {
        this.commandManager = new PaperCommandManager(this);
        commandManager.registerCommand(new SanitizerCommand(mainConfig, ruleRegistry));
    }

    private void initConfig() {
        this.mainConfig = ConfigManager.create(MainConfig.class, it -> {
            it.configure(opt -> {
                opt.configurer(new YamlBukkitConfigurer(), new SerdesBukkit());
                opt.serdes(registry -> registry.registerExclusive(ItemStack.class, new ItemStackSerializer(ItemStackFailsafe.BUKKIT)));
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

    private InventoryRuleProcessor createRuleProcessor() {
        return new InventoryRuleProcessor(new ItemRuleMatcher(), new ItemRuleApplier());
    }
}
