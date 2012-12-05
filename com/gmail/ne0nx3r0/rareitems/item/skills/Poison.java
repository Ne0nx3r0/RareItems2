package com.gmail.ne0nx3r0.rareitems.item.skills;

import com.gmail.ne0nx3r0.rareitems.item.ItemProperty;
import java.util.Random;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Poison extends ItemProperty
{
    public Poison()
    {
        super(19,"Poison","25% chance to poison an enemy!",4);
    }
    
    @Override
    public boolean onDamagedOther(final EntityDamageByEntityEvent e,int level)
    {
        if(new Random().nextInt(4) == 0
        && e.getEntity() instanceof LivingEntity)
        {
            LivingEntity le = (LivingEntity) e.getEntity();
            
            le.addPotionEffect(new PotionEffect(PotionEffectType.POISON,200,1*level));

            if(e.getDamager() instanceof Player)
            {
                ((Player) e.getDamager()).sendMessage("Poisoned!");
            }
            if(e.getEntity() instanceof Player)
            {
                ((Player) e.getEntity()).sendMessage("You are poisoned!");
            }
            
            return true;
        }
        return false;
    }
}