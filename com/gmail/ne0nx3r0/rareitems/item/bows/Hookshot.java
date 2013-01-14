package com.gmail.ne0nx3r0.rareitems.item.bows;

import com.gmail.ne0nx3r0.rareitems.item.ItemProperty;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.ProjectileHitEvent;

public class Hookshot extends ItemProperty
{
    public Hookshot()
    {
        super(45,"Hookshot","Arrows that hit the ground pull the shooter to them.",3);
    }
    
    @Override
    public boolean onArrowHitGround(ProjectileHitEvent e,Player shooter, int level)
    {
        Location location = e.getEntity().getLocation();
        
        shooter.teleport(location);
        
        shooter.sendMessage("Your hookshot arrow pulls you away!");
        
        return true;
    }
}