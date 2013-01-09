package com.gmail.ne0nx3r0.rareitems.item.spells;

import com.gmail.ne0nx3r0.rareitems.item.ItemProperty;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Haste extends ItemProperty
{    
    public Haste()
    {
        needs more testing.
        super(27,"Fast Mining","Allows you to mine faster than normal",4); 
    }
    
    @Override
    public boolean onInteract(PlayerInteractEvent e,int level)
    {
        e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SPEED,20*60,20*level));
        
        return true;
    }
    
    @Override
    public boolean onInteractEntity(PlayerInteractEntityEvent e, int level)
    {
        if(e.getRightClicked() instanceof LivingEntity)
        {
            LivingEntity le = (LivingEntity) e.getRightClicked();

            le.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,20*60,20*level));
                    
            if(le instanceof Player)
            {
                e.getPlayer().sendMessage("You cast "+this.getName()+" on "+((Player) le).getName()+"!");
                ((Player) le).sendMessage(e.getPlayer().getName()+" cast "+this.getName()+" on you!");
            }
            else
            {
                e.getPlayer().sendMessage("You cast "+this.getName()+" on that thing!");
            }
            
            return true;
        }
        return false;
    }
}