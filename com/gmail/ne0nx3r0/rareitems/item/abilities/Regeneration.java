package com.gmail.ne0nx3r0.rareitems.item.abilities;

import com.gmail.ne0nx3r0.rareitems.item.IPConstantEffect;
import org.bukkit.entity.Player;

public class Regeneration extends IPConstantEffect
{    
    public Regeneration()
    {
        super(10,"Regeneration","Regenerate 1 HP / lvl / 10 seconds");
        
        this.createRepeatingAppliedEffect(this,20*10);
    }

    @Override
    public void applyEffectToPlayer(Player p,int level)
    {
        if(p.getHealth() < 20)
        {
            int iNewHP = p.getHealth() + level * 1;
            
            if(iNewHP > 20)
            {
                iNewHP = 20;
            }
            
            p.setHealth(iNewHP);
        }
    }
}
