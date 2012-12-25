package com.gmail.ne0nx3r0.rareitems.listeners;

import com.gmail.ne0nx3r0.rareitems.RareItems;
import com.gmail.ne0nx3r0.rareitems.inventory.VirtualChest;
import com.gmail.ne0nx3r0.rareitems.item.RareItem;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

public class RareItemsPlayerListener implements Listener
{
    @EventHandler(priority=EventPriority.LOWEST, ignoreCancelled=true)
    public void onPlayerJoin(PlayerJoinEvent e)
    {        
        RareItems.pm.loadPlayerProfile(e.getPlayer());
            
        RareItems.am.addPlayerToQueue(e.getPlayer());
    }

    @EventHandler(priority=EventPriority.NORMAL, ignoreCancelled=true)
    public void onPlayerQuit(PlayerQuitEvent e)
    {
        RareItems.pm.removePlayerProfile(e.getPlayer());
            
        RareItems.am.removePlayerFromQueue(e.getPlayer());
    }
    
    @EventHandler(priority=EventPriority.LOWEST, ignoreCancelled=true)
    public void onPlayerChangeWorld(PlayerChangedWorldEvent e)
    {
        RareItems.pm.refreshArmor(e.getPlayer());
    }
    
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerInteract(PlayerInteractEvent e)
    {
        if((e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK)
        && e.getPlayer().getItemInHand() != null
        && e.getPlayer().getItemInHand().getType() != Material.AIR)
        {
            RareItem ri = RareItems.pm.getRareItem(e.getPlayer(),e.getPlayer().getItemInHand());

            if(ri != null)
            {
                ri.onInteract(e);
            }
        }
    }
    
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerInteractedWithEntity(PlayerInteractEntityEvent e)
    {
        RareItem ri = RareItems.pm.getRareItem(e.getPlayer(),e.getPlayer().getItemInHand());

        if(ri != null)
        {
            ri.onInteractEntity(e);
        }
    }
    
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerInventoryClick(InventoryClickEvent e)
    {
        if(e.getSlotType() == SlotType.ARMOR)
        {
            if(e.getCursor() != null && e.getCursor().getType() != Material.AIR)//equipped item
            {
                RareItem riEquipped = RareItems.pm.getRareItem((Player) e.getWhoClicked(),e.getCursor());
                if(riEquipped != null)
                {
                    riEquipped.onEquipped(((Player) e.getWhoClicked()));
                }
            }
            if(e.getCurrentItem().getType() != Material.AIR)//unequipped item
            {                
                RareItem riUnequipped = RareItems.pm.getRareItem((Player) e.getWhoClicked(),e.getCurrentItem());
                
                if(riUnequipped != null)
                {
                    riUnequipped.onUnequipped(((Player) e.getWhoClicked()));
                }
            }
        }
        
        if(e.getInventory().getHolder() instanceof VirtualChest)
        {
            if(e.isShiftClick())
            {
                e.setCancelled(true);
                return;
            }
            Player p = (Player) e.getWhoClicked();
            
            if(e.getRawSlot() < 54)
            {
                if(e.getCursor() != null && e.getCursor().getType() != Material.AIR)//putting down
                {
                    RareItem riPuttingDown = RareItems.pm.getRareItem(((Player) e.getWhoClicked()),e.getCursor(),true);

                    if(riPuttingDown == null 
                    || !riPuttingDown.getOwner().equalsIgnoreCase(p.getName()))
                    {
                        p.sendMessage("You can only check in your own RareItems!");

                        e.setCancelled(true);
                    }
                    else
                    {
                        if(!RareItems.pm.checkInRareItem(riPuttingDown,p))
                        {
                            p.sendMessage("You cannot check in that item");

                            e.setCancelled(true);
                        }
                    }
                     
                }

                if(e.getCurrentItem() != null && e.getCurrentItem().getType() != Material.AIR)//picking up
                {
                    RareItem riPickingUp = RareItems.pm.getRareItem(p,e.getCurrentItem(),true);

                    if(riPickingUp == null 
                    || !riPickingUp.getOwner().equalsIgnoreCase(p.getName()))
                    {
                        p.sendMessage("You can only check out your own RareItems!");

                        e.setCancelled(true);

                    }
                    else
                    {
                        if(!RareItems.pm.checkOutRareItem(riPickingUp,p))
                        {
                            p.sendMessage("You cannot check out that item");

                            e.setCancelled(true);
                        }
                    }
                }  
            }
        }
    }
    
    @EventHandler(priority=EventPriority.NORMAL, ignoreCancelled=true)
    public void onEntityDamaged(EntityDamageEvent e)
    {
        if(e.getEntity() instanceof Player)
        {
           if(e.getCause() == EntityDamageEvent.DamageCause.FALL
           && RareItems.ipm.playerHasItemProperty(((Player) e.getEntity()).getName(),40))//Cat's Feet
           {
               e.setCancelled(true);
           }
           else if(e.getCause().equals(EntityDamageEvent.DamageCause.DROWNING)
           && RareItems.ipm.playerHasItemProperty(((Player) e.getEntity()).getName(),7))//Water Breathing
           {
                   e.setCancelled(true);
           }
           else if(RareItems.ipm.playerHasItemProperty(((Player) e.getEntity()).getName(),5))//Hardy
           {
               e.setDamage(e.getDamage()-RareItems.ipm.getPlayerEffectLevel(((Player) e.getEntity()).getName(), 5));
           }
        }
    }
    
    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onEntityDamagedByEntity(EntityDamageByEntityEvent e)
    {
         if(e.getDamager() instanceof Player)
         {
            Player attacker = (Player) e.getDamager();
             
            //Strength Ability
            if(RareItems.ipm.playerHasItemProperty(attacker.getName(),5))//Strength
            {
                e.setDamage(e.getDamage()
                    +RareItems.ipm.getPlayerEffectLevel(attacker.getName(),5));//Strength
            }
            
            RareItem ri = RareItems.pm.getRareItem(attacker, attacker.getItemInHand());
            
            if(ri != null)
            {
                ri.onDamagedOther(e);
            }
         }/*
         if(e.getDamager() instanceof Arrow)
         {
            Arrow arrow = (Arrow) e.getDamager();
            
            System.out.println("hit person!");
            
            for(int i=0;i<e.getEntity().getMetadata("test").size();i++)
            {
                System.out.println(e.getEntity().getMetadata("test").get(i));
            }
         }*/
    }
/*
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onEntityShootBow(EntityShootBowEvent e)
    {
        if ((e.getEntity() instanceof Player))
        {
            Player p = (Player) e.getEntity();
            
            Arrow arrow = (Arrow) e.getProjectile();
            
            System.out.println("shot!");
            arrow.setMetadata("test", new FixedMetadataValue(RareItems.self, "testy!"));
        }
    }
    
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onProjectileHit(ProjectileHitEvent e)
    {
        System.out.println("hit!");
        
        for(int i=0;i<e.getEntity().getMetadata("test").size();i++)
        {
            System.out.println(e.getEntity().getMetadata("test").get(i));
        }
    }*/
        
    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onEntityDeath(EntityDeathEvent e)
    {
        if(e.getEntity() instanceof Player)
        {
            Player p = (Player) e.getEntity();
            for(ItemStack isDrop : e.getDrops())
            {
                if(RareItems.pm.getRareItem(p, isDrop) != null)
                {
                    e.getDrops().remove(isDrop);
                }
            }
        }
    }
}