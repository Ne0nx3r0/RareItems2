package com.gmail.ne0nx3r0.rareitems.item.vfx;

import com.gmail.ne0nx3r0.rareitems.item.IPConstantEffect;
import org.bukkit.Effect;
import org.bukkit.entity.Player;

 public class Burning extends IPConstantEffect
{
    public Burning()
    {
        super(11,"Burning","(VFX) Flames");
        
        this.createRepeatingAppliedEffect(this,20);
    }

    @Override
    public void applyEffectToPlayer(Player p,int level)
    {
        p.getWorld().playEffect(p.getLocation(), Effect.MOBSPAWNER_FLAMES, 5, 20);
    }    
}
