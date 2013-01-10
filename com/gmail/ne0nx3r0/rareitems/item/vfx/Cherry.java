package com.gmail.ne0nx3r0.rareitems.item.vfx;

import com.gmail.ne0nx3r0.rareitems.item.IPConstantEffect;
import com.gmail.ne0nx3r0.rareitems.item.ItemProperty;
import org.bukkit.entity.Player;

public class Cherry extends IPConstantEffect
{
    public Cherry()
    {
        super(43,"Cherry","(VFX) Red particles");
        
        this.createRepeatingAppliedEffect((ItemProperty) this,20*5);
    }

    @Override
    public void applyEffectToPlayer(Player p,int level)
    {
        this.addPotionGraphicalEffect(p, 0xF7022A, 20*6);
    }
}
