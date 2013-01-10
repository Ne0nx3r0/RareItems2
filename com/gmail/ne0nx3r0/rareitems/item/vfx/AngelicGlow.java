package com.gmail.ne0nx3r0.rareitems.item.vfx;

import com.gmail.ne0nx3r0.rareitems.item.IPConstantEffect;
import org.bukkit.entity.Player;

public class AngelicGlow extends IPConstantEffect
{
    public AngelicGlow()
    {
        super(12,"Angelic Glow","(VFX) White particles");
        
        this.createRepeatingAppliedEffect(this,20*5);
    }

    @Override
    public void applyEffectToPlayer(Player p,int level)
    {
        this.addPotionGraphicalEffect(p, 0xffffff, 20*6);
    }
}
