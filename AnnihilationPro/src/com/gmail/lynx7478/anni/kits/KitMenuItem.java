package com.gmail.lynx7478.anni.kits;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.gmail.lynx7478.anni.anniGame.AnniPlayer;
import com.gmail.lynx7478.anni.anniGame.Game;
import com.gmail.lynx7478.anni.itemMenus.ItemClickEvent;
import com.gmail.lynx7478.anni.itemMenus.MenuItem;
import com.gmail.lynx7478.anni.main.Lang;

public class KitMenuItem extends MenuItem
{
	private final Kit kit;
	public KitMenuItem(final Kit kit)
	{
		super(kit.getName(), kit.getIconPackage().getIcon(), kit.getIconPackage().getLore());
		this.kit = kit;
	}
	
	public Kit getKit()
	{
		return kit;
	}
	
	@Override
	public ItemStack getFinalIcon(Player player)
	{
		List<String> str = new ArrayList<String>(getLore());
		str.add(ChatColor.GOLD+"--------------------------");
		if(kit.hasPermission(player))
			str.add(Lang.UNLOCKED.toString());
		else
			str.add(Lang.LOCKED.toString());
		return setNameAndLore(getIcon().clone(), getDisplayName(), str);
	}
	
	@Override
	public void onItemClick(ItemClickEvent event)
	{
		final Player player = event.getPlayer();
		if(player != null)
		{
			event.setWillClose(true);
			final AnniPlayer anniplayer = AnniPlayer.getPlayer(player.getUniqueId());
			if(kit != null && anniplayer != null)
			{
				if(kit.hasPermission(player))
				{
					if(Game.isGameRunning() && anniplayer.getKit() != null)
						anniplayer.getKit().cleanup(player);
					anniplayer.setKit(kit);
					player.sendMessage(Lang.SELECTED.toString().replaceAll("%KIT%", kit.getName()));
					if(Game.isGameRunning() && anniplayer.getTeam() != null)
						player.setHealth(0);
				}
				else player.sendMessage(Lang.CANT_SELECT_KIT.toString());
			}
		}	
	}
}
