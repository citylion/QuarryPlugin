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
    @EventHandler
    public static void onPlace(BlockPlaceEvent event){

        ItemStack inhand = event.getItemInHand();
        Logger.Info("Checking");
        if(inhand.getLore().get(0).equals("Quarry Block")){
            Logger.Info("Pass1");

            Location firsttorch = null;
            Location secondtorch = null;
            Location thirdtorch = null;

            Location b = event.getBlock().getLocation();

            b=b.add(1,0,0);
            Logger.Info(b.toString());
            if(b.getBlock().getType().equals(Material.REDSTONE_TORCH)){
                firsttorch=b;
                Logger.Info("Pass1a");

            }
            b=b.add(-1,0,1);
            Logger.Info(b.toString());
            if(b.getBlock().getType().equals(Material.REDSTONE_TORCH)){
                firsttorch = b;
                Logger.Info("Pass1b");

            }
            b=b.add(-1,0,-1);
            Logger.Info(b.toString());
            if(b.getBlock().getType().equals(Material.REDSTONE_TORCH)){
                firsttorch = b;
                Logger.Info("Pass1c");

            }
            b=b.add(1,0,-1);
            Logger.Info(b.toString());
            if(b.getBlock().getType().equals(Material.REDSTONE_TORCH)){
                firsttorch = b;
                Logger.Info("Pass1d");

            }
            Logger.Info("Pass2 firsttorch = " + firsttorch.toString());

            for(int i=1; i<10; i++){
                b = firsttorch;

                b=b.add(i,0,0);
                Logger.Info(b.toString());
                if(b.getBlock().getType().equals(Material.REDSTONE_TORCH)){
                    secondtorch=b;
                    Logger.Info("Pass2a");
                    break;
                }
                b=b.add(-i,0,i);
                Logger.Info(b.toString());
                if(b.getBlock().getType().equals(Material.REDSTONE_TORCH)){
                    secondtorch = b;
                    Logger.Info("Pass2b");
                    break;
                }
                b=b.add(-i,0,-i);
                Logger.Info(b.toString());
                if(b.getBlock().getType().equals(Material.REDSTONE_TORCH)){
                    secondtorch = b;
                    Logger.Info("Pass2c");
                    break;
                }
                b=b.add(i,0,-i);
                Logger.Info(b.toString());
                if(b.getBlock().getType().equals(Material.REDSTONE_TORCH)){
                    secondtorch = b;
                    Logger.Info("Pass2d");
                    break;
                }
                //Logger.Info("Skipping2");
            }

            Logger.Info("Pass3");
            for(int i=1; i<10; i++){
                b = secondtorch;

                b=b.add(i,0,0);
                Logger.Info(b.toString());
                if(b.getBlock().getType().equals(Material.REDSTONE_TORCH) && b != secondtorch){
                    thirdtorch=b;
                    Logger.Info("Pass3a");
                    break;
                }
                b=b.add(-i,0,i);
                Logger.Info(b.toString());
                if(b.getBlock().getType().equals(Material.REDSTONE_TORCH) && b != secondtorch){
                    thirdtorch = b;
                    Logger.Info("Pass3b");
                    break;
                }
                b=b.add(-i,0,-i);
                Logger.Info(b.toString());
                if(b.getBlock().getType().equals(Material.REDSTONE_TORCH) && b != secondtorch){
                    thirdtorch = b;
                    Logger.Info("Pass3c");
                    break;
                }
                b=b.add(i,0,-i);
                Logger.Info(b.toString());
                if(b.getBlock().getType().equals(Material.REDSTONE_TORCH) && b != secondtorch){
                    thirdtorch = b;
                    Logger.Info("Pass3d");
                    break;
                }
                //Logger.Info("Skipping3");
            }

            boolean isClear = true;
            int minX = (int) Math.min(Math.min(firsttorch.getX(), secondtorch.getX()), thirdtorch.getX());
            int minZ = (int) Math.min(Math.min(firsttorch.getZ(), secondtorch.getZ()), thirdtorch.getZ());
            int maxX = (int) Math.max(Math.max(firsttorch.getX(), secondtorch.getX()), thirdtorch.getX());
            int maxZ = (int) Math.max(Math.max(firsttorch.getZ(), secondtorch.getZ()), thirdtorch.getZ());

            for (int x = minX; x <= maxX; x++) {
                for (int z = minZ; z <= maxZ; z++) {
                    Block block = firsttorch.getWorld().getBlockAt(x, (int) firsttorch.getY(), z);
                    Material blockMaterial = block.getType();
                    if (blockMaterial != Material.AIR && blockMaterial != Material.REDSTONE_TORCH) {
                        isClear = false;
                        Logger.Info("Skipping4 " + block.getLocation());
                        break;
                    }
                }
            }

            Location southeastCorner = new Location(firsttorch.getWorld(), maxX, firsttorch.getY(), maxZ);
            int sizeX = (int) Math.abs(firsttorch.getX() - southeastCorner.getX());
            int sizeZ = (int) Math.abs(firsttorch.getZ() - southeastCorner.getZ());


            QuarryCore.addQuarry(southeastCorner,sizeX,sizeZ);

        }

    }






}

