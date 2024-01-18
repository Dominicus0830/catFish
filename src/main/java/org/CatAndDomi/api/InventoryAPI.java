package org.CatAndDomi.api;

import org.CatAndDomi.components.NBT;
import org.bukkit.craftbukkit.v1_20_R3.inventory.CraftInventoryCustom;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class InventoryAPI extends CraftInventoryCustom {
    private boolean usePage;
    private boolean usePageTools;
    private int pages = 0;
    private int currentPage = 0;
    private ItemStack[] pageTools = new ItemStack[8];
    private Map<Integer, ItemStack[]> pageItems = new HashMap<>();
    private final String handlerName;
    private final UUID uuid;
    private Object obj;

    public InventoryAPI(InventoryHolder holder, String title, int size, JavaPlugin plugin) {
        super(holder, size, title);
        usePage = false;
        handlerName = plugin.getName();
        uuid = UUID.randomUUID();
    }

    public InventoryAPI(InventoryHolder holder, String title, int size, boolean usePage, JavaPlugin plugin) {
        super(holder, size, title);
        this.usePage = usePage;
        handlerName = plugin.getName();
        usePageTools = true;
        uuid = UUID.randomUUID();
    }

    public boolean isUsePage() {
        return usePage;
    }

    public void setUsePage(boolean usePage) {
        this.usePage = usePage;
        usePageTools = true;
    }


    public boolean isUsePageTools() {
        return usePageTools;
    }

    public void setUsePageTools(boolean usePageTools) {
        this.usePageTools = usePageTools;
    }


    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }


    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }


    public ItemStack[] getPageTools() {
        return pageTools;
    }

    public void setPageTool(int index, ItemStack item) {
        pageTools[index] = item;
    }


    public void setPageTools(ItemStack[] pageTools) {
        this.pageTools = pageTools;
    }

    public boolean setPageItems(Map<Integer, ItemStack[]> pageItems) {
        this.pageItems = pageItems;
        return true;
    }

    public boolean setPageItem(int slot, ItemStack item) {
        if (slot < 0 || slot > 44) return false;
        pageItems.get(currentPage)[slot] = item;
        return true;
    }


    public Map<Integer, ItemStack[]> getPageItems() {
        return pageItems;
    }


    public boolean setPageContent(int page, ItemStack[] items) {
        if (page < 0 || page > pages) return false;
        pageItems.put(page, items);
        return true;
    }

    public void addPageContent(ItemStack[] items) {
        pageItems.put(pages, items);
        pages++;
    }


    public void update() {
        clear();
        if(pageItems.get(currentPage) != null) {
            for (int i = 0; i < pageItems.get(currentPage).length; i++) {
                if (pageItems.get(currentPage)[i] != null) {
                    setItem(i, pageItems.get(currentPage)[i]);
                }
            }
        }
        if (usePageTools) {
            int pt = 0;
            for (int i = getSize() - 9; i < getSize(); i++) {
                if (pageTools[pt] != null) {
                    setItem(i, NBT.setStringTag(pageTools[pt], "pageTools", "true"));
                }
                pt++;
            }
        }
    }


    public boolean nextPage() {
        if (!usePage) return false;
        if (currentPage >= pages) return false;
        currentPage++;
        update();
        return true;
    }

    public boolean prevPage() {
        if (!usePage) return false;
        if (currentPage <= 0) return false;
        currentPage--;
        update();
        return true;
    }

    public boolean turnPage(int page) {
        if (!usePage) return false;
        if (page < 0 || page > pages) return false;
        currentPage = page;
        update();
        return true;
    }



    public String getHandlerName() {
        return handlerName;
    }


    public boolean isValidHandler(JavaPlugin plugin) {
        return plugin.getName().equals(handlerName);
    }

    public UUID getUniqueId() {
        return uuid;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }
}
