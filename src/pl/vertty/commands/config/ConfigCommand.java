package pl.vertty.commands.config;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import pl.vertty.commands.manager.CustomCommandManager;
import pl.vertty.commands.util.ChatUtil;

public class ConfigCommand extends CustomCommandManager
{
    private List<String> msg;
    
    public ConfigCommand(final String command, final List<String> list) {
        super(command);
        this.msg = list;
    }
    
    @Override
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        ChatUtil.sendMessage((Player)sender, this.msg);
        return true;
    }
}
