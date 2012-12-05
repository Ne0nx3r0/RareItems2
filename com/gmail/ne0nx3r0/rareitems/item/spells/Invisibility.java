package com.gmail.ne0nx3r0.rareitems.item.spells;


import com.gmail.ne0nx3r0.rareitems.item.ItemProperty;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Invisibility extends ItemProperty
{
    public Invisibility()
    {
        super(38,"Invisibility","Temporarily render yourself invisible. (60 seconds / level)",8);
    }
    
    @Override
    public boolean onInteract(PlayerInteractEvent e,int level)
    {   
        e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY,20*60*level,1));
        
        e.getPlayer().sendMessage("You are invisible!");
        
        return true;
    }
}