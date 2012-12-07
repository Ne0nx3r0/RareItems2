package com.gmail.ne0nx3r0.rareitems.item.spells;

import com.gmail.ne0nx3r0.rareitems.item.ItemProperty;
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
        
        e.getPlayer().sendMessage("Upped your fire resistance!");
        
        return true;
    }
}