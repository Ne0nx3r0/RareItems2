package com.gmail.ne0nx3r0.rareitems.item.bows;

import com.gmail.ne0nx3r0.rareitems.item.ItemProperty;
import java.util.Random;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PoisonArrow extends ItemProperty
{
    public PoisonArrow()
    {
        super(46,"Poison Arrow","Arrows that hit a target have a 20% chance to poison it. (6 seconds @ 1 * level dmg)",6);
    }
    
    @Override
    public boolean onArrowHitEntity(EntityDamageByEntityEvent e, Player shooter, int level)
    {
        if(e.getEntity() instanceof LivingEntity)
        {
            if(new Random().nextInt(5) == 0)
            {
                ((LivingEntity) e.getEntity()).addPotionEffect(new PotionEffect(PotionEffectType.POISON,120,level));

                if(e.getEntity() instanceof Player)
                {
                    ((Player) e.getEntity()).sendMessage("You've been hit by a poisoned arrow!");
                }
                
                return true;
            }
        }
        return false;
    }
}