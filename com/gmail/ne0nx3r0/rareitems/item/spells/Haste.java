package com.gmail.ne0nx3r0.rareitems.item.spells;

import org.bukkit.event.player.PlayerInteractEvent;
import com.gmail.ne0nx3r0.rareitems.item.ItemProperty;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Haste extends ItemProperty
{    
    public Haste()
    {
        super(27,"Fast Mining","Allows you to mine faster than normal",4); 
    }
    
    @Override
    public boolean onInteract(PlayerInteractEvent e,int level)
    {
        e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SPEED,20*60,20*level));
        
        return true;
    }
}