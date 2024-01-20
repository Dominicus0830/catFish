package org.CatAndDomi.components.command;

import org.CatAndDomi.components.pageinventory.PageInventoryComponent;
import org.bukkit.command.CommandSender;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CommandData {

    Map<String, CommandData> commandDataMap = new HashMap<>();
    ArgsTypes[] classes;
    Method cmdMethod;
    public String helpmessage;
    final CommandComponent component;

    public CommandData(CommandComponent component) {
        this.component = component;
    }

    public boolean isCommand(String string) {
        return commandDataMap.get(string)!=null;
    }

    public void setClasses(ArgsTypes[] classes) {
        this.classes = classes;
    }

    public void addCommand(Method method, ArgsTypes[] classes, String helpmessage, String... strings) {
        if(strings.length<1) {
            return;
        }
        if(strings.length==1) {
            CommandData data = new CommandData(component);
            data.setHelpmessage(helpmessage);
            data.setClasses(classes);
            data.setMethod(method);
            commandDataMap.put(strings[0], data);
        }else {
            if(!isCommand(strings[0])) {
                commandDataMap.put(strings[0], new CommandData(component));
            }
            getCommand(strings[0]).addCommand(method, classes, helpmessage, Arrays.copyOfRange(strings, 1, strings.length));
        }
    }

    public void setHelpmessage(String string) {
        this.helpmessage = string;
    }

    public void setMethod(Method cmdMethod) {
        this.cmdMethod = cmdMethod;
    }

    public CommandData getCommand(String string) {
        return commandDataMap.get(string);
    }

    public boolean InvokeMethod(CommandSender commandSender, String... object) {
        Object[] objects = new Object[classes.length+2];
        objects[0] = commandSender;
        if(classes!=null) {
            for(int a = 0; a<classes.length; a++) {
                switch(classes[a]) {
                    case INTEGER:
                        try{
                            objects[a+1] = Integer.parseInt(object[a]);
                        }catch(Exception e) {
                            return false;
                        }
                        break;
                    case DOUBLE:
                        try{
                            objects[a+1] = Double.parseDouble(object[a]);
                        }catch(Exception e) {
                            return false;
                        }
                        break;
                    case STRING:
                        try{
                            objects[a+1] = object[a];
                        }catch(Exception e) {
                            return false;
                        }
                        break;
                }
            }
        }
        String[] ss = Arrays.copyOfRange(object, classes.length, object.length);
        objects[objects.length-1] = ss;
        try{
            cmdMethod.invoke(component, objects);
            return true;
        }catch(Exception e) {
            return false;
        }
    }

    public boolean SendCommand(CommandSender commandSender, String... objects) {
        if(objects.length==0) {
            return InvokeMethod(commandSender, objects);
        }
        String obj = objects[0];
        if(isCommand(obj)) {
            return getCommand(obj).SendCommand(commandSender, Arrays.copyOfRange(objects, 1, objects.length));
        }else {
            return InvokeMethod(commandSender, objects);
        }
    }

    public void tabComplete(ArrayList<String> list, String[] strings) {
        int a = strings.length;
        if(a<1) {
            return;
        }
        if(a==1&&!commandDataMap.isEmpty()) {
            for(Map.Entry<String, CommandData> entry : commandDataMap.entrySet()) {
                list.add(entry.getKey());
            }
        }else if(isCommand(strings[0])) {
            getCommand(strings[0]).tabComplete(list, Arrays.copyOfRange(strings, 1, a));
        }else if(classes!=null&&classes.length>=a){
            list.add(classes[a-1].argname);
        }
    }

}
