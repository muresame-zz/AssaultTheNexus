package com.gmail.lynx7478.anni.anniGame;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import javax.imageio.ImageIO;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import ru.tehkode.permissions.bukkit.PermissionsEx;

import com.bobacadodl.imgmessage.ImageChar;
import com.bobacadodl.imgmessage.ImageMessage;
import com.gmail.lynx7478.anni.anniEvents.AnniEvent;
import com.gmail.lynx7478.anni.anniEvents.PlayerKilledEvent;
import com.gmail.lynx7478.anni.anniEvents.PlayerKilledEvent.KillAttribute;
import com.gmail.lynx7478.anni.anniGame.autoRespawn.RespawnHandler;
import com.gmail.lynx7478.anni.anniMap.GameMap;
import com.gmail.lynx7478.anni.enderchest.EnderChest;
import com.gmail.lynx7478.anni.main.AnnihilationMain;
import com.gmail.lynx7478.anni.main.Lang;
import com.gmail.lynx7478.anni.utils.Loc;
import com.gmail.lynx7478.anni.utils.VersionUtils;

public class GameListeners implements Listener
{
	
	private ArrayList<BrewingStand> brewingStands;
	
	private ArrayList<String> offlinePlayers;
	
	public GameListeners(Plugin p)
	{
        Bukkit.getPluginManager().registerEvents(this, p);
        RespawnHandler.register(p);

		String version = VersionUtils.getVersion();
		if(version.contains("v1_8") || version.contains("v1_9") || version.contains("v1_10") || version.contains("v1_11") || version.contains("v1_12"))
			new ArmorStandListener(p);
		offlinePlayers = new ArrayList<String>();
		brewingStands = new ArrayList<BrewingStand>();
	}

	@EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=true)
	public void pingServer(ServerListPingEvent event)
	{
		if(GameVars.useMOTD())
		{
			if(Game.getGameMap() == null || Game.getGameMap().getCurrentPhase() < 1)
				event.setMotd("In Lobby");
			else event.setMotd("Phase "+Game.getGameMap().getCurrentPhase());
		}
	}
	
	//should make players instantly respawn
	@EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=true)
	public void deathHandler(PlayerDeathEvent event)
	{
		if(Hardcore.isCompetitive)
		{
			return;
		}
		final Player player = event.getEntity();
		final AnniPlayer p = AnniPlayer.getPlayer(player.getUniqueId());
		if(p != null)
			p.getKit().cleanup(player);
	}
	
	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void AnniPlayersInit(PlayerCommandPreprocessEvent event)
	{
		final String[] args = event.getMessage().split(" ");
		if(args[0].equalsIgnoreCase("/tp"))
		{
			Player player = event.getPlayer();
			if(player.hasPermission("A.anni"))
			{
				if(args.length > 1)
				{
					AnniTeam team = AnniTeam.getTeamByName(args[1]);
					if(team != null)
					{
						Loc loc = team.getSpectatorLocation();
						if(loc != null)
						{
							event.setCancelled(true);
							player.teleport(loc.toLocation());
						}
					}
					else if(args[1].equalsIgnoreCase("lobby"))
					{
						if(Game.LobbyMap != null)
						{
							Location lobby = Game.LobbyMap.getSpawn();
							if(lobby != null)
							{
								event.setCancelled(true);
								player.teleport(lobby);
							}
						}
					}
//					else if(args[1].equalsIgnoreCase("map") && player.getName().equals("Mr_Little_Kitty"))
//					{
//						if(args.length > 2)
//						{
//							World w = Game.getWorld(args[2]);
//							if(w != null)
//							{
//								event.setCancelled(true);
//								player.teleport(w.getSpawnLocation());
//							}
//						}
//						else
//						{
//							for(World w : Bukkit.getWorlds())
//								player.sendMessage(w.getName());
//						}
//					}
				}
			}
		}
	}
	
	private final ChatColor g = ChatColor.GRAY;
	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void AnniPlayersInit(AsyncPlayerChatEvent event)
	{
		if(event.isAsynchronous())
		{
			if(event.getMessage().equals("versionpls"))
			{
				event.getPlayer().sendMessage(VersionUtils.getVersion());
			}
			AnniPlayer p = AnniPlayer.getPlayer(event.getPlayer().getUniqueId());
			if(p.getTeam() == null)
				if(!AnnihilationMain.getInstance().hasPEX() && !AnnihilationMain.getInstance().getGroupManager().hasGroupManager())
				{
					event.setFormat(g+"(" + Lang.ALL + ")" + " ["+ChatColor.DARK_PURPLE+Lang.LOBBY+g+"]" + g + " %s"+ChatColor.WHITE+": %s");
				}else if(AnnihilationMain.getInstance().getGroupManager().hasGroupManager())
				{
					event.setFormat(g+"(" + Lang.ALL + ")" + " ["+ChatColor.DARK_PURPLE+Lang.LOBBY+g+"]" + g + ChatColor.translateAlternateColorCodes('&', AnnihilationMain.getInstance().getGroupManager().getPrefix(p.getPlayer()))+" %s"+ChatColor.WHITE+": %s");
				}else
				{
					event.setFormat(g+"(" + Lang.ALL + ")" + " ["+ChatColor.DARK_PURPLE+Lang.LOBBY+g+"]" + g + ChatColor.translateAlternateColorCodes('&', PermissionsEx.getUser(p.getPlayer()).getPrefix())+" %s"+ChatColor.WHITE+": %s");
				}
			else if(event.getMessage().startsWith("!"))
			{
				event.setMessage(event.getMessage().substring(1));
				if(!AnnihilationMain.getInstance().hasPEX() && !AnnihilationMain.getInstance().getGroupManager().hasGroupManager())
				{
					event.setFormat(g+"(" + Lang.ALL + ")" + " ["+p.getTeam().getColoredName()+g+"]" + g +" %s"+ChatColor.WHITE+": %s");
				}else if(AnnihilationMain.getInstance().getGroupManager().hasGroupManager())
				{
					event.setFormat(g+"(" + Lang.ALL + ")" + " ["+p.getTeam().getColoredName()+g+"]" + g + ChatColor.translateAlternateColorCodes('&', AnnihilationMain.getInstance().getGroupManager().getPrefix(p.getPlayer()))+" %s"+ChatColor.WHITE+": %s");
				}else
				{
					event.setFormat(g+"(" + Lang.ALL + ")" + " ["+p.getTeam().getColoredName()+g+"]" + g + ChatColor.translateAlternateColorCodes('&', PermissionsEx.getUser(p.getPlayer()).getPrefix())+" %s"+ChatColor.WHITE+": %s");
				}
			}
			else
			{
				if(!AnnihilationMain.getInstance().hasPEX() && !AnnihilationMain.getInstance().getGroupManager().hasGroupManager())
				{
					event.setFormat(g+"(" + Lang.TEAM + ")" +  " ["+p.getTeam().getColor()+p.getTeam().toString()+g+"]"+g+ " %s"+ChatColor.WHITE+": %s");
				}else if(AnnihilationMain.getInstance().getGroupManager().hasGroupManager())
				{
					event.setFormat(g+"(" + Lang.TEAM + ")" +  " ["+p.getTeam().getColor()+p.getTeam().toString()+g+"]"+g+ ChatColor.translateAlternateColorCodes('&', AnnihilationMain.getInstance().getGroupManager().getPrefix(p.getPlayer()))+" %s"+ChatColor.WHITE+": %s");
				}else
				{
					event.setFormat(g+"(" + Lang.TEAM + ")" +  " ["+p.getTeam().getColor()+p.getTeam().toString()+g+"]"+g+ ChatColor.translateAlternateColorCodes('&', PermissionsEx.getUser(p.getPlayer()).getPrefix())+" %s"+ChatColor.WHITE+": %s");
				}
				event.getRecipients().clear();
				for(AnniPlayer pl : p.getTeam().getPlayers())
					if(pl.isOnline())
						event.getRecipients().add(pl.getPlayer());
				for(Player pl : Bukkit.getOnlinePlayers())
					if(pl.hasPermission("Anni.ChatSpy"))
						event.getRecipients().add(pl);
			}
		}
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void DeathListener(PlayerDeathEvent event)
	{
		if(Hardcore.isCompetitive)
		{
			return;
		}
		String message = "";
		Player player = event.getEntity();
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
						PlayerKilledEvent e = new PlayerKilledEvent(k,p);//TODO--------This should be created earlier, then the message should be based off of attributes computed by the event
						
						message = p.getTeam().getColor()+player.getName()+"("+p.getKit().getName()+") "+Lang.DEATHPHRASE.toString()+" "+k.getTeam().getColor()+killer.getName()+"("+k.getKit().getName()+")";
						
						if(e.getAttributes().contains(KillAttribute.REMEMBRANCE))
							message += " "+Lang.REMEMBRANCE.toString();
						else if(e.getAttributes().contains(KillAttribute.NEXUSDEFENSE))
							message += " "+Lang.NEXUSKILL.toString();
							
						AnniEvent.callEvent(e);
						if(!e.shouldDropXP())
							event.setDroppedExp(0);
					}	
				}
				else 
					event.setDroppedExp(0);
				event.setDeathMessage(message);
			}
		}
	}
	
	@EventHandler(priority=EventPriority.MONITOR,ignoreCancelled = true)
	public void teleportToLobbyThing(PlayerJoinEvent event)
	{
		final Player pl = event.getPlayer();
		if(this.offlinePlayers.contains(pl.getName()))
		{
			pl.teleport(AnniPlayer.getPlayer(pl.getUniqueId()).getTeam().getRandomSpawn());
		}
		if(EnderChest.getChestFor(pl) == null)
		{
			new EnderChest(pl);
		}
		if(Game.LobbyMap != null && Game.LobbyMap.getSpawn() != null)
		{
			final AnniPlayer p = AnniPlayer.getPlayer(pl.getUniqueId());
			if(p != null )//&& Game.GameWorld != null)
			{
				/** if(p.getName().equals("Expl0itBypass") || p.getName().equals("Mr_Little_Kitty") || p.getName().equals("CUInOverwatch"))
				{
					Bukkit.broadcastMessage(ChatColor.RED + "You have been visited by the author of the Annihilation plugin!");
				} **/
				if(!Game.isGameRunning() || p.getTeam() == null || p.getTeam().isTeamDead() || pl.getLocation().getWorld().getName().equalsIgnoreCase(Game.LobbyMap.getWorldName()))
				{
					new BukkitRunnable()
					{
						@Override
						public void run()
						{
							//Check if the lobbymap is not null when this actually runs
							if(Game.LobbyMap != null)
								Game.LobbyMap.sendToSpawn(pl);
							
//							pl.getInventory().clear();
//							pl.getInventory().setArmorContents(null);
//							pl.teleport(Game.LobbyLocation);
//							pl.getInventory().addItem(CustomItem.KITMAP.toItemStack(1));
//							pl.setHealth(pl.getMaxHealth());
//							pl.setFoodLevel(20);
							//pl.setGameMode(GameMode.ADVENTURE);
						}
					}.runTaskLater(AnnihilationMain.getInstance(),20L);
				}
			}
		}
	}
	
	//should set the respawn point of a player
	@EventHandler(priority=EventPriority.HIGHEST,ignoreCancelled = true)
	public void respawnHandler(PlayerRespawnEvent event)
	{
		if(Hardcore.isCompetitive)
		{
			return;
		}
		final Player player = event.getPlayer();
		final AnniPlayer p = AnniPlayer.getPlayer(player.getUniqueId());
		if(p != null)
		{
			if(Game.isGameRunning())
			{
				if(p.getTeam() != null && !p.getTeam().isTeamDead())
				{
					event.setRespawnLocation(p.getTeam().getRandomSpawn());
					p.getKit().onPlayerSpawn(player);
					player.setExp(0.0F);
					return;
				}
			}
			if(Game.LobbyMap != null && Game.LobbyMap.getSpawn() != null)
				event.setRespawnLocation(Game.LobbyMap.getSpawn());  //Set people to respawn in the lobby
		}
	}
	
	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent e){
		Player p = (Player) e.getPlayer();
		if(GameVars.getKillOnLeave()){
			p.setHealth(0.0);
		}
		this.offlinePlayers.add(p.getName());
	}
	
	@EventHandler
	public void onEntityDeath(EntityDeathEvent e)
	{
		if(e.getEntity() == null)
		{
			return;
		}
		if(e.getEntity().getKiller() == null)
		{
			return;
		}
		if(!e.getEntity().hasMetadata("Boss"))
		{
			return;
		}
		Player p = (Player) e.getEntity().getKiller();
		AnniPlayer aP = AnniPlayer.getPlayer(p.getUniqueId());
		p.getInventory().addItem(this.getReward());
      	 for(Player pl : Bukkit.getOnlinePlayers())
      	 {
				try {
					BufferedImage imageToSend = ImageIO.read(AnnihilationMain.getInstance().getResource("Images/Face.png"));
					String[] text = Lang.BOSSKILLSMESSAGE.toStringArray();
					for(int i = 0; i < text.length; i++)
					{
						if(text[i].contains("%PLAYER%"))
						{
							text[i] = text[i].replace("%PLAYER%", aP.getTeam().getColor()+p.getName());
						}
					}
					ImageMessage msg = new ImageMessage(imageToSend, 10, ImageChar.MEDIUM_SHADE.getChar());
					msg.appendTextToLines(text.length, text);
					msg.sendToPlayer(pl);
					
				} catch (IOException ex) {
					ex.printStackTrace();
				}
      	 }
	}
	
	@EventHandler
	public void onBrewingStandPlace(BlockPlaceEvent e)
	{
		if(!GameVars.usePrivateBrewingStands())
		{
			return;
		}
		
		if(e.getBlock().getType() != Material.BREWING_STAND)
		{
			return;
		}
		
		AnniPlayer p = AnniPlayer.getPlayer(e.getPlayer().getUniqueId());
		this.brewingStands.add(new BrewingStand(e.getBlock().getLocation(), p));
		p.getPlayer().sendMessage(Lang.BREWING_PLACE.toString());
		
	}
	
	@EventHandler
	public void onBrewingStandInteract(PlayerInteractEvent e)
	{
		
		if(e.getPlayer().getGameMode() == GameMode.CREATIVE || e.getPlayer().hasPermission("Anni.OpenBrewingStands") || e.getPlayer().isOp())
		{
			return;
		}
		
		if(!GameVars.usePrivateBrewingStands())
		{
			return;
		}
		
		if(e.getClickedBlock() == null || e.getClickedBlock().getType() == Material.AIR)
		{
			return;
		}
		
		if(e.getAction() != Action.RIGHT_CLICK_BLOCK)
		{
			return;
		}
		
		if(e.getClickedBlock().getType() == Material.BREWING_STAND)
		{
			AnniPlayer p = AnniPlayer.getPlayer(e.getPlayer().getUniqueId());
			
			for(BrewingStand b : brewingStands)
			{
				if(b.getLocation().equals(e.getClickedBlock().getLocation()))
				{
					if(!b.getOwner().equals(p) && b.getOwner().getTeam() == p.getTeam())
					{
						e.setCancelled(true);
						p.getPlayer().sendMessage(Lang.BREWING_OPEN_FAIL.toString());
						break;
					}else
					{
						break;
					}
				}
			}
			return;
		}
	}
	
	@EventHandler
	public void onBrewingStandBreak(BlockBreakEvent e)
	{
		if(!GameVars.usePrivateBrewingStands())
		{
			return;
		}
		
		if(e.getPlayer().getGameMode() == GameMode.CREATIVE || e.getPlayer().hasPermission("Anni.OpenBrewingStands") || e.getPlayer().isOp())
		{
			return;
		}
		
		if(e.getBlock().getType() == null || e.getBlock().getType() == Material.AIR)
		{
			return;
		}
		
		if(e.getBlock().getType() == Material.BREWING_STAND)
		{
			AnniPlayer aP = AnniPlayer.getPlayer(e.getPlayer().getUniqueId());
			
			for(BrewingStand b : brewingStands)
			{
				if(b.getLocation().equals(e.getBlock().getLocation()))
				{
					if(!b.getOwner().equals(aP) && b.getOwner().getTeam() == aP.getTeam())
					{
						e.setCancelled(true);
						aP.getPlayer().sendMessage(Lang.BREWING_BREAK_FAIL.toString());
						break;
					}else
					{
						brewingStands.remove(b);
						aP.getPlayer().sendMessage(Lang.BREWING_BREAK.toString());
						break;
					}
				}
			}
			return;
		}
	}
	
	private ItemStack getReward()
	{
		GameMap map = Game.getGameMap();
		int ran = this.getRandom(map.getBossRewards().length);
		if(map.getBossRewards()[ran] == null)
		{
			return this.getReward();
		}
		return map.getBossRewards()[ran];
	}
	
	private int getRandom(int max)
	{
		Random rand = new Random();
		return rand.nextInt(max);
	}
}