package com.gmail.ne0nx3r0.rareitems;

import com.gmail.ne0nx3r0.rareitems.inventory.VirtualChest;
import com.gmail.ne0nx3r0.rareitems.item.RareItem;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class RareItemsCommandExecutor implements CommandExecutor 
{
    @Override
    public boolean onCommand(CommandSender cs, Command cmd, String alias, String[] args) {
        if (!(cs instanceof Player)){
            RareItems.logger.log(Level.INFO,"RI is not console-enabled.");
        }
        
        Player p = (Player) cs;
        
        if(args.length == 0)
        {
            p.sendMessage("Usage:");
            p.sendMessage("/ri claim - check-out/in RareItems");
            p.sendMessage("/ri return - check-in a RareItem you lost or traded away.");
            p.sendMessage("/ri clear - Clear a RareItem's properties");
            
            return true;
        }
        else
        {
            if(args[0].equalsIgnoreCase("claim"))
            {
                if(RareItems.rig.hasRareItems(p.getName()))
                {
                    VirtualChest vc = new VirtualChest(p.getName()+"'s RareItems");

                    Inventory inv = vc.getInventory();

                    RareItems.rig.fillWithAvailableRareItems(p,inv);

                    p.openInventory(vc.getInventory());
                }
                else
                {
                    p.sendMessage("You don't have any rare items! Ouch.");
                }

                return true;
            }
            else if(args[0].equalsIgnoreCase("return"))
            {
                if(args.length < 2)
                {
                    p.sendMessage(ChatColor.YELLOW+"Here are the items you can return:");
                    
                    Set<Integer> checkedOutRareItems = RareItems.vcm.getCheckedOutRareItems(p.getName());
                    
                    if(checkedOutRareItems != null)
                    {
                        for(Iterator<Integer> it = checkedOutRareItems.iterator(); it.hasNext();)
                        {
                            int rid = it.next();
                            RareItem ri = RareItems.rig.getRareItem(p.getName(), rid);
                            if(ri != null)
                            {
                                p.sendMessage(ChatColor.YELLOW+"RID: "+ChatColor.WHITE+ri.getId() + " ("+ri.getDisplayName()+")");
                            }
                            else
                            {
                                Integer[] checkedOutRareItemData = RareItems.vcm.getCheckedOutRareItemData(p.getName(), rid);
                                
                                p.sendMessage(ChatColor.YELLOW+"RID: "+ChatColor.WHITE+rid+" ("+Material.getMaterial(checkedOutRareItemData[0]) +")");
                            }
                        }
                    }
                    
                    p.sendMessage(ChatColor.YELLOW+"Use /ri return <rid> to exchange an it");
                }
                else
                {
                    int rid = 0;
                    
                    try
                    {
                        rid = Integer.parseInt(args[1]);
                    }
                    catch(Exception ex)
                    {
                        p.sendMessage(ChatColor.RED+"Invalid RID");
                        
                        return true;
                    }
                    
                    if(RareItems.vcm.isCheckedOut(p.getName(),rid))
                    {
                        Integer[] riData = RareItems.vcm.getCheckedOutRareItemData(p.getName(), rid);
                        
                        for(ItemStack is : p.getInventory().getContents())
                        {
                            if(is != null 
                            && is.getTypeId() == riData[0]
                            && is.getData().getData() == riData[1].byteValue()
                            && RareItems.rig.isRareItem(is,rid))
                            {
                                p.getInventory().remove(is);
                                
                                /* 
                                 * It seems cruel to attempt check-in after 
                                 * removing the item, however it's better
                                 * than creating a duping glitch if someone
                                 * finds a way to make check-in fail. 
                                 * 
                                 * Additionally this is unlikely to fail since
                                 * it was just verified using isCheckedOut
                                */
                                if(RareItems.vcm.checkIn(rid, p))
                                {
                                    p.sendMessage("Checked in RID"+rid+"!");

                                    return true;
                                }
                            }
                        }
                        
                        //if it wasn't found in the last look for a clean copy
                        for(ItemStack is : p.getInventory().getContents())
                        {
                            //Basically the same check for a clean item
                            if(is != null 
                            && is.getTypeId() == riData[0]
                            && is.getData().getData() == riData[1].byteValue())
                            {
                                p.getInventory().remove(is);
                                
                                if(RareItems.vcm.checkIn(rid, p))
                                {
                                    p.sendMessage("Checked in RID"+rid+"!");

                                    return true;
                                }
                            }
                        }
                        
                        p.sendMessage("You don't seem to have the item, or a clean copy of it!");
                        
                        return true;
                    }
                    else
                    {
                        p.sendMessage("RID"+rid+" is not checked out!");
                    }
                }
            }
            else if(args[0].equalsIgnoreCase("clear"))
            {
                //
            }
        }
        
        return false;
    }//End onCommand
}
