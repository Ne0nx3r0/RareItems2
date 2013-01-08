package com.gmail.ne0nx3r0.rareitems.item.skills;

import com.gmail.ne0nx3r0.rareitems.item.ItemProperty;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

public class Disarm extends ItemProperty
{
    private List<Material> disarmables;
    
    public Disarm()
    {
        super(50,"Disarm","Forces an opponent to put away their actively held weapon. (20% chance onhit)",4);
        
        disarmables = new ArrayList<Material>(){};
        disarmables.add(Material.WOOD_SWORD);
        disarmables.add(Material.STONE_SWORD);
        disarmables.add(Material.IRON_SWORD);
        disarmables.add(Material.GOLD_SWORD);
        disarmables.add(Material.DIAMOND_SWORD);
        disarmables.add(Material.STONE_AXE);
        disarmables.add(Material.IRON_AXE);
        disarmables.add(Material.GOLD_AXE);
        disarmables.add(Material.DIAMOND_AXE);
        disarmables.add(Material.BOW);
    }
    
    @Override
    public boolean onDamagedOther(final EntityDamageByEntityEvent e,int level)
    {
        if(new Random().nextInt(5) == 0
        && e.getEntity() instanceof Player)
        {
            Player attacker = (Player) e.getDamager();
            Player p = (Player) e.getEntity();
            
            if(p.getItemInHand() != null
            && disarmables.contains(p.getItemInHand().getType()))
            {
                int iRandomSlot = (new Random()).nextInt(44)+9;

                ItemStack swapOut = p.getInventory().getItem(p.getInventory().getHeldItemSlot());
                ItemStack swapIn = p.getInventory().getItem(iRandomSlot);
                
                p.getInventory().setItem(p.getInventory().getHeldItemSlot(), swapIn);
                p.getInventory().setItem(iRandomSlot, swapOut);
                
                attacker.sendMessage("Disarmed!");
                
                p.sendMessage("You have been disarmed!");

                return true;
            }
        }
        
        return false;
    }
}