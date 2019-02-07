package com.gmail.lynx7478.anni.kits;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.gmail.lynx7478.anni.main.AnnihilationMain;
import com.gmail.lynx7478.anni.main.Lang;
import com.gmail.lynx7478.anni.utils.VersionUtils;

public class CivilianKit extends Kit
{
	public CivilianKit()
	{
		Bukkit.getPluginManager().registerEvents(this, AnnihilationMain.getInstance());
		//AnniEvent.registerListener(this);
		this.Initialize();
	}
	
	private Loadout loadout;
	
	@Override
	public boolean Initialize()
	{
		Material mat = null;
		if(!VersionUtils.getVersion().contains("13"))
		{
			mat = Material.WORKBENCH;
		}else
		{
			try {
				mat = (Material) Enum.valueOf((Class<Enum>) Class.forName("org.bukkit.Material"), "CRAFTING_TABLE");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		loadout = new Loadout().addWoodSword().addWoodPick().addWoodAxe().addSoulboundItem(new ItemStack(mat)).addNavCompass().finalizeLoadout();
		return true;
	}

	@Override
	public String getDisplayName()
	{
		return Lang.CIVILIANNAME.toString();
	}

	@Override
	public IconPackage getIconPackage()
	{
		Material mat = null;
		if(!VersionUtils.getVersion().contains("13"))
		{
			mat = Material.WORKBENCH;
		}else
		{
			try {
				mat = (Material) Enum.valueOf((Class<Enum>) Class.forName("org.bukkit.Material"), "CRAFTING_TABLE");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		return new IconPackage(new ItemStack(mat), Lang.CIVILIANLORE.toStringArray());
//		return new IconPackage(new ItemStack(Material.WORKBENCH), 
//				new String[]{	aqua+"You are the backbone.", 
//								"",
//								aqua+"Fuel all facets of the",
//								aqua+"war machine with your",
//								aqua+"set of wooden tools and", 
//								aqua+"prepare for battle!" 
//							});
	}

	@Override
	public void onPlayerSpawn(Player player)
	{
//		KitUtils.giveTeamArmor(player);
//		player.getInventory().addItem(Sword);
//		player.getInventory().addItem(Pick);
//		player.getInventory().addItem(Axe);
//		player.getInventory().addItem(CraftingTable);
//		player.getInventory().addItem(KitUtils.getNavCompass());
		loadout.giveLoadout(player);
	}

	@Override
	public void cleanup(Player player)
	{
		
	}

	@Override
	public boolean hasPermission(Player player)
	{
		return true;
	}
}
