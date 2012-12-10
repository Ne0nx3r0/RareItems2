package com.gmail.ne0nx3r0.rareitems.item;

import com.gmail.ne0nx3r0.rareitems.RareItems;
import com.gmail.ne0nx3r0.utils.MaterialName;
import com.gmail.ne0nx3r0.utils.Namer;
import com.gmail.ne0nx3r0.utils.RomanNumeral;
import java.util.HashMap;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class RareItem
{
    private final int id;
    private final int materialId;
    private final byte dataValue;
    private final HashMap<ItemProperty,Integer> properties;
    private final String owner;
    
    public RareItem(int riId, String owner, int materialId, byte dataValue,HashMap<ItemProperty,Integer> properties)
    {
        this.id = riId;
        this.owner = owner;
        this.materialId = materialId;
        this.dataValue = dataValue;
        this.properties = properties;
    }
    
    public void onInteract(PlayerInteractEvent e)
    {
        for(ItemProperty ip : properties.keySet())
        {
            if(ip.hasCost(e.getPlayer()))
            {
                if(ip.onInteract(e, properties.get(ip)))
                {
                    ip.takeCost(e.getPlayer());
                }
            }
            else
            {
                if(RareItems.COST_TYPE == RareItems.COST_TYPE_FOOD)
                {
                    e.getPlayer().sendMessage("Your food bar is too low to use this!");
                }
                else if(RareItems.COST_TYPE == RareItems.COST_TYPE_XP)
                {
                    e.getPlayer().sendMessage("You need more EXP to use this!");
                }
            }
        }
    }

    public void onInteractEntity(PlayerInteractEntityEvent e)
    {
        for(ItemProperty ip : properties.keySet())
        {
            if(ip.hasCost(e.getPlayer()))
            {
                if(ip.onInteractEntity(e, properties.get(ip)))
                {
                    ip.takeCost(e.getPlayer());
                }
            }
            else
            {
                if(RareItems.COST_TYPE == RareItems.COST_TYPE_FOOD)
                {
                    e.getPlayer().sendMessage("Your food bar is too low to use this!");
                }
                else if(RareItems.COST_TYPE == RareItems.COST_TYPE_XP)
                {
                    e.getPlayer().sendMessage("You need more EXP to use this!");
                }
            }
        }
    }
    
    public void onDamagedOther(final EntityDamageByEntityEvent e)
    {        
        if(e.getDamager() instanceof Player)
        {
            Player p = (Player) e.getDamager();
            

            for(ItemProperty ip : properties.keySet())
            {
                if(ip.hasCost(p))
                {
                    if(ip.onDamagedOther(e, properties.get(ip)))
                    {
                        ip.takeCost(p);
                    }
                }
                else
                {
                    if(RareItems.COST_TYPE == RareItems.COST_TYPE_FOOD)
                    {
                        p.sendMessage("Your food bar is too low to use this skill!");
                    }
                    else if(RareItems.COST_TYPE == RareItems.COST_TYPE_XP)
                    {
                        p.sendMessage("You need more EXP to use this skill!");
                    }
                }
            }
        }
    }

    public void onEquipped(Player p)
    {
        for(ItemProperty ip : properties.keySet())
        {
            ip.onEquip(p, properties.get(ip));
        }
    }    
    
    public void onUnequipped(Player p)
    {
        for(ItemProperty ip : properties.keySet())
        {
            ip.onUnequip(p, properties.get(ip));
        }
    }

    public Integer getId()
    {
        return this.id;
    }

    public String getOwner()
    {
        return this.owner;
    }

    public ItemStack generateItemStack()
    {
        CraftItemStack cssRareItem = new CraftItemStack(Material.getMaterial(materialId));
        
        if(dataValue != 0x0)
        {
            cssRareItem.getData().setData(dataValue);
        }
      
        for(ItemProperty ip : properties.keySet())
        {
            String sLevel = "";
            
            if(properties.get(ip) > 1)
            {
                sLevel = " "+RomanNumeral.convertToRoman(properties.get(ip));
            }

            Namer.addLore(cssRareItem, ip.getName() + sLevel);
        }
        
        Namer.addLore(cssRareItem, RareItems.rig.getRidPrefix()+this.id);
        
        if(cssRareItem.getType().equals(Material.WRITTEN_BOOK))
        {
            Namer.setName(cssRareItem,"Spellbook");
        }
        
        return (ItemStack) cssRareItem;
    }

    public void revokeItemProperties()
    {
        Player p = Bukkit.getPlayer(owner);
        
        for(ItemProperty ip : properties.keySet())
        {
            ip.onUnequip(p, properties.get(ip));
        }
    }

    public String getDisplayName()
    {
        String dispName = MaterialName.getMaterialDisplayName(materialId, dataValue);
        
        for(ItemProperty ip : properties.keySet())
        {
            dispName += " "+ip.getName()+" "+properties.get(ip)+",";
        }
        
        return dispName.substring(0,dispName.length()-1);
    }

    public int getMaterialId()
    {
        return materialId;
    }

    public byte getDataValue()
    {
        return dataValue;
    }

    public HashMap<ItemProperty, Integer> getItemProperties()
    {
        return this.properties;
    }
}
