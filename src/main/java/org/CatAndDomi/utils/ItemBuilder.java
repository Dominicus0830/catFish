package org.CatAndDomi.utils;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ItemBuilder {
    private final ItemStack item;
    private final ItemMeta itemMeta;
    private final PersistentDataContainer persistence;
    private Material material;
    private int amount;
    private List<String> lore = new ArrayList<>();

    public ItemBuilder(String name, Material material, int amount) {
        item = new ItemStack(material, amount);
        this.material = material;
        this.amount = amount;
        itemMeta = item.getItemMeta();
        persistence = itemMeta.getPersistentDataContainer();
        setName(itemMeta.getDisplayName());
    }

    public ItemBuilder(ItemStack item) {
        this.item = item;
        this.amount = this.item.getAmount();
        material = this.item.getType();
        itemMeta = this.item.getItemMeta();
        persistence = itemMeta.getPersistentDataContainer();
        setName(this.item.getItemMeta().getDisplayName());
    }

    public ItemStack getItemStack() {
        itemMeta.setLore(lore);
        item.setItemMeta(itemMeta);
        item.setAmount(amount);
        return item;
    }

    public ItemBuilder setName(String name) {
        itemMeta.setDisplayName(name);
        return this;
    }

    public ItemBuilder setMaterial(Material material) {
        this.material = material;
        return this;
    }

    public ItemBuilder setAmount(int amount) {
        this.amount = amount;
        return this;
    }

    public ItemBuilder addAttribute(Attribute attributeKey, String key, double value, EquipmentSlot slot, AttributeModifier.Operation operation) {
        double sharp = itemMeta.getEnchantLevel(Enchantment.DAMAGE_ALL) * 0.8;
        AttributeModifier attribute = new AttributeModifier(UUID.randomUUID(), key, value + sharp, operation, slot);
        itemMeta.addAttributeModifier(attributeKey, attribute);
        return this;
    }

    public ItemBuilder removeAttribute(Attribute attribute) {
        itemMeta.removeAttributeModifier(attribute);
        return this;
    }

    public ItemBuilder resetAttribute() {
        for (Attribute attribute : Attribute.values()) {
            itemMeta.removeAttributeModifier(attribute);
        }
        return this;
    }

    public <T> ItemBuilder setData(String key, PersistentDataType<T, T> type, T value) {
        NamespacedKey namespacedKey = NamespacedKey.fromString(key);
        if (namespacedKey != null) {
            persistence.set(namespacedKey, type, value);
        }
        return this;
    }

    public <T> Data<T> getDataOrDefault(String key, PersistentDataType<T, T> type, T defaultValue) {
        return new Data<>(this, persistence.getOrDefault(NamespacedKey.fromString(key), type, defaultValue));
    }

    public ItemBuilder setLore(List<String> lore) {
        this.lore = lore;
        return this;
    }

    public ItemBuilder addLore(String line) {
        lore.add(line);
        return this;
    }

    public ItemBuilder resetLore() {
        lore.clear();
        return this;
    }

    public void removeLore(int index) {
        try {
            lore.remove(index);
        } catch (IndexOutOfBoundsException e) {
            Bukkit.getLogger().warning("item index error");
        }
    }

    public ItemBuilder addEnchant(Enchantment enchantment, int value) {
        itemMeta.addEnchant(enchantment, value, true);
        return this;
    }

    public ItemBuilder removeEnchant(Enchantment enchantment) {
        itemMeta.removeEnchant(enchantment);
        return this;
    }

    public ItemBuilder resetEnchant() {
        itemMeta.getEnchants().forEach((key, value) -> itemMeta.removeEnchant(key));
        return this;
    }

    public ItemBuilder let(ItemBuilder item) {
        return this;
    }

    public static class Data<T> {
        private final ItemBuilder itemBuilder;
        private final T value;

        public Data(ItemBuilder itemBuilder, T value) {
            this.itemBuilder = itemBuilder;
            this.value = value;
        }

        public ItemBuilder let(OnDataCallback<T> callback) {
            callback.run(itemBuilder, value);
            return itemBuilder;
        }

        public ItemBuilder addEnchant(Enchantment enchantment) {
            if (value instanceof Integer) {
                itemBuilder.addEnchant(enchantment, (Integer) value);
            }
            return itemBuilder;
        }

        public ItemBuilder addAttribute(Attribute attributeKey, String key, EquipmentSlot slot, AttributeModifier.Operation operation) {
            if (value instanceof Number) {
                double doubleValue = ((Number) value).doubleValue();
                AttributeModifier attribute = new AttributeModifier(UUID.randomUUID(), key, doubleValue, operation, slot);
                itemBuilder.itemMeta.addAttributeModifier(attributeKey, attribute);
            }
            return itemBuilder;
        }
    }

    public interface OnDataCallback<T> {
        void run(ItemBuilder itemBuilder, T value);
    }
}

class ItemStackUtils {
    public static <T> T getDataOrDefault(ItemStack itemStack, String key, PersistentDataType<T, T> type, T defaultValue) {
        NamespacedKey namespacedKey = NamespacedKey.fromString(key);
        if (namespacedKey != null) {
            return itemStack.getItemMeta().getPersistentDataContainer().getOrDefault(namespacedKey, type, defaultValue);
        } else {
            return null;
        }
    }

    public static ItemBuilder builder(ItemStack itemStack) {
        return new ItemBuilder(itemStack);
    }
}
