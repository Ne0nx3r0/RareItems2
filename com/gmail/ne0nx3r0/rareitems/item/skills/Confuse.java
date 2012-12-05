package com.gmail.ne0nx3r0.rareitems.item.skills;

import com.gmail.ne0nx3r0.rareitems.item.ItemProperty;
import java.util.Random;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Confuse extends ItemProperty
{
    public Confuse()
    {
        super(18,"Confuse","25% chance to confuse an enemy!",6);
    }
    
    @Override
    public boolean onDamagedOther(final EntityDamageByEntityEvent e,int level)
    {
        if(new Random().nextInt(4) == 0
        && e.getEntity() instanceof LivingEntity)
        {
            LivingEntity le = (LivingEntity) e.getEntity();
            
            le.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION,60*level,1));
            
            if(e.getDamager() instanceof Player)
            {
                ((Player) e.getDamager()).sendMessage("Confused!");
            }
            if(e.getEntity() instanceof Player)
            {
                ((Player) e.getEntity()).sendMessage("You are confused!");
            }
            
            return true;
        }
        
        return false;
    }
}