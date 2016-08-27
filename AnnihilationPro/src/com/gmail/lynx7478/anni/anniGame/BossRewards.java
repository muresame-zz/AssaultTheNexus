package com.gmail.lynx7478.anni.anniGame;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.gmail.lynx7478.anni.anniMap.GameMap;
import com.gmail.lynx7478.anni.main.AnnihilationMain;

public class BossRewards implements Listener {
	
	private Inventory inventory;
	
	public BossRewards()
	{
		inventory = Bukkit.createInventory(null, 27, ChatColor.DARK_PURPLE+"Boss Rewards setter.");
		if(Game.getGameMap().getBossRewards() != null)
		{
			this.inventory.setContents(Game.getGameMap().getBossRewards());
		}
		
		this.inventory.setItem(26, finish());
		this.inventory.setItem(25, discard());
		
		AnnihilationMain.getInstance().getServer().getPluginManager().registerEvents(this, AnnihilationMain.getInstance());
	}
	
	@EventHandler
	public void onItemClick(InventoryClickEvent e)
	{
		if(e.getInventory() == null || e.getClickedInventory() == null
				|| e.getCurrentItem() == null || !e.getCurrentItem().hasItemMeta()
				|| !e.getCurrentItem().getItemMeta().hasDisplayName())
		{
			return;
		}
		if(e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.GREEN+"Finish"))
		{
			GameMap map = Game.getGameMap();
			e.setCancelled(true);
			e.getWhoClicked().closeInventory();
			for(ItemStack i : this.inventory)
			{
				if(i == null)
				{
					this.inventory.remove(i);
				}
				if(i != null && i.hasItemMeta() && i.getItemMeta().hasDisplayName())
				{
					if(i.getItemMeta().getDisplayName().equals(ChatColor.GREEN+"Finish") || i.getItemMeta().getDisplayName().equals(ChatColor.RED+"Discard"))
					{
						this.inventory.remove(i);
					}
				}
			}
			map.setBossRewards(this.inventory.getContents());
			this.cleanup();
		}else if(e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.RED+"Discard"))
		{
			e.getWhoClicked().closeInventory();
			return;
		}else
		{
			return;
		}
	}
	
	private ItemStack finish()
	{
		ItemStack a = new ItemStack(Material.WOOL, 1, (byte)5);
		ItemMeta m = a.getItemMeta();
		m.setDisplayName(ChatColor.GREEN+"Finish");
		a.setItemMeta(m);
		return a;
	}
	
	private ItemStack discard()
	{
		ItemStack a = new ItemStack(Material.WOOL, 1, (byte)14);
		ItemMeta m = a.getItemMeta();
		m.setDisplayName(ChatColor.RED+"Discard");
		a.setItemMeta(m);
		return a;
	}
	
	public void openInventory(Player p)
	{
		p.openInventory(this.inventory);
	}
	
	public void cleanup()
	{
		InventoryClickEvent.getHandlerList().unregister(this);
	}
}