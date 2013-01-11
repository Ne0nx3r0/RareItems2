package com.gmail.ne0nx3r0.rareitems.item.spells;

import com.gmail.ne0nx3r0.rareitems.RareItems;
import com.gmail.ne0nx3r0.rareitems.item.ItemProperty;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.util.Vector;

public class Burst extends ItemProperty
{
    public Burst()
    {
        super(51,"Burst","Pushes a clicked target away",2);
    }
    
    @Override
    public boolean onDamagedOther(EntityDamageByEntityEvent e, int level)
    {
        if(e.getEntity() instanceof LivingEntity)
        {        
            LivingEntity le = (LivingEntity) e.getEntity();
            
            try
            {
                RareItems.fireworks.playFirework(
                    le.getWorld(), le.getLocation(),
                    FireworkEffect
                        .builder()
                        .with(Type.BURST)
                        .withColor(Color.WHITE)
                        .build()
                );
            }
            catch (Exception ex)
            {
                Logger.getLogger(Burst.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            Vector unitVector = le.getLocation().toVector().subtract(e.getDamager().getLocation().toVector()).normalize();
            
            unitVector.setY(0.55/level);
            
            le.setVelocity(unitVector.multiply(level*2));
            
            e.setCancelled(true);
            
            return true;
        }
        return false;
    }
}