package red.civ.quarryplugin;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class Recipe {

    public static ItemStack qitem;


    public static void init(){

        if(Config.defaultrecipe == false){
            return;
        }
        Logger.Info("Enabling default recipe");

        qitem = new ItemStack(Material.FURNACE);
        List<String> lore = new ArrayList<>();
        lore.add("Quarry Block");
        ItemMeta newm = qitem.getItemMeta();
        newm.setLore(lore);
        qitem.setItemMeta(newm);


        ShapedRecipe sr = new ShapedRecipe(NamespacedKey.minecraft("quarry"), qitem );
        sr.shape("WDW",
                "DFD",
                "WDW");
        sr.setIngredient('D',Material.DIAMOND_BLOCK);
        sr.setIngredient('F',Material.FURNACE);
        sr.setIngredient('W',Material.OAK_PLANKS);
        Bukkit.getServer().addRecipe(sr);

    }

}
