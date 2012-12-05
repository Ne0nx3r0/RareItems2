package com.gmail.ne0nx3r0.rareitems.item.spells;

import com.gmail.ne0nx3r0.rareitems.item.ItemProperty;
import org.bukkit.entity.EntityType;
import org.bukkit.event.player.PlayerInteractEvent;

public class SummonBat extends ItemProperty
{
    public SummonBat()
    {
        super(30,"Summon Bat","Creates a bat.",6);
    }
    
    @Override
    public boolean onInteract(PlayerInteractEvent e,int level)
    {
        for(int i=0;i<level;i++)
        {
            e.getPlayer().getWorld().spawnEntity(e.getPlayer().getLocation(),EntityType.BAT);
        }
        
        return true;
    }
}