package com.gmail.ne0nx3r0.rareitems.item.spells;

import com.gmail.ne0nx3r0.rareitems.item.ItemProperty;
import net.minecraft.server.IInventory;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.event.player.PlayerInteractEvent;

public class MagicBag extends ItemProperty
{
    public MagicBag()
    {
        super(28,"Magic Bag","Mobile ender chest",1);
    }
    
    @Override
    public boolean onInteract(PlayerInteractEvent e,int level)
    {
        IInventory inventory = ((CraftPlayer)e.getPlayer()).getHandle().getEnderChest();
        ((CraftPlayer)e.getPlayer()).getHandle().openContainer(inventory);
        
        return true;
    }
}