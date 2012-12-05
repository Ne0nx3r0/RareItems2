
package com.gmail.ne0nx3r0.rareitems.inventory;

import com.gmail.ne0nx3r0.rareitems.RareItems;
import com.gmail.ne0nx3r0.rareitems.item.RareItem;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.entity.Player;

public final class VirtualChestManager
{
    private static HashMap<String, HashMap<Integer,Integer[]>> checkedOutItems;//Lower case player name
    
    public VirtualChestManager()
    {
        checkedOutItems = new HashMap<>();      
        
        this.load();
    }

    public boolean checkIn(int rid, Player p)
    {
        String sPlayerName = p.getName().toLowerCase();
        
        if(checkedOutItems.containsKey(sPlayerName))
        {
            if(checkedOutItems.get(sPlayerName).containsKey(rid))
            {
                checkedOutItems.get(sPlayerName).remove(rid);

                if(checkedOutItems.get(sPlayerName).isEmpty())
                {
                    checkedOutItems.remove(sPlayerName);
                }
                
                this.save();
                
                return true;
            }
        }
        return false;
    }

    public boolean checkOut(RareItem ri, Player p)
    {        
        String sPlayerName = p.getName().toLowerCase();
        
        if(!checkedOutItems.containsKey(sPlayerName))
        {
            checkedOutItems.put(sPlayerName, new HashMap<Integer,Integer[]>());
        }
        
        if(!checkedOutItems.get(sPlayerName).containsKey(ri.getId()))
        {
            if(checkedOutItems.get(sPlayerName).size() < 5)
            {
                checkedOutItems.get(sPlayerName).put(ri.getId(),new Integer[]{
                    ri.getMaterialId(),
                    new Byte(ri.getDataValue()).intValue()
                });
                
                this.save();
                
                return true;
            }
        }
        
        return false;
    }

    public boolean isCheckedOut(RareItem ri)
    {
        if(checkedOutItems.containsKey(ri.getOwner().toLowerCase()))
        {
            if(checkedOutItems.get(ri.getOwner().toLowerCase()).containsKey(ri.getId()))
            {
                return true;
            }
        }
        return false;
    }

    public boolean isCheckedOut(String sPlayerName,int rid)
    {
        if(checkedOutItems.containsKey(sPlayerName.toLowerCase()))
        {
            if(checkedOutItems.get(sPlayerName.toLowerCase()).containsKey(rid))
            {
                return true;
            }
        }
        return false;
    }
    
    public void save()
    {
        try
        {
            File file = new File(RareItems.self.getDataFolder().getAbsolutePath(),"checkout.txt");

            if(!file.exists())
            {
                RareItems.self.getDataFolder().mkdirs();
                file.createNewFile();
            }
            
            FileOutputStream saveFile = new FileOutputStream(file);
            
            try (ObjectOutputStream save = new ObjectOutputStream(saveFile))
            {
                save.writeObject(checkedOutItems);
            }
        } 
        catch(Exception ex)
        {
            Logger.getLogger(VirtualChestManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void load()
    {
        try
        {
            FileInputStream saveFile = new FileInputStream(new File(RareItems.self.getDataFolder().getAbsolutePath(),"checkout.txt"));
            
            try (ObjectInputStream restore = new ObjectInputStream(saveFile))
            {
                checkedOutItems = (HashMap<String, HashMap<Integer,Integer[]>>) restore.readObject();
            }
        } 
        catch(IOException | ClassNotFoundException ex)
        {
            RareItems.logger.log(Level.INFO,"checkout.txt not found, will be created on the next save.");
            //Logger.getLogger(VirtualChestManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Set<Integer> getCheckedOutRareItems(String sPlayerName)
    {
        if(checkedOutItems.containsKey(sPlayerName.toLowerCase()))
        {
            return checkedOutItems.get(sPlayerName.toLowerCase()).keySet();
        }
        return null;
    }

    public Integer[] getCheckedOutRareItemData(String sPlayerName, int rid) 
    {
        return checkedOutItems.get(sPlayerName.toLowerCase()).get(rid);
    }
}
