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
implementation 'com.github.Dominicus0830:catFish:0.0.0.10'
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

### DataUtils 
##### MainClass
```
public class APITest extends JavaPlugin {
    private static APITest plugin;
    public static DataUtils data;
    public static APITest getInstance() {
        return plugin;
    }

    @Override
    public void onEnable() {
        plugin = this;
        data = new DataUtils(this);
    }
    ...
}
```
##### FunctionClass
```java
public class APIFunction {
    private static final APITest plugin = APITest.getInstance();
    private static final DataUtils data = plugin.data;

    public static void getItem(Player player, Inventory inv) {
        ItemStack stack = inv.getItem(1);
        data.getConfig().set("test", stack);
    }
    
    public static void setItem(Player player, Inventory inv) {
        ItemStack stack = data.getConfig().getItemStack("test");
        inv.setItem(1, stack);
    }

    public static void ifItem(Player player, Inventory inv) {
        if(data.getConfig().getItemStack("test") == null) {
            System.out.println("null");
            return;
        }else {
            System.out.println("not null");
        }
    }
}
```

### InventoryAPI
```java
InventoryAPI inv = new InventoryAPI(null, "Inventory Name", 54, true, plugin);
// InventoryHolder, Title Size, UsePage, Plugin
ItemStack COD = new ItemStack(Material.COD);

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

inv.setPageTools(new ItemStack[]{COD, COD, prev, COD, COD, COD, next, COD, COD});
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

#### MessageComponent
```java
public CatFish cf;
    public MessageComponent component;

    public void setUpCatFish() {
        if(Bukkit.getPluginManager().isPluginEnabled("CatFish")) {
            cf = (CatFish) Bukkit.getPluginManager().getPlugin("CatFish");
        }
    }

    public void onEnable() {
        setUpCatFish();
        new CatFishBuilder(this).addComponents(ComponentType.MESSAGE).build();
        component = (MessageComponent) cf.getComponent(this, ComponentType.MESSAGE);
        component.addMessages("접두어", "&f[ &e냥 &f] ");
        component.addMessages("얌얌얌", Arrays.asList("고", "냥", "이", "팔딱팔딱"));
        component.load();
        System.out.println(component.getString("접두어"));
        System.out.println(component.getList("얌얌얌"));
    }
}
```

#### PageInventoryComponent
```java
public CatFish cf;
public PageInventoryComponent component;

public void setUpCatFish() {
    if(Bukkit.getPluginManager().isPluginEnabled("CatFish")) {
           cf = (CatFish) Bukkit.getPluginManager().getPlugin("CatFish");
    }
}

public void onEnable() {
    setUpCatFish();
    new CatFishBuilder(this).addComponents(ComponentType.PAGEINVENTORY).build();
    component = (PageInventoryComponent) cf.getComponent(this, ComponentType.PAGEINVENTORY);
    component.setPageInventoryClass(CustomInventory.class);
    component.load();
}

@Override
public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if(sender instanceof Player p) {
        if(!component.isInventory(p.getName())) {
            component.createInventory(p.getName(), 54);
            component.getInventory(p.getName()).createpage(0);
        }
        component.getInventory(p.getName()).openInventory(p, 0);
    }
    return super.onCommand(sender, command, label, args);
}
```
CustomInventory.class
```java
public class CustomInventory extends PageInventory {

    public CustomInventory(String name, PageInventoryComponent component, Integer invsize) {
        super(name, component, invsize);
    }

    public CustomInventory(PageInventoryComponent component, YamlConfiguration config) {
        super(component, config);
    }

    @Override
    public String InventoryName() {
        return name+"의 창고";
    }

    @Override
    public void close(InventoryCloseEvent e, InventoryOpener opener) {
        super.close(e, opener);
        for(int a = 0;a<invsize;a++) {
            Inventory inv = e.getInventory();
            if(inv.getItem(a)!=null) {
                setItem(opener.page, a, inv.getItem(a));
            }else {
                setItem(opener.page, a, new ItemStack(Material.AIR));
            }
        }
    }
}
```

#### CommandComponent
```java
public CatFish cf;
public CommandComponent component;
 
public void setUpCatFish() {
    if(Bukkit.getPluginManager().isPluginEnabled("CatFish")) {
        cf = (CatFish) Bukkit.getPluginManager().getPlugin("CatFish");
    }
}

public static void cmd(CommandSender commandSender, String message, String... strings) {
    if(commandSender instanceof Player p) {
        String s = message;
        for(String ss : strings) {
            s+=" " + ss;
        }
        Bukkit.broadcastMessage(p.getName() + ": " + s);
    }
}

public static void cmd1(CommandSender commandSender, String... strings) {
    if(commandSender instanceof Player p) {
        Bukkit.broadcastMessage(p.getName() + ": 뭐");
    }
}

public static void cmd2(CommandSender commandSender, Integer i, String... strings) {
    if(commandSender instanceof Player p) {
        Bukkit.broadcastMessage(p.getName() + ": " + i);
    }
}

public void onEnable() {
    setUpCatFish();
    new CatFishBuilder(this).addComponents(ComponentType.COMMAND).build();
    component = (CommandComponent) cf.getComponent(this, ComponentType.COMMAND);
    try {
        component.addCommand(this.getClass().getMethod("cmd", CommandSender.class, String.class, String[].class)
                , new ArgsTypes[]{ArgsTypes.STRING}, "/확성기 발언 <STRING>", "확성기", "발언");
        component.addCommand(this.getClass().getMethod("cmd1", CommandSender.class, String[].class)
                , new ArgsTypes[]{}, "/확성기 뭐", "확성기", "뭐");
        component.addCommand(this.getClass().getMethod("cmd2", CommandSender.class, Integer.class, String[].class)
                , new ArgsTypes[]{ArgsTypes.INTEGER}, "/확성기 숫자 <INT>", "확성기", "숫자");
    } catch (Exception e) {
        throw new RuntimeException(e);
    }
    component.load();
}
```