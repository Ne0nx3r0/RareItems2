package com.gmail.ne0nx3r0.rareitems.item.spells;

import com.gmail.ne0nx3r0.rareitems.item.ItemProperty;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class CatsFeet extends ItemProperty
{
    public CatsFeet()
    {
        super(40,"Cat's Feet","Lets you or another player jump much higher.",4);
    }
    
    @Override
    public boolean onInteract(PlayerInteractEvent e,int level)
    {
        e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.JUMP,20*60*level,1*level));
        
        e.getPlayer().sendMessage("You can jump higher!");
        
        return true;
    }    
    
    @Override
    public boolean onInteractEntity(PlayerInteractEntityEvent e, Integer level)
    {
        if(e.getRightClicked() instanceof LivingEntity)
        {
            LivingEntity le = (LivingEntity) e.getRightClicked();

            le.addPotionEffect(new PotionEffect(PotionEffectType.JUMP,20*60*level,1*level));

            if(le instanceof Player)
            {
                e.getPlayer().sendMessage("You cast Cat's Feet on "+((Player) le).getName()+"!");
                ((Player) le).sendMessage(e.getPlayer().getName()+" cast Cat's Feet on you!");
            }
            else
            {
                e.getPlayer().sendMessage("You cast Cat's Feet on that thing!");
            }
            
            return true;
        }
        return false;
    }
}