package com.gmail.ne0nx3r0.rareitems.item.enchantments;

import com.gmail.ne0nx3r0.rareitems.item.ItemProperty;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractEvent;

public class Fertilize extends ItemProperty
{
    public Fertilize()
    {
        super(3,"Fertilize","Turns clicked dirt blocks to grass",2);
    }
    
    @Override
    public boolean onInteract(PlayerInteractEvent e,int level)
    {
        if(e.getClickedBlock() != null && e.getClickedBlock().getType() == Material.DIRT)
        {
            e.getClickedBlock().setType(Material.GRASS);
            
            return true;
        }
        
        return false;
    }
}
