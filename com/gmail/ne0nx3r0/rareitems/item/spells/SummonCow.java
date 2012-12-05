package com.gmail.ne0nx3r0.rareitems.item.spells;

import com.gmail.ne0nx3r0.rareitems.item.ItemProperty;
import org.bukkit.entity.EntityType;
import org.bukkit.event.player.PlayerInteractEvent;

public class SummonCow extends ItemProperty
{
    public SummonCow()
    {
        super(32,"Summon Cow","Creates a cow.",6);
    }
    
    @Override
    public boolean onInteract(PlayerInteractEvent e, int level)
    {
        for(int i=0;i<level;i++)
        {
            e.getPlayer().getWorld().spawnEntity(e.getPlayer().getLocation(),EntityType.COW);
        }
        
        return true;
    }
}