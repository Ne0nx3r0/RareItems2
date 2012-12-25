package com.gmail.ne0nx3r0.rareitems.item.bows;

import com.gmail.ne0nx3r0.rareitems.item.ItemProperty;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class FireArrow extends ItemProperty
{
    public FireArrow()
    {
        super(44,"Fire Arrow","Light your opponents ablaze!",1);
    }
    
    @Override
    public boolean onArrowHitEntity(EntityDamageByEntityEvent e, Player shooter, int level)
    {
        e.getEntity().setFireTicks(level*20);
        
        return true;
    }
}