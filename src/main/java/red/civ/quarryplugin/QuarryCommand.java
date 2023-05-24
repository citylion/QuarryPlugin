package red.civ.quarryplugin;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;
import org.jetbrains.annotations.NotNull;



public class QuarryCommand implements CommandExecutor{

    public static Quarry quarry;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        Player player = (Player) sender;
        quarry = new Quarry(player.getLocation(),Integer.parseInt(args[0]),Integer.parseInt(args[1]));

        BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
        //scheduler.scheduleSyncRepeatingTask(Bukkit.getPluginManager().getPlugin("QuarryPlugin"), new QuarryTask(), 0l, 5L);


        return true;
    }



}
