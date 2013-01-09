package com.gmail.ne0nx3r0.rareitems.item.skills;

import com.gmail.ne0nx3r0.rareitems.item.ItemProperty;
import java.util.Random;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Weaken extends ItemProperty
{
    public Weaken()
    {
        super(22,"Weaken","25% chance to weaken an enemy for 3 seconds/level",5);
    }
    
    @Override
    public boolean onDamagedOther(final EntityDamageByEntityEvent e,int level)
    {
        if(new Random().nextInt(4) == 0
        && e.getEntity() instanceof LivingEntity)
        {
            LivingEntity le = (LivingEntity) e.getEntity();
            
            le.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS,3*20*level,1));

            if(e.getDamager() instanceof Player)
            {
                ((Player) e.getDamager()).sendMessage("Weakened!");
            }
            if(e.getEntity() instanceof Player)
            {
                ((Player) e.getEntity()).sendMessage("You are weakened!");
            } 
            
            return true;
        }
        return false;
    }
}