package com.gmail.lynx7478.anni.main;

import org.bukkit.command.CommandSender;

import com.gmail.lynx7478.anni.itemMenus.MenuItem;

public interface AnniArgument
{
	String getHelp();
	boolean useByPlayerOnly();
	String getArgumentName();
	void executeCommand(CommandSender sender, String label, String[] args);
	String getPermission();
	MenuItem getMenuItem();
}