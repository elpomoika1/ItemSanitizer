package me.elpomoika.itemSanitizer.util;

import lombok.experimental.UtilityClass;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

@UtilityClass
public final class ConfigUtil {
    public ConfigurationSection getSection(String name, FileConfiguration config, boolean canOmit) {
        final var section = config.getConfigurationSection(name);
        if (section == null) {
            if (canOmit) return null;
            throw new IllegalStateException("Section " + name + " is missed");
        }
        return section;
    }

    public ConfigurationSection getSection(String name, ConfigurationSection inputSection, boolean canOmit) {
        final var section = inputSection.getConfigurationSection(name);
        if (section == null) {
            if (canOmit) return null;
            throw new IllegalStateException("Section " + name + " is missed in " + inputSection.getName());
        }
        return section;
    }
}
