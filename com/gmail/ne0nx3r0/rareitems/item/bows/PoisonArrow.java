package com.gmail.ne0nx3r0.rareitems.item.bows;

import com.gmail.ne0nx3r0.rareitems.item.ItemProperty;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PoisonArrow extends ItemProperty
{
    public PoisonArrow()
    {
        super(46,"Poison Arrow","Eww... It's green, and sticky.",1);
    }
    
    @Override
    public boolean onArrowHitEntity(EntityDamageByEntityEvent e, Player shooter, int level)
    {
        if(e.getEntity() instanceof LivingEntity)
        {
            ((LivingEntity) e.getEntity()).addPotionEffect(new PotionEffect(PotionEffectType.POISON,200,level));
            
            return true;
        }
        return false;
    }
}