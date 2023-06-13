package red.civ.quarryplugin;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitScheduler;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
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


    public static void seralizeactive(){
        try{
            String name = getQSERFileDir();
            Logger.Info("Trying directory " + name);
            FileOutputStream stream = new FileOutputStream(name);
            ObjectOutputStream output = new ObjectOutputStream(stream);
            for(int i=0; i<quarrylist.size();i++){
                output.writeObject(quarrylist);
            }
        }
        catch (Exception e){
            Logger.Error("SERILIZATION ERROR FOLLOWS");
            e.printStackTrace();
        }

    }

    public static void makelist(){
        try {
            String name = getQSERFileDir();
            Logger.Info("Trying directory " + name);
            FileInputStream fileInputStream = new FileInputStream(name);
            ObjectInputStream inputStream = new ObjectInputStream(fileInputStream);
            quarrylist = (ArrayList<Quarry>) inputStream.readObject();
            inputStream.close();
            fileInputStream.close();

            for(int i=0; i<quarrylist.size();i++){
                if(quarrylist.get(i).unfuckALLLocations()){
                    quarrylist.remove(i);//exception catcher
                }
            }

        } catch (IOException | ClassNotFoundException i) {
            i.printStackTrace();
            return;
        }
    }

    public static String getQSERFileDir() {
        URL url = QuarryCore.class.getProtectionDomain().getCodeSource().getLocation();
        File pluginFolder = null;
        String subfoldername = "QuarryPlugin";
        try {
            pluginFolder = new File(url.toURI().getPath()).getParentFile();
            File builderfolder = new File(pluginFolder, subfoldername);
            if (!builderfolder.exists()) {
                builderfolder.mkdir();
            }
            pluginFolder = builderfolder;
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        String fname = "quarries.ser";
        File f = new File(pluginFolder, fname);
        return f.getAbsolutePath().toString();
    }
}