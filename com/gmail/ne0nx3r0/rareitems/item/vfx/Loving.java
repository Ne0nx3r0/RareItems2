package com.gmail.ne0nx3r0.rareitems.item.vfx;

import com.gmail.ne0nx3r0.rareitems.RareItems;
import com.gmail.ne0nx3r0.rareitems.item.IPConstantEffect;
import com.gmail.ne0nx3r0.rareitems.item.ItemProperty;
import org.bukkit.EntityEffect;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Loving extends IPConstantEffect
{
    public Loving()
    {
        super(48,"Loving","(VFX) Hearts");
        
        this.createRepeatingAppliedEffect((ItemProperty) this,20*3);
    }

    @Override
    public void applyEffectToPlayer(final Player p,int level)
    {
        Location l = p.getLocation();
        l.setY(400);
        final LivingEntity kludgeE = (LivingEntity) p.getWorld().spawnEntity(l, EntityType.WOLF);
        kludgeE.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY,100,100));
        kludgeE.teleport(p.getLocation());
      
        RareItems.self.getServer().getScheduler().runTaskLater(RareItems.self,  new Runnable() {
            @Override  
            public void run()
            {
                kludgeE.playEffect(EntityEffect.WOLF_HEARTS);
                kludgeE.remove();
            }
        },3);
    }
}
