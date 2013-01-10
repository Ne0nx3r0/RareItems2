package com.gmail.ne0nx3r0.rareitems;

import com.gmail.ne0nx3r0.persistence.ProfileManager;
import com.gmail.ne0nx3r0.rareitems.commands.RareItemsCommandExecutor;
import com.gmail.ne0nx3r0.rareitems.http.ApiMessenger;
import com.gmail.ne0nx3r0.rareitems.item.ItemProperty;
import com.gmail.ne0nx3r0.rareitems.item.ItemPropertyManager;
import com.gmail.ne0nx3r0.rareitems.listeners.RareItemsPlayerListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class RareItems extends JavaPlugin{
    public static Plugin self;
    public static ItemPropertyManager ipm;
    public static ApiMessenger am;
    public static ProfileManager pm;
    
    public static final Logger logger = Logger.getLogger("RareItemsRegistration");
    
    public static final int COST_TYPE_FOOD = 0;
    public static final int COST_TYPE_XP = 1;
    public static final int COST_TYPE_MONEY = 2;
    
    public static int COST_TYPE;
    public static int COST_MULTIPLIER; 
    public static boolean USE_PERMISSIONS;
    public static int MAX_CHECKED_OUT_ITEMS;
    public static boolean DEBUG_MODE = false;

    public static final String RID_PREFIX = ChatColor.DARK_GRAY+"RID: "+ChatColor.GRAY;
    
    public static  Economy economy;
    
    @Override
    public void onEnable()
    {        
        RareItems.self = this;
        
        File check = new File(getDataFolder(), "config.yml");
        
        if(!check.exists())
        {
            saveDefaultConfig();
            reloadConfig();
        }
        
        if(getConfig().getString("costType").equalsIgnoreCase("food"))
        {
            COST_TYPE = COST_TYPE_FOOD;
        }
        else if(getConfig().getString("costType").equalsIgnoreCase("xp"))
        {
            COST_TYPE = COST_TYPE_XP;
        }
        else if(getConfig().getString("costType").equalsIgnoreCase("money"))
        {
            RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
            
            if(economyProvider != null)
            {
                economy = economyProvider.getProvider();
            }

            this.getLogger().log(Level.SEVERE,"You specified money as your cost type, however you don't have Vault! Disabling...");
            
            this.getPluginLoader().disablePlugin(this);
            
            COST_TYPE = COST_TYPE_MONEY;
        }
        
        COST_MULTIPLIER = getConfig().getInt("costMultiplier");
        
        USE_PERMISSIONS = getConfig().getBoolean("usePermissions");
        
        DEBUG_MODE = getConfig().getBoolean("debugMode");
        
        MAX_CHECKED_OUT_ITEMS = getConfig().getInt("maxItemsCheckedOut");
        
        ipm = new ItemPropertyManager();
        
        am = new ApiMessenger();
        
        pm = new ProfileManager();
        
// Setup allowed item properties
        File propertyCostsFile = new File(this.getDataFolder(),"property_costs.yml");
        
        boolean newPropertyFile = false;
        
        if(!propertyCostsFile.exists())
        {
            newPropertyFile = true;
            
            getLogger().log(Level.INFO, "Creating property_costs.yml");
            
            this.copy(this.getResource("property_costs.yml"), propertyCostsFile);
        }
        
        FileConfiguration yml = YamlConfiguration.loadConfiguration(propertyCostsFile);
        
        for(ItemProperty ip : ipm.getAvailableItemProperties())
        {
            String sPropertyName = ip.getName().replace(" ", "");
            
            int customCost = yml.getInt(sPropertyName,-2);
            
            if(customCost < -1)
            {
                int defaultCost = -1;
                
                if(getConfig().getBoolean("enableNewProperties",true))
                {
                    defaultCost = ip.getCost();
                }
                
                if(!newPropertyFile)
                {
                    getLogger().log(Level.INFO,"new property added to allowed_properties.yml: "+sPropertyName+": "+defaultCost);
                }
                
                yml.set(sPropertyName, defaultCost);
            }
            else if(customCost == -1)
            {
                ip.setEnabled(false);
            }
            else if(customCost != ip.getCost())
            {
                ip.setCost(customCost);
            }
        }
        
        try
        {
            yml.save(propertyCostsFile);
        }
        catch (IOException ex)
        {
            getLogger().log(Level.SEVERE, "Unable to save alowed_properties.yml! (disabling to protect your server)");
            
            Logger.getLogger(RareItems.class.getName()).log(Level.SEVERE, null, ex);
            
            getPluginLoader().disablePlugin(this);
        }
        
//register events
        getServer().getPluginManager().registerEvents(new RareItemsPlayerListener(), this);
        
        getCommand("ri").setExecutor(new RareItemsCommandExecutor());

        if(Bukkit.getOnlinePlayers().length > 0)
        {
            for(Player p : Bukkit.getOnlinePlayers())
            {
                RareItems.pm.loadPlayerProfile(p,false);
                am.addPlayerToQueue(p);
            }
                        
            ApiMessenger.fetchPlayerRareItems(Bukkit.getOnlinePlayers(), false);
        }
    }    
    
    @Override
    public void onDisable()
    {      
        for(Player p : Bukkit.getOnlinePlayers())
        {
            p.closeInventory();
        }
        
        am.stopTask();
    }
    
    private void copy(InputStream in, File file)
    {
        try
        {
            OutputStream out = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len;
            while((len=in.read(buf))>0){
                out.write(buf,0,len);
            }
            out.close();
            in.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}