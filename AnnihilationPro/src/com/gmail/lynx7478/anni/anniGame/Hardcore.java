package com.gmail.lynx7478.anni.anniGame;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent.Result;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

import com.gmail.lynx7478.anni.anniEvents.AnniEvent;
import com.gmail.lynx7478.anni.anniEvents.PlayerKilledEvent;
import com.gmail.lynx7478.anni.anniEvents.PlayerKilledEvent.KillAttribute;
import com.gmail.lynx7478.anni.enderchest.EnderChest;
import com.gmail.lynx7478.anni.main.AnnihilationMain;
import com.gmail.lynx7478.anni.main.Lang;

public class Hardcore implements Listener {
	
	public static boolean isCompetitive;
	
	private ArrayList<UUID> deadPlayers;
	
	public Hardcore()
	{
		isCompetitive = true;
		deadPlayers = new ArrayList<UUID>();
		AnnihilationMain.getInstance().getServer().getPluginManager().registerEvents(this, AnnihilationMain.getInstance());
	}
	
	@EventHandler
	public void onDeath2LMAO(PlayerDeathEvent e)
	{
		final Player player = e.getEntity();
		final AnniPlayer p = AnniPlayer.getPlayer(player.getUniqueId());
		if(p != null)
			p.getKit().cleanup(player);
	}
	
	@EventHandler
	public void onDeath(PlayerDeathEvent e)
	{
		String message = "";
		Player player = e.getEntity();
		Player killer = player.getKiller();
		
		if(Game.isGameRunning())
		{
			AnniPlayer p = AnniPlayer.getPlayer(player.getUniqueId());
			if(p != null)
			{
				if(killer != null)
				{
					AnniPlayer k = AnniPlayer.getPlayer(killer.getUniqueId());
					if(k != null)
					{
						PlayerKilledEvent ev = new PlayerKilledEvent(k,p);//TODO--------This should be created earlier, then the message should be based off of attributes computed by the event
						
						message = p.getTeam().getColor()+player.getName()+"("+p.getKit().getName()+") "+Lang.DEATHPHRASE.toString()+" "+k.getTeam().getColor()+killer.getName()+"("+k.getKit().getName()+")";
						
						if(ev.getAttributes().contains(KillAttribute.REMEMBRANCE))
							message += " "+Lang.REMEMBRANCE.toString();
						else if(ev.getAttributes().contains(KillAttribute.NEXUSDEFENSE))
							message += " "+Lang.NEXUSKILL.toString();
							
						AnniEvent.callEvent(e);
						if(!ev.shouldDropXP())
							e.setDroppedExp(0);
					}	
				}
				else
				{
					e.setDroppedExp(0);
				e.setDeathMessage(message);
				}
				player.kickPlayer(ChatColor.RED + "You have been killed in a hardcore game. You cannot join again.");
				deadPlayers.add(player.getUniqueId());
				if(p.getTeam().getPlayerCount() == 0)
				{
					p.getTeam().setHealth(0);
				}
				p.getTeam().getNexus().deathCheck();
				
			}
		}
	}
	
	@EventHandler
	public void onLogin(AsyncPlayerPreLoginEvent e)
	{
		Player p = Bukkit.getPlayer(e.getUniqueId());
		if(p.hasPermission("Anni.HardcoreBypass"))
		{
			return;
		}
		if(Game.isGameRunning())
		{
			e.disallow(Result.KICK_OTHER, ChatColor.RED + "You cannot join a hardcore game when it's started.");
		}
		if(this.deadPlayers.contains(e.getUniqueId()))
		{
			e.disallow(Result.KICK_OTHER, ChatColor.RED + "You cannot join a hardcore game when it's started.");
		}
	}

}
