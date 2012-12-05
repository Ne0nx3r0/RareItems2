package com.gmail.ne0nx3r0.rareitems.item.vfx;

import com.gmail.ne0nx3r0.rareitems.item.IPConstantEffect;
import org.bukkit.Effect;
import org.bukkit.entity.Player;

public class EnderField extends IPConstantEffect
{
    public EnderField()
    {
        super(14,"Ender Field","Creates an ender signal wherever you go.");
        
        this.createRepeatingAppliedEffect(this,20);
    }

    @Override
    public void applyEffectToPlayer(Player p,int level)
    {
        p.getWorld().playEffect(p.getLocation(), Effect.ENDER_SIGNAL, 5, 20);
    }    
}
