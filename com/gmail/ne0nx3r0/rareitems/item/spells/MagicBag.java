package com.gmail.ne0nx3r0.rareitems.item.spells;

import com.gmail.ne0nx3r0.rareitems.item.ItemProperty;
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
        e.getPlayer().openInventory(e.getPlayer().getEnderChest());
        
        return true;
    }
}