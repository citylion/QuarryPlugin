package red.civ.quarryplugin;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import javax.xml.crypto.Data;

public final class QuarryPlugin extends JavaPlugin {

    public QuarryPlugin quarryPlugin = this;


    @Override
    public void onEnable() {
        // Plugin startup logic
        this.getServer().getPluginManager().registerEvents(new QuarryBlockListener(), this);
        //DataSaver.createFiles();

        this.getCommand("quarry").setExecutor(new QuarryCommand());
        Recipe.init();

        BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask(Bukkit.getPluginManager().getPlugin("QuarryPlugin"), new QuarryTask(), 0l, 2L);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic


    }

}
