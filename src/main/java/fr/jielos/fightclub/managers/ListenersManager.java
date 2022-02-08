package fr.jielos.fightclub.managers;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import fr.jielos.fightclub.listeners.EntityDamage;
import fr.jielos.fightclub.listeners.FoodLevelChange;
import fr.jielos.fightclub.listeners.PlayerDeath;
import fr.jielos.fightclub.listeners.PlayerJoin;
import fr.jielos.fightclub.listeners.PlayerLogin;
import fr.jielos.fightclub.listeners.PlayerMove;
import fr.jielos.fightclub.listeners.PlayerQuit;

public class ListenersManager {

	final JavaPlugin instance;
	public ListenersManager(final JavaPlugin instance) {
		this.instance = instance;
	}
	
	public void register() {
		final PluginManager pluginManager = instance.getServer().getPluginManager();
		
		pluginManager.registerEvents(new PlayerLogin(), instance);
		pluginManager.registerEvents(new PlayerJoin(), instance);
		pluginManager.registerEvents(new PlayerQuit(), instance);
		pluginManager.registerEvents(new PlayerMove(), instance);
		pluginManager.registerEvents(new PlayerDeath(), instance);
		
		pluginManager.registerEvents(new FoodLevelChange(), instance);
		pluginManager.registerEvents(new EntityDamage(), instance);
	}
}