package com.gmail.lynx7478.anni.anniGame;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import com.bobacadodl.imgmessage.ImageChar;
import com.bobacadodl.imgmessage.ImageMessage;
import com.gmail.lynx7478.anni.anniEvents.AnniEvent;
import com.gmail.lynx7478.anni.anniEvents.GameEndEvent;
import com.gmail.lynx7478.anni.anniEvents.NexusHitEvent;
import com.gmail.lynx7478.anni.announcementBar.AnnounceBar;
import com.gmail.lynx7478.anni.main.AnnihilationMain;
import com.gmail.lynx7478.anni.main.Lang;
import com.gmail.lynx7478.anni.utils.Loc;
import com.gmail.lynx7478.anni.utils.VersionUtils;
import com.gmail.lynx7478.anni.voting.ScoreboardAPI;

public class Nexus implements Listener
{
	
	private Sound anvil;
	private Sound explode;
	private Sound note;
	
	public Nexus(AnniTeam team)
	{
		this.Team = team;
		this.Location = null;
		
		if(!VersionUtils.getVersion().contains("13") && !VersionUtils.getVersion().contains("v1_9") && !VersionUtils.getVersion().contains("v1_11") && !VersionUtils.getVersion().contains("v1_10"))
		{
			anvil = Sound.ANVIL_LAND;
			explode = Sound.EXPLODE;
			note = Sound.NOTE_PIANO;
		}else
		{
			try {
				anvil = (Sound) Enum.valueOf((Class<Enum>) Class.forName("org.bukkit.Sound"), "BLOCK_ANVIL_LAND");
				explode = (Sound) Enum.valueOf((Class<Enum>) Class.forName("org.bukkit.Sound"), "ENTITY_GENERIC_EXPLODE");
				note = (Sound) Enum.valueOf((Class<Enum>) Class.forName("org.bukkit.Sound"), "BLOCK_NOTE_BLOCK_XYLOPHONE");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
	public final AnniTeam Team;
	//private ImageMessage message;
	private Loc Location;
	
	public void setLocation(Loc loc)
	{
		this.Location = loc;
	}
	
	public Loc getLocation()
	{
		return Location;
	}
	
	public void gameOverCheck()
	{
		int total = AnniTeam.Teams.length;
		int destroyed = 0;
		AnniTeam winner = null;
		for(AnniTeam team : AnniTeam.Teams)
		{
			if(team.isTeamDead())
				destroyed++;
			else winner = team;
		}
		
		if(destroyed == total-1)
		{
			AnniEvent.callEvent(new GameEndEvent(winner));
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@EventHandler(priority = EventPriority.HIGHEST)
	private void NexusCheck(BlockBreakEvent event)
	{
		if(event.getPlayer().getGameMode() != GameMode.CREATIVE && Game.isGameRunning() && Game.getGameMap() != null)
		{
			if(Location != null && !Team.isTeamDead())
			{
				Location loc = event.getBlock().getLocation();
				if(this.Location.equals(loc))
				{
					event.setCancelled(true);
					if(Game.getGameMap().canDamageNexus())
					{
						AnniPlayer p = AnniPlayer.getPlayer(event.getPlayer().getUniqueId());
						if(p != null && p.getTeam() != null && !p.getTeam().equals(Team))	
						{
							NexusHitEvent e = new NexusHitEvent(p,this,1*Game.getGameMap().getDamageMultiplier());
							AnniEvent.callEvent(e);
							if(!e.isCancelled() && e.getDamage() > 0)
							{
									loc.getWorld().playSound(loc, anvil, 1F, (float)Math.random());
								Team.setHealth(Team.getHealth()-(e.getDamage()));
								
								for(AnniPlayer player : Team.getPlayers())
								{
									Player pl = player.getPlayer();
									if(pl != null)
											pl.playSound(pl.getLocation(), note, 1f, 2.1f);
								}
								String msg = Lang.NEXUSDAMAGE.toString().replaceAll("%PLAYER%", p.getTeam().getColor()+p.getName()).replaceAll("%NEXUS%", this.Team.getExternalColoredName()).replaceAll("%HEALTH%", Integer.toString(this.Team.getHealth()));
								for(Player pl : Bukkit.getOnlinePlayers())
								{
									pl.sendMessage(msg);
									AnnounceBar.getInstance().getBar().sendToPlayer(pl, msg, this.Team.getHealth());
								}
								
								if(Team.isTeamDead())
								{
									ScoreboardAPI.removeTeam(Team);
//									for(AnniPlayer player : AnniPlayer.getPlayers().values())
//									{
//										Player pl = player.getPlayer();
//										if(pl != null)
//
//											pl.getWorld().playSound(pl.getLocation(), Sound.EXPLODE, 1F, .8F);
//									}
									World w = loc.getWorld();
									w.getBlockAt(loc).setType(Material.BEDROCK);
									try
									{
										BufferedImage image = ImageIO.read(AnnihilationMain.getInstance().getResource("Images/"+Team.getName()+"Team.png"));
										String[] lore = new String[]
										{
											"",
											"",
											"",
											"",
											Lang.TEAMDESTROYED.toStringReplacement(Team.getExternalColoredName()),
										};
										ImageMessage message =  new ImageMessage(image, 10, ImageChar.MEDIUM_SHADE.getChar()).appendText(lore);
										for(Player pl : Bukkit.getOnlinePlayers())
										{
												pl.getWorld().playSound(pl.getLocation(), explode, 1F, .8F);
											message.sendToPlayer(pl);
											AnnounceBar.getInstance().getBar().sendToPlayer(pl, p.getTeam().getColor()+p.getName()+ChatColor.GRAY+" has destroyed the  "+this.Team.getColor()+this.Team.getName()+" nexus", 0);
										}
									}
									catch(Throwable t)
									{
										
									}
									gameOverCheck();									
								}
							}
						}
					}
				}
			}
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void deathCheck()
	{
		if(Team.isTeamDead())
		{
			ScoreboardAPI.removeTeam(Team);
//			for(AnniPlayer player : AnniPlayer.getPlayers().values())
//			{
//				Player pl = player.getPlayer();
//				if(pl != null)
//
//					pl.getWorld().playSound(pl.getLocation(), Sound.EXPLODE, 1F, .8F);
//			}
			this.Location.toLocation().getBlock().setType(Material.BEDROCK);
			try
			{
				BufferedImage image = ImageIO.read(AnnihilationMain.getInstance().getResource("Images/"+Team.getName()+"Team.png"));
				String[] lore = new String[]
				{
					"",
					"",
					"",
					"",
					Lang.TEAMDESTROYED.toStringReplacement(Team.getExternalColoredName()),
				};
				ImageMessage message =  new ImageMessage(image, 10, ImageChar.MEDIUM_SHADE.getChar()).appendText(lore);
				for(Player pl : Bukkit.getOnlinePlayers())
				{
						pl.getWorld().playSound(pl.getLocation(), explode, 1F, .8F);
					message.sendToPlayer(pl);
					AnnounceBar.getInstance().getBar().sendToPlayer(pl, ChatColor.GRAY + "The " + this.Team.getColoredName() + " has been defeated due to all players dying.", 0);
				}
			}
			catch(Throwable t)
			{
				
			}
			gameOverCheck();									
		}
	}
}
