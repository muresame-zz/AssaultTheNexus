package com.gmail.lynx7478.anni.enderchest;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

import com.gmail.lynx7478.anni.anniGame.GameVars;
import com.gmail.lynx7478.anni.main.AnnihilationMain;
import com.gmail.lynx7478.anni.utils.VersionUtils;

public class EnderChest implements Listener {
	
	private static HashMap<UUID, EnderChest> chests;

	static{
		chests = new HashMap<UUID, EnderChest>();
	}
	
	public static EnderChest getChestFor(Player p)
	{
		return chests.get(p.getUniqueId());
	}
	
	public EnderChest()
	{
		AnnihilationMain.getInstance().getServer().getPluginManager().registerEvents(this, AnnihilationMain.getInstance());
	}
	
	@SuppressWarnings("unchecked")
	//TODO: EnderChest
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e){
		Player p = (Player) e.getPlayer();
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK){
			if(e.getClickedBlock().getType() == Material.ENDER_CHEST){
				if(GameVars.getUseEC()){
					e.setCancelled(true);
					if(VersionUtils.getVersion().contains("v1_9")){
						try {
							Class<Enum> cls = (Class<Enum>) Class.forName("org.bukkit.Sound");
							p.playSound(p.getLocation(), (Sound) Enum.valueOf(cls, "BLOCK_CHEST_OPEN"), 10F, 10F);
						} catch (ClassNotFoundException e1) {
							e1.printStackTrace();
						}
						
					}else{
						p.playSound(p.getLocation(), Sound.CHEST_OPEN, 10F, 10F);
					}
					EnderChest.getChestFor(p).open();
				}else{
					e.setCancelled(true);
				}
			}
		}
	}
	
	private Inventory inv;
	private Player owner;
	
	public EnderChest(Player owner){
		this.owner = owner;
		inv = Bukkit.createInventory(owner, GameVars.getECSlots(), "Private ender chest");
		chests.put(owner.getUniqueId(), this);
	}
	
	public void open(){
		owner.openInventory(inv);
	}
	
	public Inventory getInventory(){
		return inv;
	}
	
	public Player getOwner(){
		return owner;
	}

}