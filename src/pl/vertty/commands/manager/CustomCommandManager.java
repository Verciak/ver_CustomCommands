package pl.vertty.commands.manager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import pl.vertty.commands.logs.Logger;
import pl.vertty.commands.util.ReflectionUtil;



public abstract class CustomCommandManager implements CommandExecutor, TabExecutor
{
    private static CommandMap cmap;
    private final String command;
    private final String description;
    private final List<String> alias;
    private final String usage;
    private final String permMessage;

    
    public CustomCommandManager(final String command) {
        this(command, new ArrayList<String>());
    }
    
    public CustomCommandManager(final String command, final String[] aliases) {
        this(command, new ArrayList<String>(Arrays.asList(aliases)));
    }
    
    private CustomCommandManager(final String command, final List<String> aliases) {
        this.description = null;
        this.usage = null;
        this.permMessage = null;
        this.command = command.toLowerCase();
        this.alias = aliases;
        this.register();
    }
    
	private void register() {
        final ReflectCommand cmd = new ReflectCommand(this.command);
        if (this.alias != null) {
            cmd.setAliases((List<String>)this.alias);
        }
        if (this.description != null) {
            cmd.setDescription(this.description);
        }
        if (this.usage != null) {
            cmd.setUsage(this.usage);
        }
        if (this.permMessage != null) {
            cmd.setPermissionMessage(this.permMessage);
        }
        this.getCommandMap().register("", (Command)cmd);
        cmd.setExecutor(this);
    }
    
    private final CommandMap getCommandMap() {
        if (CustomCommandManager.cmap == null) {
            try {
                return CustomCommandManager.cmap = (CommandMap)ReflectionUtil.getValue(Bukkit.getServer(), "commandMap");
            }
            catch (Exception e) {
                e.printStackTrace();
                return this.getCommandMap();
            }
        }
        if (CustomCommandManager.cmap != null) {
            return CustomCommandManager.cmap;
        }
        return this.getCommandMap();
    }
    
    public abstract boolean onCommand(final CommandSender p0, final Command p1, final String p2, final String[] p3);
    
    public List<String> onTabComplete(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        return null;
    }
    
    private final class ReflectCommand extends Command
    {
        private CustomCommandManager exe;
        
        ReflectCommand(final String command) {
            super(command);
            this.exe = null;
        }
        
        public void setExecutor(final CustomCommandManager exe) {
            this.exe = exe;
        }
        
        public boolean execute(final CommandSender sender, final String commandLabel, final String[] args) {
            try {
                if (this.exe != null) {
                    this.exe.onCommand(sender, this, commandLabel, args);
                }
            }
            catch (Exception e) {
                Logger.error(e);
            }
            return true;
        }
        
        public List<String> tabComplete(final CommandSender sender, final String alais, final String[] args) {
            if (this.exe != null) {
                return this.exe.onTabComplete(sender, this, alais, args);
            }
            return null;
        }
    }
}

