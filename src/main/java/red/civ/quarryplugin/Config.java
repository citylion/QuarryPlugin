package red.civ.quarryplugin;

import org.bukkit.configuration.file.FileConfiguration;

import java.lang.reflect.Field;

public class Config {

    public static int maxlook=16*4;//max distance to look for redstone torches
    public static boolean defaultrecipe=true;//if default recipe should be exposed

    public static int tickspeed = 8;//how often to run quarry task in ticks

    public static boolean consumefuel = true;//whether or not to consume fuel to power the quarry

    public static boolean returnQuarryOnBreak = true;

    public static boolean allowbstats;

    public void initialize(QuarryPlugin p) {
        FileConfiguration config = p.getConfig();

        Field[] fields = this.getClass().getDeclaredFields();
        for (Field f : fields) {
            try {
                String string = f.getName().replace("_", ".");
                string = string.toLowerCase();
                Object value = f.get(this);
                config.addDefault(string, value);
                Object yamlValue = config.get(string, value);
                f.set(this, yamlValue);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }


}
