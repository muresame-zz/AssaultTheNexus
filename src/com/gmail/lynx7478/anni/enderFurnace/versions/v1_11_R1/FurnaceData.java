package com.gmail.lynx7478.anni.enderFurnace.versions.v1_11_R1;

import net.minecraft.server.v1_11_R1.TileEntityFurnace;

class FurnaceData extends com.gmail.lynx7478.anni.enderFurnace.api.FurnaceData
{
	
	private static TileEntityFurnace furnace;
	
    public FurnaceData(TileEntityFurnace furnace)
    {
        super(asBukkitCopy(getStacksAsCollection()),furnace.getProperty(0),furnace.getProperty(1),furnace.getProperty(2));
        furnace = furnace;
    }

    private static org.bukkit.inventory.ItemStack[] asBukkitCopy(net.minecraft.server.v1_11_R1.ItemStack[] stacks)
    {
        org.bukkit.inventory.ItemStack[] items = new org.bukkit.inventory.ItemStack[stacks.length];
        for(int i = 0; i < items.length; i++)
        {
            items[i] = org.bukkit.craftbukkit.v1_11_R1.inventory.CraftItemStack.asBukkitCopy(stacks[i]);
        }
        return items;
    }
    
    private static net.minecraft.server.v1_11_R1.ItemStack[] getStacksAsCollection()
    {
    	net.minecraft.server.v1_11_R1.ItemStack[] stacks = new net.minecraft.server.v1_11_R1.ItemStack[furnace.getContents().size()];
    	int num = 0;
    	for(net.minecraft.server.v1_11_R1.ItemStack i : furnace.getContents())
    	{
    		if(num>furnace.getContents().size())
    		{
    			break;
    		}else
    		{
    			stacks[num] = i;
    		}
    	}
    	return stacks;
    }
}
