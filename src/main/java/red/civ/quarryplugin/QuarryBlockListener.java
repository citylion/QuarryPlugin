package red.civ.quarryplugin;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

public class QuarryBlockListener implements Listener {

    static String error = "[Quarry] Invalid placement, check torches";

    @EventHandler
    public static void onPlace(BlockPlaceEvent event){

        ItemStack inhand = event.getItemInHand();
        Logger.Info("Checking");

        if(inhand.getLore() == null){
            return;
        }

        if(inhand.getLore().get(0).equals("Quarry Block")){
            Logger.Info("Pass0");

            Location firsttorch = null;
            Location secondtorch = null;
            Location thirdtorch = null;
            int maxlook =200;
            Block quarryfurnace = event.getBlock();


            //find first torch;
            if(quarryfurnace.getLocation().add(1,0,0).getBlock().getType().equals(Material.REDSTONE_TORCH)){
                Logger.Info("Pass1");
                firsttorch = quarryfurnace.getLocation().add(1,0,0);
                secondtorch = findnext(firsttorch,"Z",maxlook);
                thirdtorch = findnext(firsttorch,"X",maxlook);
                if(secondtorch == null || thirdtorch ==null){event.getPlayer().sendMessage(error);}
            }

            if(quarryfurnace.getLocation().add(-1,0,0).getBlock().getType().equals(Material.REDSTONE_TORCH)){
                Logger.Info("Pass2");
                firsttorch = quarryfurnace.getLocation().add(-1,0,0);
                secondtorch = findnext(firsttorch,"Z",maxlook);
                thirdtorch = findnext(firsttorch,"X",maxlook);
                if(secondtorch == null || thirdtorch ==null){event.getPlayer().sendMessage(error);}
            }

            if(quarryfurnace.getLocation().add(0,0,1).getBlock().getType().equals(Material.REDSTONE_TORCH)){
                Logger.Info("Pass3");
                firsttorch = quarryfurnace.getLocation().add(0,0,1);
                Logger.Info("First torch set to " + firsttorch.toString());
                secondtorch = findnext(firsttorch,"Z",maxlook);
                thirdtorch = findnext(firsttorch,"X",maxlook);
                if(secondtorch == null || thirdtorch ==null){event.getPlayer().sendMessage(error);}
            }

            if(quarryfurnace.getLocation().add(0,0,-1).getBlock().getType().equals(Material.REDSTONE_TORCH)){
                Logger.Info("Pass4");
                firsttorch = quarryfurnace.getLocation().add(0,0,-1);
                secondtorch = findnext(firsttorch,"Z",maxlook);
                thirdtorch = findnext(firsttorch,"X",maxlook);
                if(secondtorch == null || thirdtorch ==null){event.getPlayer().sendMessage(error);}
            }
            Logger.Info("Pass5");

            Logger.Info("Torch1: "  + firsttorch.toString());
            Logger.Info("Torch2: "  + secondtorch.toString());
            Logger.Info("Torch3: "  + thirdtorch.toString());

            boolean isClear = true;
            int minX = (int) Math.min(Math.min(firsttorch.getX(), secondtorch.getX()), thirdtorch.getX());
            int minZ = (int) Math.min(Math.min(firsttorch.getZ(), secondtorch.getZ()), thirdtorch.getZ());
            int maxX = (int) Math.max(Math.max(firsttorch.getX(), secondtorch.getX()), thirdtorch.getX());
            int maxZ = (int) Math.max(Math.max(firsttorch.getZ(), secondtorch.getZ()), thirdtorch.getZ());
            //se max x z
            Location secorner = new Location(firsttorch.getWorld(),minX,firsttorch.getBlockY(),minZ);
            int sizeX = Math.abs(minX-maxX);
            int sizeZ = Math.abs(minZ-maxZ);

            QuarryCore.addQuarry(event.getBlock().getLocation(), secorner,sizeX,sizeZ);

        }

    }

    public static Location findnext(Location in, String direction, int maxsearch){//north z---- south z+++++ west x------ east x++++++
        Location current = in.clone();
        Logger.Info("Asked to findnext with " + direction);
        if(direction.equals("Z")){
            current = in.clone();
            for(int i=1; i<maxsearch;i++) {
                current.add(0,0,1);
                Logger.Info("Checking for torch at " + current.toString());
                if(current.getBlock().getType().equals(Material.REDSTONE_TORCH)){
                    Logger.Info("Returning for torch at " + current.toString());
                    return current.getBlock().getLocation();
                }
            }
            current = in.clone();
            for(int i=1; i<maxsearch;i++) {
                current.add(0,0,-1);
                Logger.Info("Checking for torch at " + current.toString());
                if(current.getBlock().getType().equals(Material.REDSTONE_TORCH)){
                    Logger.Info("Returning for torch at " + current.toString());
                    return current.getBlock().getLocation();
                }
            }
        }
        else if(direction.equals("X")){
            current = in.clone();
            for(int i=1; i<maxsearch;i++) {
                current.add(1,0,0);
                Logger.Info("Checking for torch at " + current.toString());
                if(current.getBlock().getType().equals(Material.REDSTONE_TORCH)){
                    Logger.Info("Returning for torch at " + current.toString());
                    return current.getBlock().getLocation();
                }
            }
            current = in.clone();
            for(int i=1; i<maxsearch;i++) {
                current.add(-1,0,0);
                Logger.Info("Checking for torch at " + current.toString());
                if(current.getBlock().getType().equals(Material.REDSTONE_TORCH)){
                    Logger.Info("Returning for torch at " + current.toString());
                    return current.getBlock().getLocation();
                }
            }
        }

        return null;

    }






}

