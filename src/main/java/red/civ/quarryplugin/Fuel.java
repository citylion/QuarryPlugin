package red.civ.quarryplugin;

import org.bukkit.Material;

import java.io.Serializable;

public class Fuel implements Serializable {

    float fuel_level; //between 1 and 500 typically

    public float costpertick = 1;

    public static void init(){

    }


    public  float getfuelvalue(Material material){
        switch (material){

            case CHARCOAL:
            case COAL:
                return 100;
            case COAL_BLOCK:
                return 900;
            case LAVA_BUCKET:
                return 2000;
            default:
                return 0;
        }


    }

    public boolean refueltick(Material material){

        float value = getfuelvalue(material);
        //Logger.Warn("The fuel value of " + material + " is " + value);

        if(value <.9){
            return false;//do not consume
        }
        if(fuel_level>2000){
            return false;//do not consume
        }
        //do consume:
        fuel_level=fuel_level+value;
        //Logger.Warn("Fuel value is now " + fuel_level);
        return true;

    }

    public boolean consumetick(){
        if(fuel_level<1){
            return true;//IF TRUE -> QUARRY SHOULD STOP
        }
        fuel_level=fuel_level-1;
        return false;
    }


    public static String displayreadablefuel(){
        return "";
    }





}
