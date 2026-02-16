package me.elpomoika.itemSanitizer.config;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import eu.okaeri.configs.annotation.CustomKey;
import eu.okaeri.configs.annotation.Header;
import lombok.Getter;
import lombok.Setter;
import me.elpomoika.itemSanitizer.entity.action.ActionType;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Getter
public class MainConfig extends OkaeriConfig {
    @Comment("Is enable checks")
    private boolean check = true;

    private Map<String, ItemsConfig> items = new HashMap<>();

    public MainConfig() {
        items.put("illegal_helmet", new ItemsConfig());
    }

    @Getter @Setter
    public static class ItemsConfig extends OkaeriConfig {
        @Comment("material of target item")
        private String targetMaterial = "NETHERITE_SWORD";

        private MatchConfig match = new MatchConfig();
        private ActionConfig action = new ActionConfig();

        public ItemsConfig() {
            match.setDisplayName("tsundere");
            match.setLoreContains("dada");
            match.setCustomModalData(123123123);

            action.setReplaceCommand("give %player% diamond 1");
            action.setReplaceMaterial("");
        }

        @Header("It works like AND. Example: (displayName) AND (lore) AND (custom-model-data) if true -> do action")
        @Getter @Setter
        public static class MatchConfig extends OkaeriConfig {
            @CustomKey("displayname")
            private String displayName;
            @CustomKey("lore")
            private String loreContains;
            @CustomKey("custom-model-data")
            private Integer customModalData;
        }

        @Getter @Setter
        public static class ActionConfig implements Serializable {
            private ActionType type = ActionType.REPLACE;
            private String replaceMaterial;
            private String replaceCommand;
        }
    }
}