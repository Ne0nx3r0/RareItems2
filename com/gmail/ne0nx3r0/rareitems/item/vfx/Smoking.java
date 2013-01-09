package com.gmail.ne0nx3r0.rareitems.item.vfx;

import com.gmail.ne0nx3r0.rareitems.item.IPConstantEffect;
import org.bukkit.Effect;
import org.bukkit.entity.Player;

public class Smoking extends IPConstantEffect
{
    public Smoking()
    {
        super(13,"Smoking","(VFX) Ring of smoke");
        
        this.createRepeatingAppliedEffect(this,20);
    }

    @Override
    public void applyEffectToPlayer(Player p,int level)
    {
        for (int i = 0; i < 9; i++)
        {
            if(i != 4)
            {
                p.getWorld().playEffect(p.getLocation(), Effect.SMOKE, i);
            }
        }
    }    
}
