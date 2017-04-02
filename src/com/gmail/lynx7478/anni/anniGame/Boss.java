package com.gmail.lynx7478.anni.anniGame;

import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.metadata.FixedMetadataValue;

import com.gmail.lynx7478.anni.anniMap.GameMap;
import com.gmail.lynx7478.anni.main.AnnihilationMain;

public class Boss {
	
	private GameMap map;
	public Boss()
	{
		this.map = Game.getGameMap();
		LivingEntity g1 = (LivingEntity) map.getWorld().spawn(map.getBoss1Loc().toLocation(), map.getBoss1Class());
		g1.setMaxHealth(Double.MAX_VALUE);
		g1.setHealth(map.getBoss1HP());
		g1.setCustomName(map.getBoss1Name());
		g1.setCustomNameVisible(true);
		g1.setMetadata("Boss", new FixedMetadataValue(AnnihilationMain.getInstance(), "s"));
		map.setBoss1(g1);
		
		LivingEntity g2 = (LivingEntity) map.getWorld().spawn(map.getBoss2Loc().toLocation(), map.getBoss2Class());
		g2.setMaxHealth(Double.MAX_VALUE);
		g2.setHealth(map.getBoss2HP());
		g2.setCustomName(map.getBoss2Name());
		g2.setCustomNameVisible(true);
		g2.setMetadata("Boss", new FixedMetadataValue(AnnihilationMain.getInstance(), "s"));
		map.setBoss2(g2);
		
		Bukkit.getScheduler().runTaskLater(AnnihilationMain.getInstance(), new Runnable()
				{
			public void run()
			{
				new Boss();
			}
				}, map.getBossRespawnTime() * 20);
	}

}