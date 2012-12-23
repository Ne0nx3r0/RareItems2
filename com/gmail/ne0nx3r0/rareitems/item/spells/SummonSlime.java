package com.gmail.ne0nx3r0.rareitems.item.spells;

import com.gmail.ne0nx3r0.rareitems.item.ItemProperty;
import java.util.Random;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Slime;
import org.bukkit.event.player.PlayerInteractEvent;

public class SummonSlime extends ItemProperty
{
    public SummonSlime()
    {
        super(36,"Summon Slime","Creates a slime.",16);
    }
    
    @Override
    public boolean onInteract(PlayerInteractEvent e,int level)
    {
        for(int i=0;i<level;i++)
        {
            Slime slime = (Slime)  e.getPlayer().getWorld().spawnEntity(e.getPlayer().getLocation(),EntityType.SLIME);
            slime.setSize(new Random().nextInt(5)+1);
        }

        return true;
    }
}