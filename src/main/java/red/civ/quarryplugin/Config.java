package red.civ.quarryplugin;

import org.bukkit.Bukkit;
import org.bukkit.configuration.Configuration;
import org.bukkit.plugin.Plugin;

public class Config {

    public static int maxlook;//max distance to look for redstone torches
    public static boolean defaultrecipe;//if default recipe should be exposed

    public static int tickspeed;//how often to run quarry task in ticks

    public static boolean consumefuel;//whether or not to consume fuel to power the quarry



    public static boolean allowbstats;

    public static void initialize(){

        Plugin plugin = Bukkit.getPluginManager().getPlugin("QuarryPlugin");
        plugin.reloadConfig();
        Configuration configuration = plugin.getConfig();
        configuration.addDefault("maxlook",50);
        configuration.addDefault("defaultrecipe",true);
        configuration.addDefault("tickspeed",5);
        configuration.addDefault("consumefuel",true);


        configuration.getMapList("fuels");

        plugin.saveDefaultConfig();

        maxlook = configuration.getInt("maxlook");
        defaultrecipe = configuration.getBoolean("defaultrecipe");
        tickspeed  = configuration.getInt("tickspeed");
        consumefuel = configuration.getBoolean("consumefuel");

        Logger.Info("maxlook is " + maxlook);
        Logger.Info("defaultrecipe " + defaultrecipe);
        Logger.Info("tickspeed " + tickspeed);
        Logger.Info("consumefuel " + consumefuel);

    }

    public static void reload(){

        Plugin plugin = Bukkit.getPluginManager().getPlugin("QuarryPlugin");
        plugin.reloadConfig();
        Configuration configuration = plugin.getConfig();


        Logger.Info("Reloading Config");
        maxlook = configuration.getInt("maxlook");
        defaultrecipe = configuration.getBoolean("defaultrecipe");
        tickspeed  = configuration.getInt("tickspeed");
        consumefuel = configuration.getBoolean("consumefuel");
        Logger.Info("maxlook is " + maxlook);
        Logger.Info("defaultrecipe " + defaultrecipe);
        Logger.Info("tickspeed " + tickspeed);
        Logger.Info("consumefuel " + consumefuel);

    }


}
