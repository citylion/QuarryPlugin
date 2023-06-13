package red.civ.quarryplugin;

import org.bukkit.Location;

import java.io.Serializable;

public class SLoc implements Serializable {

    String worldname;
    double x;
    double z;

    double y;

    public static SLoc quickSLOC(Location location){
        return new SLoc(location.getWorld().getName(),location.getX(),location.getY(),location.getZ());
    }

    public SLoc(String iname, double ix, double iy, double iz) {
        worldname=iname;
        x=ix;z=iz;y=iy;
    }
}
