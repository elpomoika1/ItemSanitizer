package me.elpomoika.itemSanitizer.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Subcommand;
import co.aikar.commands.annotation.Syntax;
import lombok.RequiredArgsConstructor;
import me.elpomoika.itemSanitizer.config.MainConfig;
import me.elpomoika.itemSanitizer.entity.ItemRule;
import me.elpomoika.itemSanitizer.entity.MatchRule;
import me.elpomoika.itemSanitizer.entity.action.Action;
import me.elpomoika.itemSanitizer.entity.action.ActionType;
import me.elpomoika.itemSanitizer.registry.ItemRuleRegistry;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

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

    @Subcommand("add")
    @Syntax("<itemId>")
    public void onAdd(Player sender, String itemId) {
        ItemStack itemInHand = sender.getInventory().getItemInMainHand();
        if (itemInHand.getType().isAir()) {
            sender.sendMessage("Hold an item in your hand");
            return;
        }

        if (config.getItems().containsKey(itemId)) {
            sender.sendMessage("Item with this id already exists");
        }

        config.getItems().put(itemId,
                new ItemRule(
                    null,
                    itemInHand,
                    new MatchRule(),
                    new Action(ActionType.REPLACE, Material.STONE, "")
                ));

        config.save();
        registry.load(config);

        sender.sendMessage("Success add item in detected items");
    }
}
