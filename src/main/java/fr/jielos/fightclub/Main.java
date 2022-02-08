package fr.jielos.fightclub;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Difficulty;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.spigotmc.SpigotConfig;

import fr.jielos.fightclub.components.ScoreboardSign;
import fr.jielos.fightclub.game.Game;
import fr.jielos.fightclub.managers.CommandsManager;
import fr.jielos.fightclub.managers.ListenersManager;
import fr.jielos.fightclub.references.Messages;

public class Main extends JavaPlugin {
	
	private static Main instance;
	public static Main getInstance() {
		return instance;
	}
	
	private Game game;
	public Game getGame() {
		return game;
	}
	
	private Map<Player, ScoreboardSign> scoreboards = new HashMap<>();
	public Map<Player, ScoreboardSign> getScoreboards() {
		return scoreboards;
	}
	
	@Override
	public void onEnable() {
		instance = this;
		
		SpigotConfig.unknownCommandMessage = (Messages.UNKNOWN_COMMAND.getContent());
		SpigotConfig.whitelistMessage = (Messages.KICK_WHITELIST.getContent());
		SpigotConfig.tabComplete = (-1);
		
		game = new Game(this);
		
		getServer().getWorlds().get(0).setTime(6000);
		getServer().getWorlds().get(0).setDifficulty(Difficulty.NORMAL);
		getServer().getMessenger().registerOutgoingPluginChannel(this, ("SuniGames"));
		getServer().getWorlds().get(0).setGameRuleValue("doDaylightCycle", "false");
		for(Entity entity : getServer().getWorlds().get(0).getEntities()) if(!(entity instanceof Player)) entity.remove();
		
		new CommandsManager(this).register();
		new ListenersManager(this).register();
	}
}