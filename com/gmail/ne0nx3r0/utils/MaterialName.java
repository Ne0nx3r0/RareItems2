package com.gmail.ne0nx3r0.utils;

import org.bukkit.Material;

public class MaterialName
{
    public static String getMaterialDisplayName(int matId,byte dv)
    {        
        Material m = Material.getMaterial(matId);
        
        if(m ==  Material.SKULL_ITEM)
        {
            switch(dv)
            {
                case 0x0:
                    return "Skeleton Head";
                case 0x1:
                    return "Wither Head";
                case 0x2:
                    return "Zombie Head";
                case 0x3:
                    return "Steve Head";
                case 0x4:
                    return "Creeper Head";
            }
        }

        StringBuilder materialName = new StringBuilder();
        
        for(String c : m.toString().toLowerCase().split("_"))
        {
            materialName.append(Character.toUpperCase(c.charAt(0))).append(c, 1, c.length()).append(" ");
        }
        
        return materialName.toString().trim();
    }
}
