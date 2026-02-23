package me.elpomoika.itemSanitizer.util;

import lombok.experimental.UtilityClass;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;

@UtilityClass
public final class ComponentTextUtil {
    private static final PlainTextComponentSerializer PLAIN = PlainTextComponentSerializer.plainText();

    public String toPlain(Component component) {
        return component == null ? null : PLAIN.serialize(component);
    }
}
