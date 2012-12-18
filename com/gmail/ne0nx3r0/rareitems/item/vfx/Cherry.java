package com.gmail.ne0nx3r0.rareitems.item.vfx;

import com.gmail.ne0nx3r0.rareitems.item.IPConstantEffect;
import org.bukkit.entity.Player;

public class Cherry extends IPConstantEffect
{
    public Cherry()
    {
        super(43,"Cherry","Tastes delicious!");
        
        this.createRepeatingAppliedEffect(this,20*60);
    }

    @Override
    public void applyEffectToPlayer(Player p,int level)
    {
        this.addPotionGraphicalEffect(p, 0xF7022A, 20*60);
    }
}
