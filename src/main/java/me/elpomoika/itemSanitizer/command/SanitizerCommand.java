package me.elpomoika.itemSanitizer.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Subcommand;
import lombok.RequiredArgsConstructor;
import me.elpomoika.itemSanitizer.config.ConfigLoader;
import org.bukkit.command.CommandSender;

@CommandAlias("sanitizer")
@CommandPermission("sanitizer.admin")
@RequiredArgsConstructor
public class SanitizerCommand extends BaseCommand {
    private final ConfigLoader config;

    @Subcommand("reload")
    public void onReload(CommandSender sender) {
        config.reload();

        sender.sendMessage("Successfully reload");
    }
}
