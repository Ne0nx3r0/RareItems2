package com.gmail.ne0nx3r0.rareitems.item.enchantments;


import com.gmail.ne0nx3r0.rareitems.item.ItemProperty;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class FireHandling extends ItemProperty
{
    public FireHandling()
    {
        super(23,"Fire Handling","Scoop up fire.",4);
    }
    
    @Override
    public boolean onInteract(PlayerInteractEvent e,int level)
    {
        if(e.getClickedBlock() != null)
        {
            Block bFire = e.getClickedBlock().getRelative(BlockFace.UP);
            
            if(bFire != null
            && bFire.getType() == Material.FIRE)
            {
                bFire.setType(Material.AIR);

                ItemStack fire = new ItemStack(Material.FIRE);

                e.getPlayer().getWorld().dropItemNaturally(bFire.getLocation(), fire);
                
                return true;
            }
        }
        return false;
    }
}