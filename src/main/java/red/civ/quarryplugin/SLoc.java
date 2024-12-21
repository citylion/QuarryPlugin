package red.civ.quarryplugin;

import org.bukkit.Location;

import java.io.Serializable;
import java.util.Objects;

public class SLoc implements Serializable {

    String worldname;
    double x;
    double z;

    double y;

    public static SLoc quickSLOC(Location location){
        return new SLoc(location.getWorld().getName(),location.getX(),location.getY(),location.getZ());
    }

    public SLoc(String wname, double ix, double iy, double iz) {
        worldname=wname;
        x=ix;z=iz;y=iy;
    }

    @Override
    public boolean equals(Object maybe){
        if(!(maybe instanceof SLoc)){return false;}
        SLoc other = (SLoc) maybe;
        if(this.x==other.x && this.y==other.y && this.z==other.z){
            if(this.worldname.equalsIgnoreCase(((SLoc) maybe).worldname)){
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x,y,z,worldname);
    }

}
