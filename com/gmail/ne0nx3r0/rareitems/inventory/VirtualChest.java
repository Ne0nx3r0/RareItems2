
package com.gmail.ne0nx3r0.rareitems.inventory;

//Very trimmed down version of the VirtualChest plugin, not meant to save or retain data

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class VirtualChest implements InventoryHolder
{
    private Inventory chest;

    public VirtualChest(String playerOwner)
    {
        this.chest = Bukkit.createInventory(this, 54, playerOwner);
    }

    @Override
    public Inventory getInventory()
    {
       return this.chest;
    }
    
    
}
