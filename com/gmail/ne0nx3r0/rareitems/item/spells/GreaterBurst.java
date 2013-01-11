package com.gmail.ne0nx3r0.rareitems.item.spells;

import com.gmail.ne0nx3r0.rareitems.RareItems;
import com.gmail.ne0nx3r0.rareitems.item.ItemProperty;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

public class GreaterBurst extends ItemProperty
{
    public GreaterBurst()
    {
        super(52,"Greater Burst","Forcibly shoves all nearby creatures away",8);
    }
    
    @Override
    public boolean onDamagedOther(EntityDamageByEntityEvent e, int level)
    {
        return this.activate((Player) e.getDamager(),level);
    }
    
    @Override
    public boolean onInteract(PlayerInteractEvent e,int level)
    {
        return this.activate(e.getPlayer(),level);
    }

    private boolean activate(Player player,int level)
    {
        List<Entity> nearbyEntities = player.getNearbyEntities(8, 8, 8);
        
        if(!nearbyEntities.isEmpty())
        {
            Vector vPlayer = player.getLocation().toVector();
            
            for(Entity ent : nearbyEntities)
            {
                if(ent instanceof LivingEntity)
                {
                    Vector unitVector = ent.getLocation().toVector().subtract(vPlayer).normalize();

                    unitVector.setY(0.55/level);

                    ent.setVelocity(unitVector.multiply(level*2));
                }
            }
            
            try
            {
                RareItems.fireworks.playFirework(
                    player.getWorld(), player.getLocation(),
                    FireworkEffect
                        .builder()
                        .with(Type.BALL_LARGE)
                        .withColor(Color.WHITE)
                        .build()
                );
            }
            catch (Exception ex)
            {
                player.sendMessage(ChatColor.RED+"An error occurred while executing this RareItem's power.");
                Logger.getLogger(GreaterBurst.class.getName()).log(Level.SEVERE, null, ex);
            }

            return true;
        }
        
        player.sendMessage(ChatColor.RED+"No nearby targets.");
        
        return false;
    }
}