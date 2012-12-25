package com.gmail.ne0nx3r0.rareitems.item.bows;

import com.gmail.ne0nx3r0.rareitems.item.ItemProperty;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.ProjectileHitEvent;

public class FireArrow extends ItemProperty
{
    public FireArrow()
    {
        super(44,"Fire Arrow","Light your opponents ablaze!",1);
    }
    
    @Override
    public boolean onArrowHitGround(ProjectileHitEvent e,Player shooter, int level)
    {
        
        
        return false;
    }
}