package red.civ.quarryplugin;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Random;

public class Quarry implements Serializable {

    Random random = new Random();

    int xlen;
    int zlen;

    int pos[] = new int[3];

    Fuel quarryfuelstore;

    SLoc s_mining_origin;
    transient Location mining_origin;

    SLoc s_quarry_furance;
    transient Location quarry_furnace;

    SLoc s_laspos;
    transient Location laspos;

    private int dirx=+1;//directionality of x scanning
    private int dirz=+1;//directionality of z scanning

    private int dir=0;

    private int bedrockHits = 0;

    public boolean REMOVED = false;

    public Quarry(Location qf, Location og, int szx, int szz){

        quarryfuelstore = new Fuel();
        quarryfuelstore.fuel_level=10;
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



    public Location unadaptLocation(SLoc loc){
        return new Location(Bukkit.getWorld(loc.worldname),loc.x,loc.y,loc.z);
    }

    public boolean unadaptAllLocations(){
        try {
            if (mining_origin == null) {
                mining_origin = unadaptLocation(s_mining_origin);
            }
            if (quarry_furnace == null) {
                quarry_furnace = unadaptLocation(s_quarry_furance);
            }
            if (laspos == null) {
                laspos = unadaptLocation(s_laspos);
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

        //REMOVE CASES
        if(this.quarry_furnace == null){
            String msg = "CANNOTPARSE";
            if(s_quarry_furance != null){
                msg = s_quarry_furance.toString();
            }
            REMOVED=true;
            Logger.Warn("Forced to remove a null quarry, at " + msg);
            return true;
        }
        if(!quarry_furnace.getBlock().getType().equals(Material.FURNACE)  || bedrockHits>0){
            if(Config.returnQuarryOnBreak){
                Location loc = unadaptLocation(s_quarry_furance);
                loc.getWorld().dropItem(loc,Recipe.qitem);
            }
            Logger.Warn("Forced to remove a quarry due to no quarry block, at " + quarry_furnace.toString());
            REMOVED=true;
            return true;
        }


        if(laspos != null){
            for(int i = laspos.getBlockY(); i<= mining_origin.getBlockY(); i++){

                laspos.setY(i);
                if(i>-64){
                    laspos.getBlock().setType(Material.AIR);
                }
            }
        }

        if(mining_origin.getBlockY()+pos[1] < -64){//set this to be -64 or 0
            return true;
        }

        //Logger.Warn("Fuel is " + quarryfuelstore.fuel_level);
        if(Config.consumefuel){
            //Logger.Warn("consumefueltrue");
            //logic to prevent quarry from running if low fuel AND logic to consume fuel as needed

            if(quarry_furnace.clone().add(0,1,0).getBlock().getType().equals(Material.CHEST)){
                Chest fuelchest = (Chest) quarry_furnace.clone().add(0,1,0).getBlock().getState();
                Inventory fuels = fuelchest.getInventory();
                //Logger.Warn("looking fuel here");
                for(int i=0; i < fuels.getSize();i++){
                    ItemStack itemStack = fuels.getItem(i);
                    if(itemStack != null) {
                        if (quarryfuelstore.refueltick(itemStack.getType())) {
                            itemStack.setAmount(itemStack.getAmount() - 1);
                        }
                    }
                }
            }



        }


        //Logger.Info("Digging2");
        Block b = mining_origin.getWorld().getBlockAt(mining_origin.getBlockX()+pos[0], mining_origin.getBlockY()+pos[1], mining_origin.getBlockZ()+pos[2]);

        if(b.getType().equals(Material.BEDROCK)){
            bedrockHits++;
            return true;
        }

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
        Material btype = b.getType();

        if(!(btype.equals(Material.WATER) || btype.equals(Material.LAVA))) {

            if(Config.consumefuel){//prevent quarry if out of fuel
                if(quarryfuelstore.consumetick()){
                    Player nearest = (Player) quarry_furnace.getWorld().getNearbyEntities(quarry_furnace, 6, 6, 6).stream()
                            .filter(e -> e instanceof Player)
                            .findFirst()
                            .orElse(null);
                    if(nearest != null){
                        int randmsgchance = random.nextInt(15);
                        if(randmsgchance==10){
                            nearest.sendMessage(ChatColor.RED + "Your quarry is out of fuel! Put a chest above the quarry with coal/charcoal");
                        }
                    }
                    return false;
                }
            }


            //b.breakNaturally();
            float bhard = b.getType().getHardness();

            if(bhard <0 || bhard >2000){
                return false;
            }

            int randomNumber = random.nextInt((int) bhard + 1) + 1;
            //Logger.Info("Rand is " + randomNumber);
            if (randomNumber != 1) {
                return false;
            }

            if (b.getType().equals(Material.CHEST) || b.getType().equals(Material.CHEST)) {
                b.breakNaturally();
            } else {
                ItemStack olditem = new ItemStack(b.getType(), 1);
                b.setType(Material.AIR);
                chestchad(quarry_furnace, olditem);
            }
        }


        //Logger.Info("Breaking " + b.getLocation());

        //Logger.Info(String.valueOf(origin.getWorld().getBlockAt(origin.getBlockX()+pos[0],origin.getBlockY()+pos[1],origin.getBlockZ()+pos[2]).getType()));
        //Logger.Info(String.valueOf(Material.AIR));

        if(btype.equals(Material.AIR) || btype.equals(Material.WATER) || btype.equals(Material.LAVA)){
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
