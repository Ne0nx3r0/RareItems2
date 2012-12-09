package com.gmail.ne0nx3r0.persistence;

import com.gmail.ne0nx3r0.rareitems.RareItems;
import com.gmail.ne0nx3r0.rareitems.http.ApiMessenger;
import com.gmail.ne0nx3r0.rareitems.item.RareItem;
import java.io.File;
import java.util.HashMap;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

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
        File playerProfile = new File(PROFILE_DIRECTORY,p.getName()+".yml");

        if(playerProfile.exists())
        {
            //load profile
            
            ApiMessenger.fetchPlayerRareItems(1,false);
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

    private void savePlayerProfile(PlayerProfile pp)
    {
        File playerProfile = new File(PROFILE_DIRECTORY,pp.getName()+".yml");
        
        
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
        return playerProfiles.get(p.getName().toLowerCase()).checkOut(ri.getId());
    }
    
}
