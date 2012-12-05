package com.gmail.ne0nx3r0.rareitems.item.enchantments;


import com.gmail.ne0nx3r0.rareitems.item.ItemProperty;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractEvent;

public class MeltObsidian extends ItemProperty
{
    public MeltObsidian()
    {
        super(24,"Melt Obsidian","Make lava from obsidian",1);
    }

    @Override
    public boolean onInteract(PlayerInteractEvent e,int level)
    {
        if(e.getClickedBlock() != null)
        {
            if(e.getClickedBlock().getType() == Material.OBSIDIAN)
            {
                e.getClickedBlock().setType(Material.LAVA);
                
                return true;
            }
        }
        return false;
    }
}