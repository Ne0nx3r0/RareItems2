package com.gmail.ne0nx3r0.rareitems.http;

import com.gmail.ne0nx3r0.rareitems.RareItems;
import com.gmail.ne0nx3r0.rareitems.item.ItemProperty;
import com.gmail.ne0nx3r0.rareitems.item.RareItem;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ApiMessenger
{
    private static HashMap<String,Integer> onlinePlayerIds;
    
    public ApiMessenger()
    {
        onlinePlayerIds = new HashMap<>();
        
        Bukkit.getScheduler().scheduleAsyncRepeatingTask(RareItems.self,new Runnable()
        {
            @Override
            public void run()
            {
                System.out.println("Checking if any players have new RareItems...");
                
                JSONObject json = new JSONObject();

                JSONArray jPlayers = new JSONArray();
                
                for(Player p : Bukkit.getOnlinePlayers())
                {
                    jPlayers.add(p.getName());
                }
                
                for(int iSiteId : RareItems.am.getSiteIds().values())
                {
                    jPlayers.add(iSiteId);
                }
                
                json.put("server_players", jPlayers);
                
                json.put("server_name", Bukkit.getServerName());

                json.put("server_port",Bukkit.getServer().getPort());

                try{
                    URLConnection connection = new URL("http://www.rareitemsplugin.tk/api/getRareItemUpdate/").openConnection();
                    connection.setDoOutput(true);
                    connection.setDoInput(true);

                    try(OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream()))
                    {
                        writer.write("params="+json.toJSONString());
                        writer.flush();
                    }

                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                    String line;
                    String returnString = "";

                    while ((line = reader.readLine()) != null)
                    {
                        returnString += line;
                    }
                    
                    ApiMessenger.receivePlayerUpdate(returnString);
                }
                catch (Exception ex)
                {
                    RareItems.logger.log(Level.SEVERE, null, ex);
                }
            }
        }, 20*60*30, 20*60*30);
    }

    public static void receivePlayerUpdate(String response)
    {
/*
 *{"status":"success",
 * "players":{
 *  "redfurysoldier":{
 *      "233":{
 *          "material":"298",
 *          "dataValue":"0",
 *          "properties":{
 *              "5":"3",
 *              "6":"3",
 *              "10":"5",
 *              "12":"1"
 *              }
 *          }
 *      }
 *  }
 *}
 * */
        //System.out.println(response);
        
        try
        {
            JSONObject json = (JSONObject) (new JSONParser()).parse(response);
            
            if(((String) json.get("status")).equals("success") && (boolean) json.get("hasItems"))
            {
                Map<String,Object> playersData = (Map<String,Object>) json.get("players");
                
                for(String sPlayerName : playersData.keySet())
                {
                    //verify player is still online
                    Player p = Bukkit.getPlayer(sPlayerName);
                    
                    if(p != null)
                    {
                        RareItems.rig.removeAllPlayerRareItems(sPlayerName);
                        
                        Map<String,Object> pendingItems = (Map<String,Object>) playersData.get(sPlayerName);
                            
                        for(String sPendingItemId : pendingItems.keySet())
                        {   
                            Map<String,Object> pendingItem = (Map<String,Object>) pendingItems.get(sPendingItemId);
                            
                            HashMap<ItemProperty,Integer> ips = new HashMap<>();
                            
                            String ipString = "";
                            Map<String,String> pendingItemProperties = (Map<String,String>) pendingItem.get("properties");
                            for(String sIpId : pendingItemProperties.keySet())
                            {
                                int ipId = Integer.parseInt(sIpId);
                                
                                ItemProperty ip = RareItems.rig.getItemProperty(ipId);

                                if(ip != null)
                                {
                                    int ipLevel = Integer.parseInt(pendingItemProperties.get(sIpId));

                                    ips.put(
                                        ip,
                                        ipLevel
                                    );
                                }
                                else
                                {
                                    RareItems.logger.log(Level.WARNING,"Server returned an invalid ItemProperty ID"+ipId+"! (is RareItems up to date?)");
                                }
                            }
                            
                            int materialId = Integer.parseInt((String) pendingItem.get("material"));
                            
                            RareItem ri = new RareItem(
                                Integer.parseInt(sPendingItemId),
                                sPlayerName,
                                materialId,
                                Byte.parseByte((String) pendingItem.get("dataValue")),
                                ips
                            );
                            RareItems.rig.addPlayerAvailableRareItem(ri);
                            
                            if(((String) pendingItem.get("pending")).equals("1"))
                            {
                                RareItems.self.getServer().broadcastMessage("----------------------------------------------------");
                                RareItems.self.getServer().broadcastMessage(sPlayerName + " scored a "+ri.getDisplayName()+"!");
                                RareItems.self.getServer().broadcastMessage("----------------------------------------------------");
                            }
                        }
                        
                    }
                }
            }
        }
        catch(ParseException ex)
        {
            Logger.getLogger(ApiMessenger.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void fetchPlayerRareItems(final String sPlayerName)
    {
        (new Thread(){
            @Override
            public void run()
            {
                try{
                    URLConnection connection = new URL("http://www.rareitemsplugin.tk/api/getPlayerRareItems/").openConnection();
                    connection.setDoOutput(true);
                    connection.setDoInput(true);

                    try(OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream()))
                    {
                        writer.write("player="+sPlayerName);
                        writer.flush();
                    }

                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                    String line;
                    String returnString = "";

                    while ((line = reader.readLine()) != null)
                    {
                        returnString += line;
                    }
                    
                    //System.out.println(returnString);
                    
                    final String sReturnString = returnString;
                    
                    Bukkit.getScheduler().scheduleSyncDelayedTask(RareItems.self,new Runnable(){
                        @Override
                        public void run()
                        {
                            ApiMessenger.receiveRareItems(sReturnString);
                        }
                    });
                }
                catch (Exception ex)
                {
                    RareItems.logger.log(Level.SEVERE, null, ex);
                }
            }
        }).start();
    }
    
    public static void receiveRareItems(String sReturnString)
    {
        JSONParser jp = new JSONParser();

        try
        {
            JSONObject json = (JSONObject) jp.parse(sReturnString);

            if((json.get("status")).equals("success"))
            {
                if((boolean) json.get("hasItems"))
                {
                    String sPlayerName = (String) json.get("player");
                    int iPlayerSiteId = Integer.parseInt((String) json.get("siteId"));
                    
                    RareItems.am.addSiteId(sPlayerName, iPlayerSiteId);

                    Map<String,Map<String,Object>> items = (Map<String,Map<String,Object>>) json.get("items");

                    for(String sRid : items.keySet())
                    {
                        int rid = Integer.parseInt(sRid);
                        int materialId = Integer.parseInt((String) items.get(sRid).get("materialId"));
                        byte dataValue = Byte.parseByte((String) items.get(sRid).get("dataValue"));

                        Map<String,String> ipData = (Map<String,String>) items.get(sRid).get("properties");
                        HashMap<ItemProperty,Integer> ips = new HashMap<>();
                        for(String sIPid : ipData.keySet())
                        {
                            ItemProperty ip = RareItems.rig.getItemProperty(Integer.parseInt(sIPid));
                            
                            if(ip != null)
                            {
                                ips.put(
                                    ip,
                                    Integer.parseInt(ipData.get(sIPid))
                                );
                            }
                            else
                            {
                                RareItems.logger.log(Level.WARNING,"Server returned an invalid ItemProperty ID"+sIPid+"! (is RareItems up to date?)");
                            }
                        }

                        RareItem ri = new RareItem(rid,sPlayerName,materialId,dataValue,ips);
                        RareItems.rig.addPlayerAvailableRareItem(ri);
                        
                        if((boolean) items.get(sRid).get("pending"))
                        {
                            RareItems.self.getServer().broadcastMessage("----------------------------------------------------");
                            RareItems.self.getServer().broadcastMessage(sPlayerName + " scored a "+ri.getDisplayName()+"!");
                            RareItems.self.getServer().broadcastMessage("----------------------------------------------------");
                        }
                    }
                    
                    RareItems.rig.refreshArmor(Bukkit.getPlayer(sPlayerName));
                }
            }
            else
            {
                RareItems.logger.log(Level.INFO,(String) json.get("message"));
            }
        }
        catch(ParseException ex)
        {
            Logger.getLogger(ApiMessenger.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addSiteId(String sPlayerName,int iPlayerSiteId)
    {
        onlinePlayerIds.put(sPlayerName,iPlayerSiteId);
    }
    public HashMap<String, Integer> getSiteIds()
    {
        return onlinePlayerIds;
    }
    public void removeSiteId(String sPlayerName)
    {
        onlinePlayerIds.remove(sPlayerName);
    }
}
