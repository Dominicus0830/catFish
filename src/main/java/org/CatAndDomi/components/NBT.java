package org.CatAndDomi.components;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagString;
import org.CatAndDomi.api.ItemStackSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_20_R3.inventory.CraftItemStack;
import org.bukkit.entity.EntityType;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class NBT {

    public static NBTTagCompound getTagCompound(net.minecraft.world.item.ItemStack item) {
        return item.t() ? item.v() : new NBTTagCompound();
    }

    /**
     * @param objitem require ItemStack
     * @param key     require String
     * @param value   will be cast as String
     * @return ItemStack
     *
     */
    
    public static ItemStack setObjectTag(ItemStack objitem, String key, Object value) {
        final net.minecraft.world.item.ItemStack item = CraftItemStack.asNMSCopy(objitem);
        final NBTTagCompound ntc = getTagCompound(item);
        ntc.a(key, NBTTagString.a(value.toString()));
        item.c(ntc);
        return CraftItemStack.asBukkitCopy(item);
    }

    /**
     * @param objitem require ItemStack
     * @param key     require String
     * @return ItemStack
     */
    
    public static ItemStack removeTag(ItemStack objitem, String key) {
        net.minecraft.world.item.ItemStack item = CraftItemStack.asNMSCopy(objitem);
        NBTTagCompound ntc = getTagCompound(item);
        item.c(ntc);
        item.c(key);
        return CraftItemStack.asBukkitCopy(item);
    }

   
    public static ItemStack removeAllTags(ItemStack objitem) {
        net.minecraft.world.item.ItemStack item = CraftItemStack.asNMSCopy(objitem);
        NBTTagCompound ntc = getTagCompound(item);
        for (String key : ntc.e()) {
            item.c(key);
        }
        item.c(ntc);
        return CraftItemStack.asBukkitCopy(item);
    }

    /**
     * @param objitem ItemStack
     * @param key     String
     * @return String
     */
  
    public static String getStringTag(ItemStack objitem, String key) {
        net.minecraft.world.item.ItemStack item = CraftItemStack.asNMSCopy(objitem);
        NBTTagCompound ntc = getTagCompound(item);
        return ntc.l(key).replace('"', ' ').trim();
    }

    /**
     * @param objitem ItemStack
     * @param key     String
     * @return byte
     */
    
    public static byte getByteTag(ItemStack objitem, String key) {
        net.minecraft.world.item.ItemStack item = CraftItemStack.asNMSCopy(objitem);
        NBTTagCompound ntc = getTagCompound(item);
        return ntc.f(key);
    }

    /**
     * @param objitem ItemStack
     * @param key     String
     * @return short
     */
    
    public static short getShortTag(ItemStack objitem, String key) {
        net.minecraft.world.item.ItemStack item = CraftItemStack.asNMSCopy(objitem);
        NBTTagCompound ntc = getTagCompound(item);
        return ntc.f(key);
    }

    /**
     * @param objitem ItemStack
     * @param key     String
     * @return int
     */
    
    public static int getIntegerTag(ItemStack objitem, String key) {
        net.minecraft.world.item.ItemStack item = CraftItemStack.asNMSCopy(objitem);
        NBTTagCompound ntc = getTagCompound(item);
        return ntc.h(key);
    }

    /**
     * @param objitem ItemStack
     * @param key     String
     * @return float
     */
    
    public static float getFloatTag(ItemStack objitem, String key) {
        net.minecraft.world.item.ItemStack item = CraftItemStack.asNMSCopy(objitem);
        NBTTagCompound ntc = getTagCompound(item);
        return ntc.j(key);
    }

    /**
     * @param objitem ItemStack
     * @param key     String
     * @return double
     */
    
    public static double getDoubleTag(ItemStack objitem, String key) {
        net.minecraft.world.item.ItemStack item = CraftItemStack.asNMSCopy(objitem);
        NBTTagCompound ntc = getTagCompound(item);
        return ntc.k(key);
    }

    /**
     * @param objitem ItemStack
     * @param key     String
     * @return boolean
     */
    public static boolean getBooleanTag(ItemStack objitem, String key) {
        net.minecraft.world.item.ItemStack item = CraftItemStack.asNMSCopy(objitem);
        NBTTagCompound ntc = getTagCompound(item);
        return ntc.q(key);
    }

    /**
     * @param objitem ItemStack
     * @param key     String
     * @return long
     */
    
    public static long getLongTag(ItemStack objitem, String key) {
        net.minecraft.world.item.ItemStack item = CraftItemStack.asNMSCopy(objitem);
        NBTTagCompound ntc = getTagCompound(item);
        return ntc.i(key);
    }

    /**
     * @param objitem ItemStack
     * @param key     String
     * @return byte[]
     */
    
    public static byte[] getByteArrayTag(ItemStack objitem, String key) {
        net.minecraft.world.item.ItemStack item = CraftItemStack.asNMSCopy(objitem);
        NBTTagCompound ntc = getTagCompound(item);
        return ntc.m(key);
    }

    /**
     * @param objitem ItemStack
     * @param key     String
     * @return int[]
     */
    
    public static int[] getIntArrayTag(ItemStack objitem, String key) {
        net.minecraft.world.item.ItemStack item = CraftItemStack.asNMSCopy(objitem);
        NBTTagCompound ntc = getTagCompound(item);
        return ntc.n(key);
    }

    /**
     * @param objitem ItemStack
     * @param key     String
     * @return NBTTagCompound
     */
    
    public static NBTTagCompound getCompoundTag(ItemStack objitem, String key) {
        net.minecraft.world.item.ItemStack item = CraftItemStack.asNMSCopy(objitem);
        NBTTagCompound ntc = getTagCompound(item);
        return ntc.p(key);
    }

    /**
     * @param objitem ItemStack
     * @param key     String
     * @return Material
     */
    
    public static Material getMaterialTag(ItemStack objitem, String key) {
        net.minecraft.world.item.ItemStack item = CraftItemStack.asNMSCopy(objitem);
        NBTTagCompound ntc = getTagCompound(item);
        return Material.getMaterial(ntc.l(key));
    }

    /**
     * @param objitem ItemStack
     * @param key     String
     * @return InventoryType
     */
    
    public static InventoryType getInventoryTypeTag(ItemStack objitem, String key) {
        net.minecraft.world.item.ItemStack item = CraftItemStack.asNMSCopy(objitem);
        NBTTagCompound ntc = getTagCompound(item);
        return InventoryType.valueOf(ntc.l(key));
    }

    /**
     * @param objitem ItemStack
     * @param key     String
     * @return NBTTagList
     */
    
    public static EntityType getEntityTypeTag(ItemStack objitem, String key) {
        net.minecraft.world.item.ItemStack item = CraftItemStack.asNMSCopy(objitem);
        NBTTagCompound ntc = getTagCompound(item);
        return EntityType.valueOf(ntc.l(key));
    }

    /**
     * @param objitem ItemStack
     * @param key     String
     * @return boolean
     */
    public static boolean hasTagKey(ItemStack objitem, String key) {
        net.minecraft.world.item.ItemStack item = CraftItemStack.asNMSCopy(objitem);
        NBTTagCompound ntc = getTagCompound(item);
        return ntc.e(key);
    }

    
    public static Map<String, String> getAllStringTag(ItemStack objitem) {
        net.minecraft.world.item.ItemStack item = CraftItemStack.asNMSCopy(objitem);
        NBTTagCompound ntc = getTagCompound(item);
        if (ntc.e().size() == 0) {
            return null;
        }
        Map<String, String> tags = new HashMap<>();
        for (String key : ntc.e()) {
            tags.put(key, ntc.c(key).toString());
        }
        return tags;
    }

    /**
     * @param objitem ItemStack
     * @param key     String
     * @param value   String
     * @return ItemStack
     */
    
    public static ItemStack setStringTag(ItemStack objitem, String key, String value) {
        net.minecraft.world.item.ItemStack item = CraftItemStack.asNMSCopy(objitem);
        NBTTagCompound ntc = getTagCompound(item);
        ntc.a(key, value);
        item.c(ntc);
        return CraftItemStack.asBukkitCopy(item);
    }

    /**
     * @param objitem ItemStack
     * @param key     String
     * @param value   byte
     * @return ItemStack
     */
    
    public static ItemStack setByteTag(ItemStack objitem, String key, byte value) {
        net.minecraft.world.item.ItemStack item = CraftItemStack.asNMSCopy(objitem);
        NBTTagCompound ntc = getTagCompound(item);
        ntc.a(key, value);
        item.c(ntc);
        return CraftItemStack.asBukkitCopy(item);
    }

    /**
     * @param objitem ItemStack
     * @param key     String
     * @param value   short
     * @return ItemStack
     */
    
    public static ItemStack setShortTag(ItemStack objitem, String key, short value) {
        net.minecraft.world.item.ItemStack item = CraftItemStack.asNMSCopy(objitem);
        NBTTagCompound ntc = getTagCompound(item);
        ntc.a(key, value);
        item.c(ntc);
        return CraftItemStack.asBukkitCopy(item);
    }

    /**
     * @param objitem ItemStack
     * @param key     String
     * @param value   int
     * @return ItemStack
     */
    
    public static ItemStack setIntTag(ItemStack objitem, String key, int value) {
        net.minecraft.world.item.ItemStack item = CraftItemStack.asNMSCopy(objitem);
        NBTTagCompound ntc = getTagCompound(item);
        ntc.a(key, value);
        item.c(ntc);
        return CraftItemStack.asBukkitCopy(item);
    }

    /**
     * @param objitem ItemStack
     * @param key     String
     * @param value   long
     * @return ItemStack
     */
    
    public static ItemStack setLongTag(ItemStack objitem, String key, long value) {
        net.minecraft.world.item.ItemStack item = CraftItemStack.asNMSCopy(objitem);
        NBTTagCompound ntc = getTagCompound(item);
        ntc.a(key, value);
        item.c(ntc);
        return CraftItemStack.asBukkitCopy(item);
    }

    /**
     * @param objitem ItemStack
     * @param key     String
     * @param value   float
     * @return ItemStack
     */
    
    public static ItemStack setFloatTag(ItemStack objitem, String key, float value) {
        net.minecraft.world.item.ItemStack item = CraftItemStack.asNMSCopy(objitem);
        NBTTagCompound ntc = getTagCompound(item);
        ntc.a(key, value);
        item.c(ntc);
        return CraftItemStack.asBukkitCopy(item);
    }

    /**
     * @param objitem ItemStack
     * @param key     String
     * @param value   double
     * @return ItemStack
     */
    
    public static ItemStack setDoubleTag(ItemStack objitem, String key, double value) {
        net.minecraft.world.item.ItemStack item = CraftItemStack.asNMSCopy(objitem);
        NBTTagCompound ntc = getTagCompound(item);
        ntc.a(key, value);
        item.c(ntc);
        return CraftItemStack.asBukkitCopy(item);
    }

    /**
     * @param objitem ItemStack
     * @param key     String
     * @param value   byte[]
     * @return ItemStack
     */
    
    public static ItemStack setByteArrayTag(ItemStack objitem, String key, byte[] value) {
        net.minecraft.world.item.ItemStack item = CraftItemStack.asNMSCopy(objitem);
        NBTTagCompound ntc = getTagCompound(item);
        ntc.a(key, value);
        item.c(ntc);
        return CraftItemStack.asBukkitCopy(item);
    }

    /**
     * @param objitem ItemStack
     * @param key     String
     * @param value   int[]
     * @return ItemStack
     */
    
    public static ItemStack setIntArrayTag(ItemStack objitem, String key, int[] value) {
        net.minecraft.world.item.ItemStack item = CraftItemStack.asNMSCopy(objitem);
        NBTTagCompound ntc = getTagCompound(item);
        ntc.a(key, value);
        item.c(ntc);
        return CraftItemStack.asBukkitCopy(item);
    }

    /**
     * @param objitem ItemStack
     * @param key     String
     * @param value   Material
     * @return ItemStack
     */
    
    public static ItemStack setMaterialTag(ItemStack objitem, String key, Material value) {
        net.minecraft.world.item.ItemStack item = CraftItemStack.asNMSCopy(objitem);
        NBTTagCompound ntc = getTagCompound(item);
        ntc.a(key, value.name());
        item.c(ntc);
        return CraftItemStack.asBukkitCopy(item);
    }

    // ItemStackSerializer

    // Single ItemStack

    /**
     * @param objitem ItemStack
     * @param key     String
     * @param value   ItemStack
     * @return ItemStack
     */
    public static ItemStack setItemStackTag(ItemStack objitem, String key, ItemStack value) {
        String sitem = ItemStackSerializer.serialize(value);
        String[] sitems = sitem.split("(?<=\\G.{288})");

        for (int i = 0; i < sitems.length; i++) {
            objitem = NBT.setStringTag(objitem, key + i, sitems[i]);
        }
        objitem = NBT.setIntTag(objitem, key + "_size", sitems.length);
        return objitem;
    }

    /**
     * @param objitem ItemStack
     * @param key     String
     * @return ItemStack
     */
    
    public static ItemStack getItemStackTag(ItemStack objitem, String key) {
        int length = NBT.getIntegerTag(objitem, key + "_size");
        String s = "";
        for (int i = 0; i < length; i++) {
            s += NBT.getStringTag(objitem, key + i);
        }
        return ItemStackSerializer.deserialize(s);
    }

    // Inventory

    /**
     * @param objitem ItemStack
     * @param inv Inventory
     * @param key String
     * @return ItemStack
     */
    public static ItemStack setInventoryTag(ItemStack objitem, Inventory inv, String key) {
        for (int i = 0; i < inv.getSize(); i++) {
            objitem = NBT.setItemStackTag(objitem, "inv_" + key + "_" + i+"_item", inv.getItem(i));
        }
        objitem = NBT.setIntTag(objitem, "inv_" + key + "_size", inv.getSize());
        return objitem;
    }

    /**
     * @param objitem ItemStack
     * @param key String
     * @return Inventory
     */
    
    public static Inventory getInventoryTag(ItemStack objitem, String key) {
        Inventory inv = Bukkit.createInventory(null, NBT.getIntegerTag(objitem, "inv_" + key + "_size"));
        for (int i = 0; i < inv.getSize(); i++) {
            inv.setItem(i, NBT.getItemStackTag(objitem, "inv_" + key + "_" + i+"_item"));
        }
        return inv;
    }
}
