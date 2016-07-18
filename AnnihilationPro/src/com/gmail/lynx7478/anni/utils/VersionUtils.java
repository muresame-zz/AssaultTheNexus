package com.gmail.lynx7478.anni.utils;

import org.bukkit.Bukkit;

public class VersionUtils
{
    public static String getVersion()
    {
        String packageName = Bukkit.getServer().getClass().getPackage().getName();
        return packageName.substring(packageName.lastIndexOf(".") + 1);
    }
    
    public static boolean is1_9(){
        String packageName = Bukkit.getServer().getClass().getPackage().getName();
        packageName.substring(packageName.lastIndexOf(".") + 1);
        if(packageName.contains("v1_9"))
        	return true;
        else
        	return false;
    }
}
