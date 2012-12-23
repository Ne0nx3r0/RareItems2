package com.gmail.ne0nx3r0.rareitems.item.enchantments;

import com.gmail.ne0nx3r0.rareitems.item.ItemProperty;
import java.util.Random;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class HalfBakedIdea extends ItemProperty
{
    public HalfBakedIdea()
    {
        super(39,"Half-Baked Idea","To the lab mouse, life is a confusing array of cheese and electricity",4);
    }
    
    @Override
    public boolean onInteract(PlayerInteractEvent e,int level)
    {
        Random r = new Random();
        if(r.nextBoolean())
        {
            PotionEffectType[] potionEffects = PotionEffectType.values();

            e.getPlayer().addPotionEffect(new PotionEffect(potionEffects[r.nextInt(potionEffects.length)],20*60,1));

            e.getPlayer().sendMessage("Holy crap something happened!");
        
            return true;
        }
        else
        {        
            e.getPlayer().sendMessage("Holy crap something happened!");
            
            return false;
        }
    }
}