package com.gmail.ne0nx3r0.rareitems.item;

import com.gmail.ne0nx3r0.rareitems.item.abilities.*;
import com.gmail.ne0nx3r0.rareitems.item.enchantments.*;
import com.gmail.ne0nx3r0.rareitems.item.skills.*;
import com.gmail.ne0nx3r0.rareitems.item.spells.*;
import com.gmail.ne0nx3r0.rareitems.item.vfx.*;
import java.util.HashMap;
import java.util.Map;

public class ItemPropertyManager
{    
    private final HashMap<ItemProperty, HashMap<String,Integer>> activeEffects;
    private final Map<String,Map<ItemProperty,Integer>> playerActiveEffects;
    private final Map<String,ItemProperty> itemProperties;
    private final Map<Integer,ItemProperty> itemPropertiesIdLookup;
    
    public ItemPropertyManager()
    {
        playerActiveEffects = new HashMap<>();
        activeEffects = new HashMap<>();
        itemProperties = new HashMap<>();
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
        
        this.storeItemProperty(new Durability());
        this.storeItemProperty(new Fly());
        this.storeItemProperty(new Hardy());
        this.storeItemProperty(new Regeneration());
        this.storeItemProperty(new Strength());
        this.storeItemProperty(new WaterBreathing());
    }
    
    public boolean playerHasItemProperty(String playerName, int id)
    {
        if(playerActiveEffects.containsKey(playerName))
        {
            return playerActiveEffects.get(playerName).containsKey(itemPropertiesIdLookup.get(id));
        }
        return false;
    }


    public ItemProperty getItemProperty(int itempropertyID)
    {
        return this.itemPropertiesIdLookup.get(itempropertyID);
    }

    private void storeItemProperty(ItemProperty ip)
    {
        itemProperties.put(ip.getName(), ip);
        itemPropertiesIdLookup.put(ip.getId(), ip);
        activeEffects.put(ip, new HashMap<String,Integer>());
    }

    public void grantPlayerEffect(String name, ItemProperty ip, int level)
    {
        if(!playerActiveEffects.containsKey(name))
        {
            playerActiveEffects.put(name, new HashMap<ItemProperty,Integer>());
        }
        
        activeEffects.get(ip).put(name,level);
        playerActiveEffects.get(name).put(ip, level);
    }

    public void revokePlayerEffect(String name, ItemProperty ip)
    {
        if(playerActiveEffects.containsKey(name))
        {
            activeEffects.get(ip).remove(name);
            playerActiveEffects.get(name).remove(ip);
        
            if(playerActiveEffects.get(name).isEmpty())
            {
                playerActiveEffects.remove(name);
            }
        }
    }

    public HashMap<String, Integer> getEffectActivePlayers(ItemProperty ip)
    {
        return this.activeEffects.get(ip);
    }

    public int getPlayerEffectLevel(String playerName, int ipId)
    {
        return this.playerActiveEffects.get(playerName).get(this.itemPropertiesIdLookup.get(ipId));
    }
}
