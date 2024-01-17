# CatFish
## Develop by babynako & Dominicus0830

## GRADLE

## 리포지토리 - Repository
```gradle
maven { 
    url = 'maven { url 'https://jitpack.io' }'
}
```

## 디펜던시 - Dependencies
```gradle
implementation 'com.github.Dominicus0830:catFish:0.0.0.7'
```

## API
### ConfigUtils
```java
public static YamlConfiguration config;
public static TestPlugin plugin;
public void onEnable() {
    plugin = this;
    config = ConfigUtils.loadDefaultPluginConfig(plugin);
}
```

### ColorUtils
```java
String s = "&aTest String"
s = ColorUtils.applyColor(s);
String s2 = "<#FFFFFF>TestString"
s2 = ColorUtils.applyColor(s2);
```

### InventoryUtils
```java
Player p = Bukkit.getPlayer("PlayerName");
ItemStack item = new ItemStack(Material.COD, 10);
if(InventoryUtils.hasEnoughSpace(p.getInventory().getStorageContents(), item)) {
    p.getInventory().addItem(item);
}else{
    p.sendMessage("인벤토리에 남은 공간이 없습니다!");
}
```

### InventoryAPI
```java
InventoryAPI inv = new InventoryAPI(null, "Inventory Name", 54, true, plugin);
// InventoryHolder, Title Size, UsePage, Plugin
ItemStack pane = new ItemStack(Material.COD);

ItemStack prev = NBT.setStringTag(new ItemStack(Material.DIAMOND), "prev", "true");
ItemMeta im = prev.getItemMeta();
im.setDisplayName("이전 페이지");
prev.setItemMeta(im);
//setting prev item

ItemStack next = NBT.setStringTag(new ItemStack(Material.IRON_INGOT), "next", "true");
im = next.getItemMeta();
im.setDisplayName("다음 페이지");
next.setItemMeta(im);
//setting next item

inv.setPageTools(new ItemStack[]{pane, pane, prev, pane, pane, pane, next, pane, pane});
//must be 9 items
inv.addPageContent(/*ItemStack[]*/);
inv.update();
Player#openInventory(inv);
```
#### Setting Inventory Click Event
```java
@EventHandler
public void onInventoryClick(InventoryClickEvent e) {
    if (e.getClickedInventory() instanceof InventoryAPI) {
        InventoryAPI inv = (InventoryAPI) e.getClickedInventory();
        if (NBT.hasTagKey(e.getCurrentItem(), "prev")) {
            e.setCancelled(true);
            inv.prevPage();
            return;
        }
        if (NBT.hasTagKey(e.getCurrentItem(), "next")) {
            e.setCancelled(true);
            inv.nextPage();
            return;
        }
    }
}
```