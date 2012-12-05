package com.gmail.ne0nx3r0.rareitems;

import com.gmail.ne0nx3r0.rareitems.http.ApiMessenger;
import com.gmail.ne0nx3r0.rareitems.inventory.VirtualChestManager;
import com.gmail.ne0nx3r0.rareitems.item.RareItemManager;
import java.util.logging.Logger;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class RareItems extends JavaPlugin{
    public static Plugin self;
    public static RareItemManager rig;
    public static ApiMessenger am;
    public static VirtualChestManager vcm;
    public static final Logger logger = Logger.getLogger("RareItemsRegistration");

    @Override
    public void onEnable()
    {        
        RareItems.self = this;
        
        RareItems.rig = new RareItemManager();
        
        RareItems.am = new ApiMessenger();
        
        RareItems.vcm = new VirtualChestManager();
        
        //register events
        getServer().getPluginManager().registerEvents(new RareItemsPlayerListener(), this);
        
        getCommand("ri").setExecutor(new RareItemsCommandExecutor());
    }
}