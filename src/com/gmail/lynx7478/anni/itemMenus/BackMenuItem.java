package com.gmail.lynx7478.anni.itemMenus;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import com.gmail.lynx7478.anni.utils.VersionUtils;

/**
 * A {@link com.gmail.lynx7478.anni.itemMenus.StaticMenuItem} that opens the
 * {@link com.gmail.lynx7478.anni.itemMenus.ItemMenu}'s parent menu if it exists.
 */
public class BackMenuItem extends StaticMenuItem
{

	private static Material mat;
	public BackMenuItem()
	{
		super(ChatColor.RED + "Back", new ItemStack(Material.FENCE_GATE));
	}

	@Override
	public void onItemClick(ItemClickEvent event)
	{
		event.setWillGoBack(true);
	}
	
	public static void init() throws ClassNotFoundException
	{
		if(!VersionUtils.getVersion().contains("13"))
		{
			mat = Material.FENCE_GATE;
		}else
		{
			mat = (Material) Enum.valueOf((Class<Enum>) Class.forName("org.bukkit.Material"), "LEGACY_FENTE_GATE");
		}
	}
}