package com.gmail.ne0nx3r0.rareitems.item.skills;

import com.gmail.ne0nx3r0.rareitems.item.ItemProperty;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class Backstab extends ItemProperty
{
    public Backstab()
    {
        super(15,"Backstab","Deal extra damage if attacking an enemy from behind (damage * level)",8);
    }
    
    @Override
    public boolean onDamagedOther(final EntityDamageByEntityEvent e,int level)
    {
        if(e.getEntity().getLocation().getDirection().dot(e.getDamager().getLocation().getDirection()) > 0.0D)
        {
            e.setDamage(e.getDamage() * level);
            
            if(e.getDamager() instanceof Player)
            {
                ((Player) e.getDamager()).sendMessage("Backstab!");
            }
            if(e.getEntity() instanceof Player)
            {
                ((Player) e.getEntity()).sendMessage("You were backstabbed!");
            }
            
            return true;
        }
        return false;
    }
}