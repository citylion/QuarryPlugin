package red.civ.quarryplugin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class ConfigCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if(args.length==1 && args[0].equals("reload")){

            if(sender.isOp()){
                Config.reload();
                sender.sendMessage("Config reloaded. You will need to restart for certain parameters like defaultrecipe and tickspeed to take effect.");
            }
            else{
                sender.sendMessage("You must be an operator to reload this config");
            }
            return true;

        }

        sender.sendMessage("Maxlook is " + Config.maxlook);
        sender.sendMessage("DefaultRecipe enabled is " + Config.defaultrecipe);
        sender.sendMessage("Quarry tick speed is " + Config.tickspeed);
        return true;
    }
}
