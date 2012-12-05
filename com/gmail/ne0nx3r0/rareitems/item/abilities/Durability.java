package com.gmail.ne0nx3r0.rareitems.item.abilities;

import com.gmail.ne0nx3r0.rareitems.item.IPConstantEffect;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Durability extends IPConstantEffect
{
    
    public Durability()
    {
        super(9,"Durability","Automatically repairs your armor over time.");
        
        this.createRepeatingAppliedEffect(this,20*10);
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
