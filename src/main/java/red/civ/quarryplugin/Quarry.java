package red.civ.quarryplugin;

import org.bukkit.BlockChangeDelegate;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Random;

public class Quarry implements Serializable {

    Random random = new Random();

    int xlen;
    int zlen;

    int pos[] = new int[3];

    SLoc s_mining_origin;
    transient Location mining_origin;

    SLoc s_quarry_furance;
    transient Location quarry_furnace;

    SLoc s_laspos;
    transient Location laspos;

    private int dirx=+1;//directionality of x scanning
    private int dirz=+1;//directionality of z scanning

    private int dir=0;

    public Quarry(Location qf, Location og, int szx, int szz){

        mining_origin =og.add(0,1,0);
        quarry_furnace=qf;
        xlen=szx;
        zlen=szz;
        pos[0]=0;
        pos[1]=0;
        pos[2]=0;
        dir=0;
    }

    public void updateSLOCS(){
        s_laspos = SLoc.quickSLOC(laspos);
        s_mining_origin = SLoc.quickSLOC(mining_origin);
        s_quarry_furance = SLoc.quickSLOC(quarry_furnace);
    }

    /*
    public Quarry(Location qf, Location og, int szx, int szz, int[] ppos, Location plaspos){

        mining_origin =og.add(0,1,0);
        quarry_furnace=qf;
        xlen=szx;
        zlen=szz;
        pos[0]=ppos[0];
        pos[1]=ppos[0];
        pos[2]=ppos[0];
        laspos=plaspos;
        dir=0;
    }*/

    public Location unfuckLocation(SLoc loc){
        return new Location(Bukkit.getWorld(loc.worldname),loc.x,loc.y,loc.z);
    }

    public boolean unfuckALLLocations(){
        try {
            if (mining_origin == null) {
                mining_origin = unfuckLocation(s_mining_origin);
            }
            if (quarry_furnace == null) {
                quarry_furnace = unfuckLocation(s_quarry_furance);
            }
            if (laspos == null) {
                laspos = unfuckLocation(s_laspos);
            }
            return false;
        }
        catch (Exception e){

            Logger.Warn("Forced to remove a quarry. Please report this bug");
            e.printStackTrace();
            return true;
        }
    }

    public boolean dig(){


        //Logger.Info("Digging");

        if(!quarry_furnace.getBlock().getType().equals(Material.FURNACE)){
            return true;
        }


        if(laspos != null){
            for(int i = laspos.getBlockY(); i<= mining_origin.getBlockY(); i++){
                laspos.setY(i);
                laspos.getBlock().setType(Material.AIR);
            }
        }
        //Logger.Info("Digging1");
        if(mining_origin.getBlockY()+pos[1] < 1){
            return true;
        }


        //Logger.Info("Digging2");
        Block b = mining_origin.getWorld().getBlockAt(mining_origin.getBlockX()+pos[0], mining_origin.getBlockY()+pos[1], mining_origin.getBlockZ()+pos[2]);
        //Logger.Info("Digging3");
        Location aboveb = b.getLocation().add(0,1,0);

        aboveb.getBlock().setType(Material.ORANGE_CONCRETE);


        //Logger.Info("Digging4");

        Location fen = aboveb;
        for(int i = b.getY()+2; i<= mining_origin.getBlockY(); i++){
            fen.setY(i);
            fen.getBlock().setType(Material.OAK_FENCE);
        }
        //Logger.Info("Digging5");



        //b.breakNaturally();
        float bhard = b.getType().getHardness();
        int randomNumber = random.nextInt((int) bhard+1) + 1;
        //Logger.Info("Rand is " + randomNumber);
        if(randomNumber != 1){
            return false;
        }

        if(b.getType().equals(Material.CHEST) || b.getType().equals(Material.CHEST)){
            b.breakNaturally();
        }
        else{
            ItemStack olditem = new ItemStack(b.getType(), 1);
            b.setType(Material.AIR);
            chestchad(quarry_furnace,olditem);
        }



        //Logger.Info("Breaking " + b.getLocation());

        //Logger.Info(String.valueOf(origin.getWorld().getBlockAt(origin.getBlockX()+pos[0],origin.getBlockY()+pos[1],origin.getBlockZ()+pos[2]).getType()));
        //Logger.Info(String.valueOf(Material.AIR));
        Material btype = b.getType();
        if(btype.equals(Material.AIR) || btype.equals(Material.WATER)){
            //Logger.Info("AIR now");

            if(pos[0]+1<=xlen){
                //Logger.Info("a"+String.valueOf(pos[0]+1));
                //Logger.Info("a"+String.valueOf(xlen));

                pos[0]=pos[0]+1;
            }
            else if(pos[2]+1<=zlen){
                //Logger.Info("b"+String.valueOf(pos[2]+1));
                //Logger.Info("b"+String.valueOf(zlen));
                pos[0]=0;
                pos[2]=pos[2]+1;
            }
            else{
                pos[0]=0;
                pos[2]=0;
                pos[1]=pos[1]-1;
            }
        }
        else{
            //Logger.Info("Still not air " + mining_origin.getWorld().getBlockAt(mining_origin.getBlockX()+pos[0], mining_origin.getBlockY()+pos[1], mining_origin.getBlockZ()+pos[2]).getType());
        }

        laspos=b.getLocation();
        updateSLOCS();
        return false;
    }

    public void chestchad(Location location, ItemStack toadd){
        int maxlook = 10;
        Location search = location.clone();
        int result = -1;
        for(int i=0; i<maxlook; i++){
            search.add(1,0,0);
            result = depositsearch(search,toadd);
            //Logger.Info("CHECKING " + search);
            if(result==1){return;}
            else if(result==0){
                break;
            }
        }
        search=location.clone();
        for(int i=0; i<maxlook; i++){
            search.add(-1,0,0);
            result = depositsearch(search,toadd);
            //Logger.Info("CHECKING " + search);
            if(result==1){return;}
            else if(result==0){
                break;
            }
        }
        search=location.clone();
        for(int i=0; i<maxlook; i++){
            search.add(0,0,1);
            result = depositsearch(search,toadd);
            //Logger.Info("CHECKING " + search);
            if(result==1){return;}
            else if(result==0){
                break;
            }
        }
        search=location.clone();
        for(int i=0; i<maxlook; i++){
            search.add(0,0,-1);
            result = depositsearch(search,toadd);
            //Logger.Info("CHECKING " + search);
            if(result==1){return;}
            else if(result==0){
                break;
            }
        }
    }

    public int depositsearch(Location search, ItemStack toadd){
        Chest chest = null;
        //Logger.Info("The block type at " + search + " is " + search.getBlock().getType());
        if(search.getBlock().getType().equals(Material.CHEST)){
            //Logger.Info("IN CHECK");
            chest = (Chest) search.getBlock().getState();
            HashMap notin = null;
            notin = chest.getInventory().addItem(toadd);
            if(!notin.isEmpty()){
                //Logger.Info("Notin is not empty");
                return 2;//look for chests next to
            }
            //Logger.Info("Success filled");
            return 1;//success filled
        }
        //Logger.Info("UnSuccess filled");
        return 0;//unsuccess
    }


}
