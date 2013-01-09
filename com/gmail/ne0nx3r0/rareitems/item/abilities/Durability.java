package com.gmail.ne0nx3r0.rareitems.item.abilities;

import com.gmail.ne0nx3r0.rareitems.item.IPConstantEffect;
import com.gmail.ne0nx3r0.rareitems.item.ItemProperty;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Durability extends IPConstantEffect
{
    
    public Durability()
    {
        super(9,"Durability","Automagically repairs equipped armor over time");
        
        this.createRepeatingAppliedEffect((ItemProperty) this,20*10);
    }

    @Override
    public void applyEffectToPlayer(Player player, int level)
    {
        ItemStack[] armor = player.getInventory().getArmorContents();
        
        for(int i = 0; i < armor.length; i++)
        {
            if(armor[i].getDurability() > 0)
            {
                int iNewArmor = armor[i].getDurability() - 1 * level;
                
                if(iNewArmor < 0)
                {
                    iNewArmor = 0;
                }
                
                armor[i].setDurability((short) iNewArmor);
            }
        }
    }
}
