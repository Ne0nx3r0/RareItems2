package com.gmail.ne0nx3r0.rareitems.item.vfx;

import com.gmail.ne0nx3r0.rareitems.item.IPConstantEffect;
import org.bukkit.entity.Player;

public class Smelly extends IPConstantEffect
{
    public Smelly()
    {
        super(41,"Smelly","(VFX) Puke green particles");
        
        this.createRepeatingAppliedEffect(this,20*5);
    }

    @Override
    public void applyEffectToPlayer(Player p,int level)
    {
        this.addPotionGraphicalEffect(p, 0x9AAE07, 20*6);
    }
}
