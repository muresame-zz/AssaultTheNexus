package com.gmail.lynx7478.anni.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffectType;

import com.gmail.lynx7478.anni.anniEvents.NexusHitEvent;
import com.gmail.lynx7478.anni.anniGame.AnniPlayer;
import com.gmail.lynx7478.anni.anniGame.GameVars;
import com.gmail.lynx7478.anni.main.Lang;

public class InvisibilityListeners implements Listener
{
	public InvisibilityListeners(Plugin plugin)
	{
		Bukkit.getPluginManager().registerEvents(this, plugin);
//		AnniEvent.registerListener(this);
    }
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void blockBreakingChecker(final BlockBreakEvent event)
	{
		Player player = event.getPlayer();
		AnniPlayer p = AnniPlayer.getPlayer(player.getUniqueId());
		if(p != null && GameVars.getRevealOnBreak() != false)
			checkInvis(player);
	}
	
	@EventHandler
	public void nexuChecker(NexusHitEvent event)
	{
		Player p = event.getPlayer().getPlayer();
		if(p != null && GameVars.getRevealOnBreak() != false)
			checkInvis(p);
	}
	
//	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
//	public void blockPlacingChecker(final BlockPlaceEvent event)
//	{
//		Player player = event.getPlayer();
//		AnniPlayer p = AnniPlayer.getPlayer(player.getUniqueId());
//		if(p != null)
//			checkInvis(player);
//	}
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void playerDamageChecker(final EntityDamageEvent event)
	{
		if(event.getEntityType() == EntityType.PLAYER)
		{
			Player player = (Player)event.getEntity();
			AnniPlayer p = AnniPlayer.getPlayer(player.getUniqueId());
			if(p != null && GameVars.getRevealOnDamage() != false)
				checkInvis(player);
		}
	}
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void playerDamageByPlayerChecker(final EntityDamageByEntityEvent e)
	{
		if(e.getEntity() instanceof Player && e.getDamager() instanceof Player)
		{
			Player player = (Player) e.getEntity();
			AnniPlayer p = AnniPlayer.getPlayer(player.getUniqueId());
			if(p != null && GameVars.getRevealOnHit() != false)
				checkInvis(player);
		}
	}
	
	private void checkInvis(Player player)
	{
		if(player.hasPotionEffect(PotionEffectType.INVISIBILITY))
		{
			player.removePotionEffect(PotionEffectType.INVISIBILITY);
			player.sendMessage(Lang.INVISREVEAL.toString());
		}
	}
}