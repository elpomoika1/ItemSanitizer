package me.elpomoika.itemSanitizer.config;

import lombok.AccessLevel;
import lombok.Getter;
import me.elpomoika.itemSanitizer.config.core.ConfigProvider;
import me.elpomoika.itemSanitizer.entity.ItemRule;
import me.elpomoika.itemSanitizer.entity.MatchRule;
import me.elpomoika.itemSanitizer.entity.action.ActionRule;
import me.elpomoika.itemSanitizer.entity.action.ActionType;
import me.elpomoika.itemSanitizer.util.ConfigUtil;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

@Getter
public class ConfigLoader implements ConfigProvider {
    @Getter(AccessLevel.NONE)
    private final JavaPlugin plugin;
    @Getter(AccessLevel.NONE)
    private FileConfiguration config;

    public ConfigLoader(JavaPlugin plugin) {
        this.plugin = plugin;
        this.config = plugin.getConfig();
    }

    @Override
    public void setup() {
        parseItems();
        parseValues();
    }

    @Override
    public void reload() {
        plugin.reloadConfig();
        this.config = plugin.getConfig();

        setup();
    }

    private void parseValues() {
        this.isCheck = config.getBoolean("check");
    }

    private boolean isCheck;

    private void parseItems() {
        rules.clear();

        ConfigurationSection itemsSec = ConfigUtil.getSection("items", config, true);
        if (itemsSec == null) return;

        for (String key : itemsSec.getKeys(false)) {
            try {
                ConfigurationSection sec = ConfigUtil.getSection(key, itemsSec, false);
                if (sec == null) continue;

                ItemRule rule = parseItemRule(sec);
                rules.put(key, rule);
            } catch (Exception ex) {
                plugin.getLogger().severe("Failed to load rule " + key + ex.getMessage());
            }
        }

        plugin.getLogger().info("Loaded " + rules.size() + " item rules");
    }

    private final Map<String, ItemRule> rules = new HashMap<>();

    private ItemRule parseItemRule(ConfigurationSection sec) {
        String materialName = sec.getString("material");
        if (materialName == null) throw new IllegalStateException("material is missing");

        Material material = Material.matchMaterial(materialName);
        if (material == null) throw new IllegalStateException("invalid material: " + materialName);

        MatchRule match = getMatchRule(ConfigUtil.getSection("match", sec, true));
        ConfigurationSection actionSec = ConfigUtil.getSection("action", sec, false);
        ActionRule action = getActionRule(actionSec);

        return new ItemRule(material, match, action);
    }

    private MatchRule getMatchRule(@Nullable ConfigurationSection sec) {
        if (sec == null) return null;

        String displayName = sec.getString("display-name");
        String loreContains = sec.getString("lore-contains");

        Integer cmd = sec.contains("custom-model-data")
                ? sec.getInt("custom-model-data")
                : null;

        if (displayName == null && loreContains == null && cmd == null)
            return null;

        return new MatchRule(displayName, loreContains, cmd);
    }

    private ActionRule getActionRule(@NotNull ConfigurationSection sec) {
        String typeName = sec.getString("type");
        if (typeName == null) throw new IllegalStateException("action.type is missing");

        ActionType type;
        try {
            type = ActionType.valueOf(typeName.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalStateException("invalid action.type: " + typeName);
        }

        Material replace = null;
        if (type == ActionType.REPLACE) {
            ConfigurationSection repl = ConfigUtil.getSection("replace-with", sec, false);

            String matName = repl.getString("material");
            if (matName == null) throw new IllegalStateException("replace-with.material is missing");

            replace = Material.matchMaterial(matName);
            if (replace == null) throw new IllegalStateException("invalid replace material: " + matName);
        }

        return new ActionRule(type, replace);
    }

}
