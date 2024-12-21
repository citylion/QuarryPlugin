package red.civ.quarryplugin;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class ConfigCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if(args.length==1 && args[0].equals("reload")){

            if(sender.isOp()){
                sender.sendMessage(ChatColor.YELLOW+"Config reloaded. You may need to restart for changes to certain parameters like defaultrecipe to take effect.");
                QuarryPlugin.config.initialize(QuarryPlugin.INSTANCE);
            }
            else{
                sender.sendMessage("You must be an operator to reload this config");
            }
            return true;
        }

        sender.sendMessage("Maxlook is " + Config.maxlook);
        sender.sendMessage("DefaultRecipe enabled is " + Config.defaultrecipe);
        sender.sendMessage("Quarry tick speed is " + Config.tickspeed);
        sender.sendMessage("Quarry should consume fuel is " + Config.consumefuel);
        return true;
    }
}
