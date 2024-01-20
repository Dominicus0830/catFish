package org.CatAndDomi.components.command;

import org.CatAndDomi.components.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Method;
import java.util.*;

public class CommandComponent extends Component implements CommandExecutor, TabCompleter {

    Map<String, CommandData> commandDataMap = new HashMap<>();

    public CommandComponent(JavaPlugin plugin) {
        super(plugin);
    }

    public void addCommand(Method method, ArgsTypes[] classes, String helpmessage, String... strings) {
        if(strings.length<1) {
            return;
        }
        if(strings.length==1) {
            CommandData data = new CommandData();
            data.setHelpmessage(helpmessage);
            data.setClasses(classes);
            data.setMethod(method);
            commandDataMap.put(strings[0], data);
        }else {
            if(!isCommand(strings[0])) {
                commandDataMap.put(strings[0], new CommandData());
            }
            getCommand(strings[0]).addCommand(method, classes, helpmessage, Arrays.copyOfRange(strings, 1, strings.length));
        }
    }

    @Override
    public void load() {
        super.load();
        for(Map.Entry<String, CommandData> entry : commandDataMap.entrySet()) {
            Bukkit.getPluginCommand(entry.getKey()).setExecutor(this);
            Bukkit.getPluginCommand(entry.getKey()).setTabCompleter(this);
        }
    }

    public boolean isCommand(String string) {
        return commandDataMap.get(string)!=null;
    }

    public CommandData getCommand(String string) {
        return commandDataMap.get(string);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        getCommand(command.getName()).SendCommand(commandSender, strings);
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        ArrayList<String> list = new ArrayList<>();
        int a = strings.length;
        if(a<1) {
            return list;
        }
        if(a==1&&!commandDataMap.isEmpty()) {
            for(Map.Entry<String, CommandData> entry : commandDataMap.entrySet()) {
                list.add(entry.getKey());
            }
        }else if(isCommand(strings[0])) {
            getCommand(strings[0]).tabComplete(list, Arrays.copyOfRange(strings, 1, a));
        }
        return list;
    }
}
