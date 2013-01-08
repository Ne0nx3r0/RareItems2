package com.gmail.ne0nx3r0.rareitems.item.skills;

import com.gmail.ne0nx3r0.rareitems.item.ItemProperty;
import java.util.Random;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Disarm extends ItemProperty
{
    public Disarm()
    {
        super(50,"Disarm","Forces an opponent to put away their actively held weapon. (20% chance onhit)",4);
    }
    
    @Override
    public boolean onDamagedOther(final EntityDamageByEntityEvent e,int level)
    {
        if(new Random().nextInt(5) == 0
        && e.getEntity() instanceof LivingEntity)
        {
            LivingEntity le = (LivingEntity) e.getEntity();
            
            le.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS,40*level,1));
            
            if(e.getDamager() instanceof Player)
            {
                ((Player) e.getDamager()).sendMessage("Disarmed!");
            }
            if(e.getEntity() instanceof Player)
            {
                ((Player) e.getEntity()).sendMessage("You have been disarmed!");
            }
            
            return true;
        }
        
        return false;
    }
}