package org.CatAndDomi.components;

import org.CatAndDomi.components.message.MessageComponent;
import org.bukkit.plugin.java.JavaPlugin;

public enum ComponentType {

    NONE(Component.class),
    MESSAGE(MessageComponent.class)
    ;

    public final Class<? extends Component> clz;

    ComponentType(Class<? extends Component> clz) {
        this.clz = clz;
    }

    public Component createComponent(JavaPlugin plugin) {
        try{
            return clz.getDeclaredConstructor(new Class[]{JavaPlugin.class}).newInstance(plugin);
        }catch(Exception e) {
            return null;
        }
    }

}
