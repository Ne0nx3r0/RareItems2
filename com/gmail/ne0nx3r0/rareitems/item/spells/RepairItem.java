package com.gmail.ne0nx3r0.rareitems.item.spells;

import com.gmail.ne0nx3r0.rareitems.item.ItemProperty;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class RepairItem extends ItemProperty
{
    public RepairItem()
    {
        super(29,"Repair Item","Repairs the first item in your inventory bar",15);
    }
    
    @Override
    public boolean onInteract(PlayerInteractEvent e,int level)
    {
        ItemStack isSlotOne = e.getPlayer().getInventory().getItem(0);
        
        if(isSlotOne.getType().getMaxDurability() > 20)
        {
            short sDurability = (short) (isSlotOne.getDurability() - 8 * level);
            
            if(sDurability < 0)
            {
                sDurability = 0;
            }
            
            isSlotOne.setDurability(sDurability);
            
            e.getPlayer().sendMessage("Item repaired!");
            
            return true;
        }
        else
        {
            e.getPlayer().sendMessage("Item in slot #1 is not repairable!");
        }
        
        return false;
    }
    
    @Override
    public boolean onInteractEntity(PlayerInteractEntityEvent e, int level)
    {
        if(e.getRightClicked() instanceof Player)
        {
            Player pClicked = (Player) e.getRightClicked();
            ItemStack isSlotOne = pClicked.getItemInHand();

            if(isSlotOne.getType().getMaxDurability() > 20)
            {
                short sDurability = (short) (isSlotOne.getDurability() - 8 * level);

                if(sDurability < 0)
                {
                    sDurability = 0;
                }

                isSlotOne.setDurability(sDurability);

                e.getPlayer().sendMessage("Item in "+pClicked.getName()+"'s hand repaired!");

                return true;
            }
            else
            {
                e.getPlayer().sendMessage("Item in "+pClicked.getName()+"'s hand is not repairable!");
            }
            
            return true;
        }
        return false;
    }
}