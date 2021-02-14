package dev.feldmann.redstonegang.wire.utils;

import dev.feldmann.redstonegang.wire.Wire;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.metadata.Metadatable;

import java.util.List;

public class MetaUtils {

    public static void set(Metadatable m, String key, Object value) {
        m.setMetadata(key, new FixedMetadataValue(Wire.instance, value));
    }

    public static Object get(Metadatable m, String key) {
        if (m.hasMetadata(key)) {
            List<MetadataValue> metadata = m.getMetadata(key);
            return metadata.get(0).value();
        }
        return null;

    }

    public static void remove(Metadatable m, String key) {
        m.removeMetadata(key, Wire.instance);
    }
}
