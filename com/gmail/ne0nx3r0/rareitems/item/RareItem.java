package com.gmail.ne0nx3r0.rareitems.item;

import com.gmail.ne0nx3r0.rareitems.RareItems;
import com.gmail.ne0nx3r0.utils.MaterialName;
import com.gmail.ne0nx3r0.utils.RomanNumeral;
import java.util.ArrayList;
import java.util.HashMap;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class RareItem
{
    private final int id;
    private final int materialId;
    private final byte dataValue;
    private final HashMap<ItemProperty,Integer> properties;
    private final String owner;
    private final String color;
    
    public RareItem(int riId, String owner, int materialId, byte dataValue,HashMap<ItemProperty,Integer> properties)
    {
        this.id = riId;
        this.owner = owner;
        this.materialId = materialId;
        this.dataValue = dataValue;
        this.properties = properties;
        this.color = null;
    }
    
    public RareItem(int riId, String owner, int materialId, byte dataValue,HashMap<ItemProperty,Integer> properties,String color)
    {
        this.id = riId;
        this.owner = owner;
        this.materialId = materialId;
        this.dataValue = dataValue;
        this.properties = properties;
        this.color = color;
    }
    
    public void onInteract(PlayerInteractEvent e)
    {
        for(ItemProperty ip : properties.keySet())
        {
            if(!ip.isEnabled())
            {
                continue;
            }
            
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
                else if(RareItems.COST_TYPE == RareItems.COST_TYPE_MONEY)
                {
                    e.getPlayer().sendMessage("You need more "+RareItems.economy.currencyNamePlural()+" to use this!");
                }
            }
        }
    }

    public void onInteractEntity(PlayerInteractEntityEvent e)
    {
        for(ItemProperty ip : properties.keySet())
        {
            if(!ip.isEnabled())
            {
                continue;
            }
            
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
                else if(RareItems.COST_TYPE == RareItems.COST_TYPE_MONEY)
                {
                    e.getPlayer().sendMessage("You need more "+RareItems.economy.currencyNamePlural()+" to use this skill!");
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
                if(!ip.isEnabled())
                {
                    continue;
                }
                
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
                    else if(RareItems.COST_TYPE == RareItems.COST_TYPE_MONEY)
                    {
                        p.sendMessage("You need more "+RareItems.economy.currencyNamePlural()+" to use this skill!");
                    }
                }
            }
        }
    }

    public void onArrowHitGround(ProjectileHitEvent e,Player shooter)
    {
        for(ItemProperty ip : properties.keySet())
        {
            if(!ip.isEnabled())
            {
                continue;
            }
            
            if(ip.hasCost(shooter))
            {
                if(ip.onArrowHitGround(e,shooter, properties.get(ip)))
                {
                    ip.takeCost(shooter);
                }
            }
            else
            {
                if(RareItems.COST_TYPE == RareItems.COST_TYPE_FOOD)
                {
                    shooter.sendMessage("Your food bar is too low to use this skill!");
                }
                else if(RareItems.COST_TYPE == RareItems.COST_TYPE_XP)
                {
                    shooter.sendMessage("You need more EXP to use this skill!");
                }
                else if(RareItems.COST_TYPE == RareItems.COST_TYPE_MONEY)
                {
                    shooter.sendMessage("You need more "+RareItems.economy.currencyNamePlural()+" to use this skill!");
                }
            }
        }
    }
    
    public void onArrowHitEntity(EntityDamageByEntityEvent e, Player shooter)
    {
        for(ItemProperty ip : properties.keySet())
        {
            if(!ip.isEnabled())
            {
                continue;
            }
            
            if(ip.hasCost(shooter))
            {
                if(ip.onArrowHitEntity(e,shooter, properties.get(ip)))
                {
                    ip.takeCost(shooter);
                }
            }
            else
            {
                if(RareItems.COST_TYPE == RareItems.COST_TYPE_FOOD)
                {
                    shooter.sendMessage("Your food bar is too low to use this skill!");
                }
                else if(RareItems.COST_TYPE == RareItems.COST_TYPE_XP)
                {
                    shooter.sendMessage("You need more EXP to use this skill!");
                }
                else if(RareItems.COST_TYPE == RareItems.COST_TYPE_MONEY)
                {
                    shooter.sendMessage("You need more "+RareItems.economy.currencyNamePlural()+" to use this skill!");
                }
            }
        }
    }
    
    public void onEquipped(Player p)
    {
        for(ItemProperty ip : properties.keySet())
        {
            if(!ip.isEnabled())
            {
                continue;
            }
            
            ip.onEquip(p, properties.get(ip));
        }
    }    
    
    public void onUnequipped(Player p)
    {
        for(ItemProperty ip : properties.keySet())
        {
            if(!ip.isEnabled())
            {
                continue;
            }
            
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
        ItemStack is = new ItemStack(Material.getMaterial(materialId));
        
        if(dataValue != 0x0)
        {
            is.getData().setData(dataValue);
        }
      
        ArrayList<String> lore = new ArrayList<>();
        
        for(ItemProperty ip : properties.keySet())
        {
            String sLevel = "";
            
            if(properties.get(ip) > 1)
            {
                sLevel = " "+RomanNumeral.convertToRoman(properties.get(ip));
            }

            if(!ip.isEnabled())
            {
                lore.add(ChatColor.GRAY+"(Not Allowed) "+ip.getName());
            }
            else
            {
                lore.add(ip.getName() + sLevel);
            }
        }
        
        lore.add(RareItems.RID_PREFIX+this.id);

        ItemMeta im = is.getItemMeta();
        
        if(this.color != null && im instanceof LeatherArmorMeta)
        {
            LeatherArmorMeta lam = (LeatherArmorMeta) im;

            lam.setColor(org.bukkit.Color.fromBGR(
                Integer.valueOf(this.color.substring(0, 2), 16),
                Integer.valueOf(this.color.substring(2, 4), 16), 
                Integer.valueOf(this.color.substring(4, 6), 16)
            ));
        }
        
        if(is.getType().equals(Material.WRITTEN_BOOK))
        {
            im.setDisplayName("Spellbook");
        }

        im.setLore(lore);
        
        is.setItemMeta(im);
        
        

        return is;
    }

    public void revokeItemProperties()
    {
        Player p = Bukkit.getPlayer(owner);
        
        for(ItemProperty ip : properties.keySet())
        {
            if(!ip.isEnabled())
            {
                continue;
            }
            
            ip.onUnequip(p, properties.get(ip));
        }
    }

    public String getDisplayName()
    {
        String dispName = MaterialName.getMaterialDisplayName(materialId, dataValue);
        
        if(properties.isEmpty())
        {
            return dispName +" (Unknown properties)";
        }
        
        for(ItemProperty ip : properties.keySet())
        {
            if(!ip.isEnabled())
            {
                dispName += ChatColor.GRAY+" (Not Allowed) "+ip.getName()+",";
            }
            else
            {
                dispName += " "+ip.getName()+" "+properties.get(ip)+",";
            }
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

    public ArrayList<String> getDescription()
    {
        ArrayList<String> sDescription = new ArrayList<>();
        
        sDescription.add(ChatColor.GOLD+getDisplayName());
        
        for(ItemProperty ip : properties.keySet())
        {
            if(!ip.isEnabled())
            {
                sDescription.add(ChatColor.GRAY+"(Not Allowed) "
                        + ip.getName()+" "+ChatColor.WHITE+ip.getDescription());
            }
            else
            {                
                String sCostString = "";
                
                if(RareItems.COST_TYPE == RareItems.COST_TYPE_FOOD)
                {
                    sCostString = "food";
                }
                else if(RareItems.COST_TYPE == RareItems.COST_TYPE_XP)
                {
                    sCostString = "exp";
                }
                else if(RareItems.COST_TYPE == RareItems.COST_TYPE_MONEY)
                {
                    sCostString = RareItems.economy.currencyNamePlural();
                }
                
                sDescription.add(ChatColor.LIGHT_PURPLE + ip.getName()
                        + " (Costs " + ip.getCost() +sCostString+"): "
                        + ChatColor.WHITE + ip.getDescription());
            }
        }
        
        return sDescription;
    }
}
