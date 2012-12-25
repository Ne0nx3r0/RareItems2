package com.gmail.ne0nx3r0.rareitems.item.bows;

import com.gmail.ne0nx3r0.rareitems.item.ItemProperty;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.ProjectileHitEvent;

public class Hookshot extends ItemProperty
{
    public Hookshot()
    {
        super(45,"Hookshot","Because 'grappling hook' is too metro.",1);
    }
    
    @Override
    public boolean onArrowHitGround(ProjectileHitEvent e,Player shooter, int level)
    {
        Location location = e.getEntity().getLocation();
        
        shooter.teleport(location);
        
        return false;
    }
}