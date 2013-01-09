package com.gmail.ne0nx3r0.rareitems.item.enchantments;


import com.gmail.ne0nx3r0.rareitems.item.ItemProperty;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractEvent;

public class Smelt extends ItemProperty
{
    public Smelt()
    {
        super(1,"Smelt","Turns clicked cobblestone into stone",1);
    }

    @Override
    public boolean onInteract(PlayerInteractEvent e,int level)
    {
        if(e.getClickedBlock() != null && e.getClickedBlock().getType() == Material.COBBLESTONE)
        {
            e.getClickedBlock().setType(Material.STONE);
            
            return true;
        }
        return false;
    }
}