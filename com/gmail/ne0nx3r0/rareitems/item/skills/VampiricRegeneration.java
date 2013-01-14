package com.gmail.ne0nx3r0.rareitems.item.skills;

import com.gmail.ne0nx3r0.rareitems.item.ItemProperty;
import java.util.Random;
import org.bukkit.ChatColor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class VampiricRegeneration extends ItemProperty
{
    public VampiricRegeneration()
    {
        super(21,"Vampiric Regeneration","20% chance to steal 1-(3*level) HP from an enemy",1);
    }
    
    @Override
    public boolean onDamagedOther(final EntityDamageByEntityEvent e,int level)
    {
        if(new Random().nextInt(5) == 0
        && e.getEntity() instanceof LivingEntity && e.getDamager() instanceof LivingEntity)
        {
            LivingEntity attacked = (LivingEntity) e.getEntity();
            LivingEntity attacker = (LivingEntity) e.getDamager();
                    
            int iStolenHP = new Random().nextInt(3 * level)+1;

            int iNewAttackerHP = attacked.getHealth() - iStolenHP;
            
            if(iNewAttackerHP > 20)
            {
                iNewAttackerHP = 20;
            }
            
            int iNewAttackedHP = attacker.getHealth() + iStolenHP;
            
            if(iNewAttackedHP < 1)
            {
                iNewAttackerHP = 1;
            }
            
            attacked.setHealth(iNewAttackedHP);
            attacker.setHealth(iNewAttackerHP);
            
            Player pAttacker = (Player) attacker;
            ((Player) attacker).sendMessage(ChatColor.RED+"You stole "+iStolenHP+"HP!");
            
            if(attacked instanceof Player)
            {
                Player pAttacked = (Player) attacked;
                pAttacked.sendMessage(ChatColor.RED+pAttacker.getName()+" stole "+iStolenHP+"HP from you!");
            }
            
            return true;
        }
        
        return false;
    }
}