package red.civ.quarryplugin;

import org.bukkit.plugin.java.JavaPlugin;

import javax.xml.crypto.Data;

public final class QuarryPlugin extends JavaPlugin {


    @Override
    public void onEnable() {
        // Plugin startup logic
        this.getServer().getPluginManager().registerEvents(new QuarryBlockListener(), this);
        //DataSaver.createFiles();

        this.getCommand("quarry").setExecutor(new QuarryCommand());
        Recipe.init();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic


    }

}
