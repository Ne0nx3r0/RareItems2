package com.gmail.ne0nx3r0.rareitems.item;

import com.gmail.ne0nx3r0.rareitems.RareItems;
import com.gmail.ne0nx3r0.rareitems.item.abilities.*;
import com.gmail.ne0nx3r0.rareitems.item.bows.*;
import com.gmail.ne0nx3r0.rareitems.item.enchantments.*;
import com.gmail.ne0nx3r0.rareitems.item.skills.*;
import com.gmail.ne0nx3r0.rareitems.item.spells.*;
import com.gmail.ne0nx3r0.rareitems.item.vfx.*;
import java.util.HashMap;
import java.util.Map;

public class ItemPropertyManager
{    
    private final HashMap<ItemProperty, HashMap<String,Integer>> activeEffects;
    
    //playerNameLowerCase,itemPropertyId,itemPropertyPlayerLevel
    private final Map<String,Map<Integer,Integer>> playerActiveEffects;
    
    //playerNameLowerCase,itemPropertyId,removalTaskId
    private final Map<String,Map<Integer,Integer>> tempEffectIds;
            
    private final Map<String,ItemProperty> itemPropertiesLookup;
    private final Map<Integer,ItemProperty> itemPropertiesIdLookup;
    
    public ItemPropertyManager()
    {
        playerActiveEffects = new HashMap<>();
        tempEffectIds = new HashMap<>();
        activeEffects = new HashMap<>();
        itemPropertiesLookup = new HashMap<>();
        itemPropertiesIdLookup = new HashMap<>();
        
        this.storeItemProperty(new Fertilize());
        this.storeItemProperty(new Smelt());
        this.storeItemProperty(new Spore());
        this.storeItemProperty(new FireHandling());
        this.storeItemProperty(new MeltObsidian());
        this.storeItemProperty(new PaintWool());
        this.storeItemProperty(new HalfBakedIdea());
        
        this.storeItemProperty(new Backstab());
        this.storeItemProperty(new CallLightning());
        this.storeItemProperty(new Confuse());
        this.storeItemProperty(new Poison());
        this.storeItemProperty(new Slow());
        this.storeItemProperty(new VampiricRegeneration());
        this.storeItemProperty(new Weaken());
        
        this.storeItemProperty(new CraftItem());
        this.storeItemProperty(new FireResistance());
        this.storeItemProperty(new GrowTree());
        this.storeItemProperty(new Haste());
        this.storeItemProperty(new MagicBag());
        this.storeItemProperty(new RepairItem());
        this.storeItemProperty(new SummonBat());
        this.storeItemProperty(new SummonChicken());
        this.storeItemProperty(new SummonCow());
        this.storeItemProperty(new SummonMooshroom());
        this.storeItemProperty(new SummonOcelot());
        this.storeItemProperty(new SummonSheep());
        this.storeItemProperty(new SummonSlime());
        this.storeItemProperty(new SummonPig());
        this.storeItemProperty(new Invisibility());
        this.storeItemProperty(new CatsFeet());
        
        this.storeItemProperty(new AngelicGlow());
        this.storeItemProperty(new Burning());
        this.storeItemProperty(new EnderField());
        this.storeItemProperty(new Smoking());
        this.storeItemProperty(new Golden());
        this.storeItemProperty(new Smelly());
        this.storeItemProperty(new Cherry());
        
        this.storeItemProperty(new Durability());
        this.storeItemProperty(new Fly());
        this.storeItemProperty(new Hardy());
        this.storeItemProperty(new Regeneration());
        this.storeItemProperty(new Strength());
        this.storeItemProperty(new WaterBreathing());
        
        this.storeItemProperty(new FireArrow());
        this.storeItemProperty(new Hookshot());
    }
    
    public boolean playerHasItemProperty(String playerName, int id)
    {
        if(playerActiveEffects.containsKey(playerName))
        {
            return playerActiveEffects.get(playerName).containsKey(id);
        }
        return false;
    }


    public ItemProperty getItemProperty(int itempropertyID)
    {
        return this.itemPropertiesIdLookup.get(itempropertyID);
    }

    private void storeItemProperty(ItemProperty ip)
    {
        itemPropertiesLookup.put(ip.getName(), ip);
        itemPropertiesIdLookup.put(ip.getId(), ip);
        activeEffects.put(ip, new HashMap<String,Integer>());
    }

    public void grantPlayerEffect(String name, ItemProperty ip, int level)
    {
        if(!playerActiveEffects.containsKey(name))
        {
            playerActiveEffects.put(name, new HashMap<Integer,Integer>());
        }
        
        activeEffects.get(ip).put(name,level);
        playerActiveEffects.get(name).put(ip.getId(), level);
    }

    public void revokePlayerEffect(String playerName, int ipId)
    {
        revokePlayerEffect(playerName,itemPropertiesIdLookup.get(ipId));
    }

    public void revokePlayerEffect(String playerName, ItemProperty ip)
    {
        if(tempEffectIds.containsKey(playerName)
        && tempEffectIds.get(playerName).containsKey(ip.getId()))
        {
            tempEffectIds.get(playerName).remove(ip.getId());
        }
        
        if(playerActiveEffects.containsKey(playerName))
        {
            activeEffects.get(ip).remove(playerName);
            playerActiveEffects.get(playerName).remove(ip.getId());
        
            if(playerActiveEffects.get(playerName).isEmpty())
            {
                playerActiveEffects.remove(playerName);
            }
        }    
    }

    public HashMap<String, Integer> getEffectActivePlayers(ItemProperty ip)
    {
        return this.activeEffects.get(ip);
    }

    public int getPlayerEffectLevel(String playerName, int ipId)
    {
        return this.playerActiveEffects.get(playerName).get(ipId);
    }

    public void addTemporaryEffect(final String sPlayerName, ItemProperty ip, Integer level, int duration)
    {
        final int ipId = ip.getId();

        if(!tempEffectIds.containsKey(sPlayerName))
        {
            tempEffectIds.put(sPlayerName, new HashMap<Integer,Integer>());
        }
        else if(tempEffectIds.get(sPlayerName).containsKey(ip.getId()))
        {
            RareItems.self.getServer().getScheduler().cancelTask(
                tempEffectIds.get(sPlayerName).remove(ip.getId())
            );
        }
        
        tempEffectIds.get(sPlayerName).put(ip.getId(),RareItems.self.getServer().getScheduler()
            .scheduleSyncDelayedTask(RareItems.self,new Runnable(){
                @Override 
                public void run(){
                    RareItems.ipm.revokePlayerEffect(sPlayerName, ipId);
                }
            }, duration));
        
        RareItems.ipm.grantPlayerEffect(sPlayerName, ip, level);
    }
}
