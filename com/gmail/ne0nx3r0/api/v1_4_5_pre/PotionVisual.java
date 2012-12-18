package com.gmail.ne0nx3r0.api.v1_4_5_pre;

import com.gmail.ne0nx3r0.rareitems.RareItems;
import net.minecraft.server.DataWatcher;
import net.minecraft.server.EntityLiving;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.entity.CraftLivingEntity;
import org.bukkit.entity.LivingEntity;

public class PotionVisual
{
    public static int addPotionGraphicalEffect(LivingEntity entity, int color, int duration)
    {
        final EntityLiving el = ((CraftLivingEntity)entity).getHandle();
        
        final DataWatcher dw = el.getDataWatcher();
        
        dw.watch(8, Integer.valueOf(color));

        return Bukkit.getScheduler().scheduleSyncDelayedTask(RareItems.self, new Runnable()
        {
            @Override
            public void run()
            {
                int c = 0;
                
                if(!el.effects.isEmpty())
                {
                    c = net.minecraft.server.PotionBrewer.a(el.effects.values());
                }
                
                dw.watch(8, Integer.valueOf(c));
            }
        }, duration);
    }
}
