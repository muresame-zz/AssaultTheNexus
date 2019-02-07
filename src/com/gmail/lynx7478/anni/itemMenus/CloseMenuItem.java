/*
 * This file is part of AmpMenus.
 *
 * Copyright (c) 2014 <http://github.com/ampayne2/AmpMenus/>
 *
 * AmpMenus is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * AmpMenus is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with AmpMenus.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.gmail.lynx7478.anni.itemMenus;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import com.gmail.lynx7478.anni.utils.VersionUtils;

/**
 * A {@link com.gmail.lynx7478.anni.itemMenus.StaticMenuItem} that closes the
 * {@link com.gmail.lynx7478.anni.itemMenus.ItemMenu}.
 */
public class CloseMenuItem extends StaticMenuItem
{

	private static Material mat;
	
	
	public CloseMenuItem()
	{
		super(ChatColor.RED + "Close", new ItemStack(mat));
	}

	@Override
	public void onItemClick(ItemClickEvent event)
	{
		event.setWillClose(true);
	}
	
	public static void init() throws ClassNotFoundException
	{
		if(!VersionUtils.getVersion().contains("13"))
		{
			mat = Material.RECORD_4;
		}else
		{
			mat = (Material) Enum.valueOf((Class<Enum>) Class.forName("org.bukkit.Material"), "LEGACY_RECORD_4");
		}
	}
}