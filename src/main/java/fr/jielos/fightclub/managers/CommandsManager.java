package fr.jielos.fightclub.managers;

import org.bukkit.plugin.java.JavaPlugin;

import fr.jielos.fightclub.commands.CommandForcestart;

public class CommandsManager {

	final JavaPlugin instance;
	public CommandsManager(final JavaPlugin instance) {
		this.instance = instance;
	}
	
	public void register() {
		this.instance.getCommand("forcestart").setExecutor(new CommandForcestart());
	}
}