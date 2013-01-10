package com.gmail.ne0nx3r0.api.v1_4_6;

import net.minecraft.server.v1_4_6.DataWatcher;
import net.minecraft.server.v1_4_6.EntityLiving;
import org.bukkit.craftbukkit.v1_4_6.entity.CraftLivingEntity;
import org.bukkit.entity.LivingEntity;

public class PotionVisual
{
    public static void addPotionGraphicalEffect(LivingEntity entity, int color)
    {
        EntityLiving el = ((CraftLivingEntity)entity).getHandle();
        
        DataWatcher dw = el.getDataWatcher();
        
        dw.watch(8, Integer.valueOf(color));
    }
    
    public static void removePotionGraphicalEffect(LivingEntity entity)
    {
        EntityLiving el = ((CraftLivingEntity)entity).getHandle();
        
        DataWatcher dw = el.getDataWatcher();
        
        int c = 0;

        if(!el.effects.isEmpty())
        {
            c = net.minecraft.server.v1_4_6.PotionBrewer.a(el.effects.values());
        }

        dw.watch(8, Integer.valueOf(c));
    }
}
