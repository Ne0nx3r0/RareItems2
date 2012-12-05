package com.gmail.ne0nx3r0.rareitems.item.skills;

import com.gmail.ne0nx3r0.rareitems.RareItems;
import com.gmail.ne0nx3r0.rareitems.item.ItemProperty;
import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class CallLightning extends ItemProperty
{
    public CallLightning()
    {
        super(17,"Call Lightning","25% chance to strike an opponent with lightning",1);
    }
    
    @Override
    public boolean onDamagedOther(final EntityDamageByEntityEvent e,int level)
    {
        if(new Random().nextInt(4) == 0
        && e.getEntity() instanceof LivingEntity)
        {
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(RareItems.self, new Runnable()
            {
                @Override
                public void run()
                {
                    e.getEntity().getWorld().spawnEntity(e.getEntity().getLocation(), EntityType.LIGHTNING);
                }
            },20);
            
            return true;
        }
        return false;
    }
}