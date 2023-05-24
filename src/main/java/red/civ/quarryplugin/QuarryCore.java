package red.civ.quarryplugin;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitScheduler;

public class QuarryCore {


    public static void addQuarry(Location location, int xs, int zs){
        Quarry q = new Quarry(location, xs, zs);

        Logger.Info("SE Quarry origin " + location.toString());
        Logger.Info("xs size " + xs);
        Logger.Info("zs size" + zs );

        BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask(Bukkit.getPluginManager().getPlugin("QuarryPlugin"), new QuarryTask(q), 0l, 2L);
    }

}
