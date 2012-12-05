package com.gmail.ne0nx3r0.rareitems.item.abilities;

import com.gmail.ne0nx3r0.rareitems.RareItems;
import com.gmail.ne0nx3r0.rareitems.item.IPConstantEffect;
import com.gmail.ne0nx3r0.rareitems.item.ItemProperty;
import java.util.HashMap;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Fly extends IPConstantEffect
{
    public Fly()
    {
        super(8,"Fly","You can fly.");
        
        this.createRepeatingAppliedEffect(this,20*5);
        
        final ItemProperty ip = this;
        Bukkit.getScheduler().scheduleSyncRepeatingTask(RareItems.self,  new Runnable()
        {
            @Override
            public void run()
            {
                HashMap<String, Integer> activePlayers = RareItems.rig.getEffectActivePlayers(ip);
                for(String sPlayer : activePlayers.keySet())
                {
                    Player p = Bukkit.getPlayer(sPlayer);
                    
                    if(p.isFlying() && p.getFoodLevel() == 0)
                    {
                        p.setFlying(false);
                    }
                }
            }
        }, 20, 20);
    }

    @Override
    public void onEquip(Player p,int level)
    {
        RareItems.rig.grantPlayerEffect(p.getName(),this,level);
        p.setAllowFlight(true);
    }

    @Override
    public void onUnequip(Player p,int level)
    {
        RareItems.rig.revokePlayerEffect(p.getName(),this);
        p.setFlying(false);
        p.setAllowFlight(false);
    }
    
    @Override
    public void applyEffectToPlayer(Player p, int level)
    {
        if(p.isFlying())
        {
            if(p.getFoodLevel() > 0)
            {
                int iNewFoodLevel = p.getFoodLevel() - 5 + level;
                
                if(iNewFoodLevel < 0)
                {
                    iNewFoodLevel = 0;
                }
                
                p.setFoodLevel(iNewFoodLevel);
            }
            else
            {
                p.setFlying(false);
            }
        }
    }
}
