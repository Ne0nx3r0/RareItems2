package com.gmail.ne0nx3r0.rareitems.item.vfx;

import com.gmail.ne0nx3r0.rareitems.RareItems;
import com.gmail.ne0nx3r0.rareitems.item.IPConstantEffect;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class Golden extends IPConstantEffect
{
    public Golden()
    {
        super(42,"Golden","(VFX) Gold particles");
    }
    
    @Override
    public void onEquip(Player p,int level)
    {
        RareItems.ipm.grantPlayerEffect(p.getName(),this,level);
        
        this.addPotionGraphicalEffect((LivingEntity) p,0xFFFE40);
    }
    
    @Override
    public void onUnequip(Player p,int level)
    {
        RareItems.ipm.revokePlayerEffect(p.getName(),this);
        
        this.removePotionGraphicalEffect((LivingEntity) p);
    }
}
