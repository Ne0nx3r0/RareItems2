package com.gmail.ne0nx3r0.rareitems.item.enchantments;


import com.gmail.ne0nx3r0.rareitems.item.ItemProperty;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractEvent;

public class Spore extends ItemProperty
{
    public Spore()
    {
        super(2,"Spore","Turns clicked cobblestone into mossy cobblestone.",1);
    }
    
    @Override
    public boolean onInteract(PlayerInteractEvent e,int level)
    {
        if(e.getClickedBlock() != null && e.getClickedBlock().getType() == Material.COBBLESTONE)
        {
            e.getClickedBlock().setType(Material.MOSSY_COBBLESTONE);
            
            return true;
        }
        
        return false;
    }
}