package me.elpomoika.itemSanitizer.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Subcommand;
import lombok.RequiredArgsConstructor;
import me.elpomoika.itemSanitizer.config.MainConfig;
import me.elpomoika.itemSanitizer.registry.ItemRuleRegistry;
import org.bukkit.command.CommandSender;

@CommandAlias("sanitizer|is|itemsanitizer")
@CommandPermission("sanitizer.admin")
@RequiredArgsConstructor
public class SanitizerCommand extends BaseCommand {
    private final MainConfig config;
    private final ItemRuleRegistry registry;

    @Subcommand("reload")
    public void onReload(CommandSender sender) {
        config.load();
        registry.load(config);

        sender.sendMessage("Successfully reload");
    }
}
