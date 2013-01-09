package com.gmail.ne0nx3r0.rareitems.item.skills;

import com.gmail.ne0nx3r0.rareitems.item.ItemProperty;
import java.util.Random;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Blinding extends ItemProperty
{
    public Blinding()
    {
        super(49,"Blinding","20% chance to blind a target onhit (3s / level)",4);
    }
    
    @Override
    public boolean onDamagedOther(final EntityDamageByEntityEvent e,int level)
    {
        if(new Random().nextInt(5) == 0
        && e.getEntity() instanceof LivingEntity)
        {
            LivingEntity le = (LivingEntity) e.getEntity();
            
            le.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS,60*level,1));
            
            if(e.getDamager() instanceof Player)
            {
                ((Player) e.getDamager()).sendMessage("Blinded!");
            }
            if(e.getEntity() instanceof Player)
            {
                ((Player) e.getEntity()).sendMessage("You are blinded!");
            }
            
            return true;
        }
        
        return false;
    }
}