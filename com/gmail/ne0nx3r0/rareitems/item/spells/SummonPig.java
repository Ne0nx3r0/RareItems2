package com.gmail.ne0nx3r0.rareitems.item.spells;

import com.gmail.ne0nx3r0.rareitems.item.ItemProperty;
import org.bukkit.entity.EntityType;
import org.bukkit.event.player.PlayerInteractEvent;

public class SummonPig extends ItemProperty
{
    public SummonPig()
    {
        super(37,"Summon Pig","Creates a pig.",6);
    }
    
    @Override
    public boolean onInteract(PlayerInteractEvent e,int level)
    {
        for(int i=0;i<level;i++)
        {
            e.getPlayer().getWorld().spawnEntity(e.getPlayer().getLocation(),EntityType.PIG);
        }
        
        return true;
    }
}