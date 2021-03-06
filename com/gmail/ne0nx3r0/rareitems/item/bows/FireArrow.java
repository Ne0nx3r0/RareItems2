package com.gmail.ne0nx3r0.rareitems.item.bows;

import com.gmail.ne0nx3r0.rareitems.item.ItemProperty;
import java.util.Random;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class FireArrow extends ItemProperty
{
    public FireArrow()
    {
        super(44,"Fire Arrow","Arrows that hit a targt have a 25% chance to light them on fire. (2 seconds / level)",0);
    }
    
    @Override
    public boolean onArrowHitEntity(EntityDamageByEntityEvent e, Player shooter, int level)
    {        
        if(e.getEntity() instanceof LivingEntity)
        {
            if(new Random().nextInt(4) == 0)
            {
                e.getEntity().setFireTicks(level*60);
        
                if(e.getEntity() instanceof Player)
                {
                    ((Player) e.getEntity()).sendMessage("You've been hit by a fire arrow!");
                }
                
                shooter.sendMessage("Fire arrow hit!");
                
                return true;
            }
        }
        return false;
    }
}