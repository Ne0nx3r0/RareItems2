package com.gmail.ne0nx3r0.rareitems.item.abilities;

import com.gmail.ne0nx3r0.rareitems.RareItems;
import com.gmail.ne0nx3r0.rareitems.item.IPConstantEffect;
import com.gmail.ne0nx3r0.rareitems.item.ItemProperty;
import java.util.HashMap;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Fly extends IPConstantEffect
{
    public int counter = 1;
    
    public Fly()
    {
        super(8,"Fly","Allows flight, costs Food/XP",1);
        
        this.createRepeatingAppliedEffect((ItemProperty) this,20*5);
        
        final ItemProperty ip = this;
        Bukkit.getScheduler().scheduleSyncRepeatingTask(RareItems.self,  new Runnable()
        {
            @Override
            public void run()
            {
                HashMap<String, Integer> activePlayers = RareItems.ipm.getEffectActivePlayers(ip);
                for(String sPlayer : activePlayers.keySet())
                {
                    Player p = Bukkit.getPlayer(sPlayer);
                    
                    if(p.isFlying() && !ip.hasCost(p))
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
        RareItems.ipm.grantPlayerEffect(p.getName(),this,level);
        p.setAllowFlight(true);
    }

    @Override
    public void onUnequip(Player p,int level)
    {
        RareItems.ipm.revokePlayerEffect(p.getName(),this);
        p.setFlying(false);
        p.setAllowFlight(false);
    }
    
    @Override
    public void applyEffectToPlayer(Player p, int level)
    {
        if(p.isFlying())
        {
            if(this.hasCost(p))
            { 
                if(level <= counter)
                {
                    this.takeCost(p);
                }
            }
            else
            {
                p.setFlying(false);
            }
        }

        if(counter == 5)
        {
            counter = 1;
        }
        else 
        {
            counter++;
        }
    }
}
