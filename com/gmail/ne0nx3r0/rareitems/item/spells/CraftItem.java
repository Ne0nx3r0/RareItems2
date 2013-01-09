package com.gmail.ne0nx3r0.rareitems.item.spells;

import com.gmail.ne0nx3r0.rareitems.item.ItemProperty;
import org.bukkit.event.player.PlayerInteractEvent;

public class CraftItem extends ItemProperty
{
    public CraftItem()
    {
        super(16,"Craft Item","Opens a virtual crafting table",1);
    }
    
    @Override
    public boolean onInteract(PlayerInteractEvent e,int level)
    {
        e.getPlayer().openWorkbench(null, true);
        
        return true;
    }
}