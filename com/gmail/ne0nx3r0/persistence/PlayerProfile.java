package com.gmail.ne0nx3r0.persistence;

import com.gmail.ne0nx3r0.rareitems.RareItems;
import com.gmail.ne0nx3r0.rareitems.item.RareItem;
import com.gmail.ne0nx3r0.utils.Namer;
import java.util.HashMap;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class PlayerProfile
{
    private final String username;
    private final int siteId;
    private int money;
    private HashMap<Integer,RareItem> rareItems;//rid
    private HashMap<Integer,Integer[]> checkouts;

    public PlayerProfile(String username, int siteId)
    {
        this.username = username;
        this.siteId = siteId;
        this.money = 0;
        
        this.rareItems = new HashMap<>();
        this.checkouts = new HashMap<>();
    }

    PlayerProfile(String username, int siteId, int money,HashMap<Integer, RareItem> rareItems, HashMap<Integer, Integer[]> checkedOutRareItems)
    {
        this.username = username;
        this.siteId = siteId;
        this.money = money;
        this.rareItems = rareItems;
        this.checkouts = checkedOutRareItems;
    }

    public String getName()
    {
        return this.username;
    }

    public void addRareItem(RareItem ri)
    {
        this.rareItems.put(ri.getId(), ri);
    }

    public RareItem getRareItem(ItemStack is,boolean includeInactive)
    {
        String packageName = RareItems.self.getServer().getClass().getPackage().getName();
        String version = packageName.substring(packageName.lastIndexOf('.') + 1);

        if (version.equals("craftbukkit"))
        {
            int rid = Namer.getRid(is);
            
            if(rareItems.containsKey(rid)
            && (this.isCheckedOut(rid) || includeInactive))
            {
                return rareItems.get(rid);
            }
        }
        else
        {
            if(is != null 
            && is.getType() != Material.AIR 
            && is.getItemMeta() != null)
            {
                List<String> lore = is.getItemMeta().getLore();

                if(lore != null && lore.size() > 0)
                {
                    String sRIDString = lore.get(lore.size()-1);

                    if(sRIDString.startsWith(RareItems.RID_PREFIX))
                    {
                        int rid = Integer.parseInt(sRIDString.substring(RareItems.RID_PREFIX.length()));

                        if(rareItems.containsKey(rid)
                        && (this.isCheckedOut(rid) || includeInactive))
                        {
                            return rareItems.get(rid);
                        }
                    }
                }
            }
        }
  
        
        
        return null;
    }

    RareItem getRareItem(int rid, boolean includeInactive)
    {
        if(rareItems.containsKey(rid)
        && (this.isCheckedOut(rid) || includeInactive))
        {
            return rareItems.get(rid);
        }
        
        return null;
    }

    public boolean isCheckedOut(int rid)
    {
        return checkouts.containsKey(rid);
    }

    public void revokeAllItemProperties()
    {
        for(RareItem ri : rareItems.values())
        {
            ri.revokeItemProperties();
        }
    }
    
    public void refreshArmor()
    {
        Player p = Bukkit.getPlayer(username);
        
        revokeAllItemProperties();
        
        if(p != null)
        {
            if(p.getInventory().getArmorContents().length > 0)
            {
                ItemStack[] armor = p.getInventory().getArmorContents();

                for(int i=0;i<armor.length;i++)
                {
                    RareItem ri = getRareItem(armor[i],false);

                    if(ri != null)
                    {
                        ri.onEquipped(p);
                    }
                }
            }
        }
    }

    void fillWithCheckedInItems(Inventory inv)
    {
        for(RareItem ri : rareItems.values())
        {
            if(!isCheckedOut(ri))
            {
                inv.addItem(ri.generateItemStack());
            }
        }
    }

    boolean checkOut(RareItem ri)
    {
        if(this.checkouts.size() <= RareItems.MAX_CHECKED_OUT_ITEMS)
        {
            if(this.rareItems.containsKey(ri.getId()))
            {
                this.checkouts.put(ri.getId(), new Integer[]{
                    ri.getMaterialId(),
                    ((Byte) ri.getDataValue()).intValue()
                });
                
                RareItems.pm.savePlayerProfile(this);
                
                return true;
            }
        }
        
        return false;
    }

    boolean checkIn(RareItem ri)
    {
        return checkIn(ri.getId());
    }

    boolean checkIn(int rid)
    {
        if(checkouts.containsKey(rid))
        {
            checkouts.remove(rid);
                
            RareItems.pm.savePlayerProfile(this);
            
            return true;
        }
        return false;
    }

    public boolean isCheckedOut(RareItem ri)
    {
        return this.checkouts.containsKey(ri.getId());
    }

    public void removeAllRareItems()
    {
        this.rareItems = new HashMap<>();
    }

    int getSiteId()
    {
        return this.siteId;
    }

    HashMap<Integer,Integer[]> getCheckedOutItems()
    {
        return this.checkouts;
    }

    HashMap<Integer,RareItem> getRareItems()
    {
        return this.rareItems;
    }

    Integer[] getCheckedOutRareItemData(int rid)
    {
        return this.checkouts.get(rid);
    }

    public int getMoney()
    {
        return this.money;
    }

    public void setMoney(int amount)
    {
        this.money = amount;
    }
}
