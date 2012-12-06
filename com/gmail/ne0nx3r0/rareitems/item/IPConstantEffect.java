package com.gmail.ne0nx3r0.rareitems.item;

import com.gmail.ne0nx3r0.rareitems.RareItems;
import org.bukkit.entity.Player;

public class IPConstantEffect extends ItemProperty
{
    public IPConstantEffect(int id,String name,String description)
    {
        super(id,name,description,0);
    }
    
    public IPConstantEffect(int id,String name,String description,int cost)
    {
        super(id,name,description,cost);
    }
    
    @Override
    public void onEquip(Player p,int level)
    {
        RareItems.rig.grantPlayerEffect(p.getName(),this,level);
    }
    
    @Override
    public void onUnequip(Player p,int level)
    {
        RareItems.rig.revokePlayerEffect(p.getName(),this);
    }
}
