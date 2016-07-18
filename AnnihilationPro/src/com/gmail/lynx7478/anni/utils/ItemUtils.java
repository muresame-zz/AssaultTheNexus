package com.gmail.lynx7478.anni.utils;

import java.util.ArrayList;

import org.bukkit.Material;

public class ItemUtils {
	
	public static void addToList(ArrayList<Material> list, Material... materials)
	{
		for(Material mat : materials)
		{
			list.add(mat);
		}
	}

}
