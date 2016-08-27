package com.gmail.lynx7478.anni.boss;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public class Boss {
	
	private Location loc;
	
	private String name;
	
	private int health;
	
	public static ItemStack reward1;
	public static ItemStack reward2;
	public static ItemStack reward3;
	public static ItemStack reward4;
	public static ItemStack reward5;
	public static ItemStack reward6;
	
	public Boss(Location location, String name, int health){
		this.loc = location;
		this.name = name;
		this.health = health;
		
	}
	
	public static final void setup(){
		reward1 = new ItemStack(Material.DIAMOND_SWORD);
		reward1.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 4);
		reward2 = new ItemStack(Material.DIAMOND_PICKAXE);
		reward2.addUnsafeEnchantment(Enchantment.DIG_SPEED, 4);
		reward3 = new ItemStack(Material.DIAMOND_HELMET);
		reward3.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
		
		reward4 = new ItemStack(Material.DIAMOND_CHESTPLATE);
		reward4.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
		
		reward5 = new ItemStack(Material.DIAMOND_LEGGINGS);
		reward5.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
		
		reward6 = new ItemStack(Material.DIAMOND_BOOTS);
		reward6.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
	}
	
	/** @EventHandler(priority=EventPriority.HIGHEST)
	public void onBossDeath(EntityDeathEvent e){
		if(e.getEntity().getKiller() instanceof Player){
			Player p = (Player) e.getEntity().getKiller();
			if(e.getEntity() instanceof IronGolem){
				IronGolem i = (IronGolem) e.getEntity();
				String loot;
				int random = (int) (Math.random() * 6 + 1);
				if(random == 1){
					loot = "Boss Sword";
					p.getInventory().addItem(reward1);
				}else if(random == 2){
					loot = "Boss Pickaxe";
					p.getInventory().addItem(reward2);
				}else if(random == 3){
					loot = "Boss Helmet";
					p.getInventory().addItem(reward3);
				}else if(random == 4){
					loot = "Boss Chestplate";
					p.getInventory().addItem(reward4);
				}else if(random == 5){
					loot = "Boss Leggings";
					p.getInventory().addItem(reward5);
				}else if(random == 6){
					loot = "Boss Boots";
					p.getInventory().addItem(reward6);
				}
			}
		}
	} **/

}
