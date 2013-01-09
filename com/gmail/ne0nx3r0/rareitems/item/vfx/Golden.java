package com.gmail.ne0nx3r0.rareitems.item.vfx;

import com.gmail.ne0nx3r0.rareitems.item.IPConstantEffect;
import org.bukkit.entity.Player;

public class Golden extends IPConstantEffect
{
    public Golden()
    {
        super(42,"Golden","(VFX) Gold particles");
        
        this.createRepeatingAppliedEffect(this,20*60);
    }

    @Override
    public void applyEffectToPlayer(Player p,int level)
    {
        this.addPotionGraphicalEffect(p, 0xFFFE40, 20*60);
    }
}
