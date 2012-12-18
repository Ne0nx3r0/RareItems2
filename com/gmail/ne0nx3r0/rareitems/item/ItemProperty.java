package com.gmail.ne0nx3r0.rareitems.item;

import com.gmail.ne0nx3r0.rareitems.RareItems;
import java.util.HashMap;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class ItemProperty
{
    private final int id;
    private final String name;
    private final String description;
    private final int cost;
    
    public ItemProperty(int id,String name,String description,int cost)
    {
        this.id = id;
        this.name = name;
        this.description = description;
        this.cost = cost;
    }
    
    public void applyEffectToPlayer(Player p,int level){}
    
    public boolean onInteract(PlayerInteractEvent e,int level){return false;}

    public boolean onInteractEntity(PlayerInteractEntityEvent e, Integer level){return false;}
    
    public boolean onDamagedOther(EntityDamageByEntityEvent e, int level){return false;}
    
    public void onEquip(Player p,int level){}
    
    public void onUnequip(Player p,int level){}

    int getCost()
    {
        return cost * RareItems.COST_MULTIPLIER;
    }

    public String getName()
    {
        return name;
    }

    public Integer getId()
    {
        return id;
    }

    public int addPotionGraphicalEffect(LivingEntity entity, int color, int duration)
    {
        String packageName = RareItems.self.getServer().getClass().getPackage().getName();
        String version = packageName.substring(packageName.lastIndexOf('.') + 1);

        if (version.equals("craftbukkit"))
        {
            return com.gmail.ne0nx3r0.api.v1_4_5_pre.PotionVisual
                 .addPotionGraphicalEffect(entity, color, duration);
        }
        else if(version.equals("v1_4_5"))
        {
            return com.gmail.ne0nx3r0.api.v1_4_5.PotionVisual
                .addPotionGraphicalEffect(entity, color, duration);
        }

        return -1;
    }
    
    public void createRepeatingAppliedEffect(final ItemProperty ip,int duration)
    {        
        Bukkit.getServer().getScheduler().scheduleAsyncRepeatingTask(RareItems.self, new Runnable()
        {
            @Override
            public void run()
            {
                HashMap<String, Integer> activePlayers = RareItems.ipm.getEffectActivePlayers(ip);
                for(String sPlayer : activePlayers.keySet())
                {
                    Player p = Bukkit.getPlayer(sPlayer);
                    
                    if(p != null)
                    {
                        ip.applyEffectToPlayer(p,activePlayers.get(p.getName()));
                    }
                    else
                    {
                        RareItems.ipm.revokePlayerEffect(sPlayer,ip);
                    }
                }
            }
        }, duration, duration);
    }
    
    public boolean hasCost(Player p)
    {
        if(RareItems.COST_TYPE == RareItems.COST_TYPE_FOOD)
        {
            if(p.getFoodLevel() >= cost * RareItems.COST_MULTIPLIER)
            {
                return true;
            }
        }        
        if(RareItems.COST_TYPE == RareItems.COST_TYPE_XP)
        {
            if(p.getExp() >= cost * RareItems.COST_MULTIPLIER)
            {
                return true;
            }
        }
        return false;
    }
    
    public void takeCost(Player p)
    {
        if(RareItems.COST_TYPE == RareItems.COST_TYPE_FOOD)
        {
            p.setFoodLevel(p.getFoodLevel() - cost * RareItems.COST_MULTIPLIER);
        }        
        if(RareItems.COST_TYPE == RareItems.COST_TYPE_XP)
        {
            p.setExp(p.getExp() - cost * RareItems.COST_MULTIPLIER);
        }
    }
}
