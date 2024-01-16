package org.CatAndDomi.api;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.StringReader;
import java.util.Base64;

public class ItemStackSerializer {
    public static String serialize(ItemStack item) {
        YamlConfiguration data = new YamlConfiguration();
        data.set("item", item);
        String s = data.saveToString();
        return Base64.getEncoder().encodeToString(s.getBytes());
    }

    public static ItemStack deserialize(String data) {
        String s = new String(Base64.getDecoder().decode(data));
        return YamlConfiguration.loadConfiguration(new StringReader(s)).getItemStack("item");
    }
}
