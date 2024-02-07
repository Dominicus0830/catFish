package org.CatAndDomi.components;

import org.CatAndDomi.components.POS.POSComponet;
import org.CatAndDomi.components.command.CommandComponent;
import org.CatAndDomi.components.deeplearner.DeepLearnerComponent;
import org.CatAndDomi.components.math.MathTriangleComponent;
import org.CatAndDomi.components.message.MessageComponent;
import org.CatAndDomi.components.pageinventory.PageInventoryComponent;
import org.bukkit.plugin.java.JavaPlugin;

public enum ComponentType {

    NONE(Component.class),
    MESSAGE(MessageComponent.class),
    PAGEINVENTORY(PageInventoryComponent.class),
    MATH_TRIANGLE(MathTriangleComponent.class),
    COMMAND(CommandComponent.class),
    DEEPLEARNER(DeepLearnerComponent.class),
    POS(POSComponet.class)
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
