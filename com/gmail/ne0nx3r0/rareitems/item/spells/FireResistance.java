package com.gmail.ne0nx3r0.rareitems.item.spells;


import com.gmail.ne0nx3r0.rareitems.item.ItemProperty;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class FireResistance extends ItemProperty
{
    public FireResistance()
    {
        super(25,"Fire Resistance","Use to temporarily up your fire resistance",6);
    }
    
    @Override
    public boolean onInteract(PlayerInteractEvent e,int level)
    {
        e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE,20*60,20*level));
        
        e.getPlayer().sendMessage("Upped your fire resistance!");
        
        return true;
    }
}