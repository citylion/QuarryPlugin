package red.civ.quarryplugin;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.ArrayList;

public class QuarryCore {

    public static ArrayList<Quarry> quarrylist = new ArrayList<Quarry>();

    public static void addQuarry(Location furnace, Location secnr, int xs, int zs){
        Logger.Info("Adding a new quary at " + secnr.toString());
        Quarry q = new Quarry(furnace, secnr, xs, zs);

        quarrylist.add(q);


    }

    public static void digAll(){

        for(int i=0; i<quarrylist.size();i++){
            if(quarrylist.get(i).dig()){
                quarrylist.remove(i);
            }
        }

    }



}
