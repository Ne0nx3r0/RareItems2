package com.gmail.ne0nx3r0.persistence;

import com.gmail.ne0nx3r0.rareitems.RareItems;
import com.gmail.ne0nx3r0.rareitems.http.ApiMessenger;
import com.gmail.ne0nx3r0.rareitems.item.ItemProperty;
import com.gmail.ne0nx3r0.rareitems.item.RareItem;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.craftbukkit.libs.com.google.gson.Gson;
import org.bukkit.craftbukkit.libs.com.google.gson.GsonBuilder;
import org.bukkit.craftbukkit.libs.com.google.gson.JsonSyntaxException;
import org.bukkit.craftbukkit.libs.com.google.gson.JsonParseException;

public class ProfileManager
{
    private HashMap<String,PlayerProfile> playerProfiles;
    private final File PROFILE_DIRECTORY;
    
    public ProfileManager()
    {
        playerProfiles = new HashMap<>();

        PROFILE_DIRECTORY = new File(RareItems.self.getDataFolder(),"players");
        
        if(!PROFILE_DIRECTORY.exists())
        {
            PROFILE_DIRECTORY.mkdirs();
        }
    }
    
    public void loadPlayerProfile(Player p)
    {
        String sPlayerName = p.getName().toLowerCase();
        
        File playerProfile = new File(PROFILE_DIRECTORY,sPlayerName+".yml");

        if(playerProfile.exists())
        {
            File ymlFile = new File(PROFILE_DIRECTORY,sPlayerName+".yml");

            if(!ymlFile.exists())
            {
                try
                {
                    ymlFile.createNewFile();
                }
                catch(IOException ex)
                {
                    RareItems.logger.log(Level.INFO, "Could not create {0}", ymlFile.getName());
                }
            }

            FileConfiguration yml = YamlConfiguration.loadConfiguration(ymlFile);
            /*
            siteId: 1
            checkedOut:
            75:
            - 398
            - 0
            76:
            - 398
            - 0
            rareItems:
            75:
            dv: 0
            p:
            39: 1
            m: 398
            76:
            dv: 0
            p:
            39: 1
            m: 398
             */
            //List<Map<String,Object>> tempCircuits = (List<Map<String,Object>>) yml.get("circuits");
            List<Map<?, ?>> checkedOut = yml.getMapList(sPlayerName);
            System.out.println(checkedOut.);
            
            if(1==1) return;
            
            int iSiteId = yml.getInt("siteId");
            HashMap<String,Object> checkedOutRareItemDatas = (HashMap<String,Object>) yml.get("checkedOut");
            HashMap<Integer, Integer[]> checkedOutRareItems = new HashMap<>();
            for(String sRid : checkedOutRareItemDatas.keySet())
            {
                int rid = Integer.parseInt(sRid);
                
                checkedOutRareItems.put(rid,(Integer[]) checkedOutRareItemDatas.get(rid));
            }
            
            
            
            HashMap<Integer,Object> rareItemMaps = (HashMap<Integer,Object>) yml.get("rareItems");
            HashMap<Integer,RareItem> rareItems = new HashMap<>();
            for(Integer rid : rareItemMaps.keySet())
            {
                HashMap<String,Object> rareItem = (HashMap<String,Object>) rareItemMaps.get(rid);

                rareItems.put(rid,new RareItem(
                    rid,
                    sPlayerName,
                    Integer.parseInt((String) rareItem.get("m")),
                    Byte.parseByte((String) rareItem.get("dv")),
                    (HashMap<ItemProperty,Integer>) rareItem.get("p")
                ));
            }
            
            this.playerProfiles.put(sPlayerName, new PlayerProfile(
                sPlayerName,
                iSiteId,
                rareItems,
                checkedOutRareItems
            ));
            
            ApiMessenger.fetchPlayerRareItems(iSiteId,false);
        }
        else
        {
            ApiMessenger.fetchPlayerRareItems(p,false);
        }
    }
    
    public void addPlayerProfile(PlayerProfile pp)
    {
        this.playerProfiles.put(pp.getName().toLowerCase(), pp);
        System.out.println("Added player profile "+pp.getName());
        
        pp.refreshArmor();
    }

    public RareItem getRareItem(Player p, ItemStack is)
    {
        return getRareItem(p,is,false);
    }
    
    public RareItem getRareItem(Player p, ItemStack is, boolean includeInactive)
    {
        String sPlayerName = p.getName().toLowerCase();
        
        if(playerProfiles.containsKey(sPlayerName))
        {
            return playerProfiles.get(sPlayerName).getRareItem(is,includeInactive);
        }
        
        return null;
    }

    public void removePlayerProfile(Player p)
    {
        String sPlayerName = p.getName().toLowerCase();
        
        if(playerProfiles.containsKey(sPlayerName))
        {
            this.savePlayerProfile(playerProfiles.get(sPlayerName));
            
            playerProfiles.remove(sPlayerName);
        }
    }

    public void savePlayerProfile(PlayerProfile pp)
    {
        File ymlFile = new File(PROFILE_DIRECTORY,pp.getName()+".yml");
        
        if(!ymlFile.exists())
        {
            try
            {
                ymlFile.createNewFile();
            }
            catch(IOException ex)
            {
                RareItems.logger.log(Level.INFO, "Could not create {0}", ymlFile.getName());
            }
        }
        
        FileConfiguration yml = YamlConfiguration.loadConfiguration(ymlFile);

        yml.set("siteId", pp.getSiteId());
        yml.set("checkedOut", pp.getCheckedOutItems());
        
        HashMap<Integer,Object> rareItems = new HashMap<>();
        for(RareItem ri : pp.getRareItems().values())
        {
            HashMap<String,Object> rareItem = new HashMap<>();
            
            rareItem.put("m", ri.getMaterialId());
            rareItem.put("dv", ri.getDataValue());
            
            HashMap<Integer,Integer> itemProperties = new HashMap<>();
            for(ItemProperty ip : ri.getItemProperties().keySet())
            {
                itemProperties.put(ip.getId(),ri.getItemProperties().get(ip));
            }
            rareItem.put("p", itemProperties);
                    
            rareItems.put(ri.getId(),rareItem);
        }
        
        yml.set("rareItems",rareItems);
        
        try
        {
            yml.save(ymlFile);
        }
        catch (IOException ex) 
        {
            Logger.getLogger(ProfileManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void refreshArmor(Player player)
    {
        String sPlayerName = player.getName().toLowerCase();
        
        if(playerProfiles.containsKey(sPlayerName))
        {
            playerProfiles.get(sPlayerName).refreshArmor();
        }
    }

    public boolean isRareItem(Player p, ItemStack is, int rid)
    {
        String sPlayerName = p.getName().toLowerCase();
        
        if(playerProfiles.containsKey(sPlayerName))
        {
            return (playerProfiles.get(sPlayerName).getRareItem(is, true) != null);
        }
        return false;
    }

    public boolean hasRareItems(Player p)
    {
        return playerProfiles.containsKey(p.getName().toLowerCase());
    }

    public void fillWithCheckedInItems(Player p, Inventory inv)
    {
        String sPlayerName = p.getName().toLowerCase();
        if(playerProfiles.containsKey(sPlayerName))
        {
            playerProfiles.get(sPlayerName).fillWithCheckedInItems(inv);
        }
    }

    public boolean checkOutRareItem(RareItem ri, Player p)
    {
        return playerProfiles.get(p.getName().toLowerCase()).checkOut(ri);
    }

    public PlayerProfile getPlayerProfile(String sPlayerName,int iSiteId)
    {
        if(playerProfiles.containsKey(sPlayerName.toLowerCase()))
        {
            return this.playerProfiles.get(sPlayerName.toLowerCase());
        }

        PlayerProfile pp = new PlayerProfile(sPlayerName,iSiteId);
        
        playerProfiles.put(sPlayerName, pp);
        
        return pp;
    }

    public void saveAllPlayerProfiles()
    {
        for(PlayerProfile pp : playerProfiles.values())
        {
            savePlayerProfile(pp);
        }
    }
    
}
