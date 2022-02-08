package fr.jielos.fightclub.listeners;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffect;

import fr.jielos.fightclub.Main;
import fr.jielos.fightclub.components.ActionBar;
import fr.jielos.fightclub.components.ScoreboardSign;
import fr.jielos.fightclub.game.Game;
import fr.jielos.fightclub.references.Config;
import fr.jielos.fightclub.references.Locations;
import fr.jielos.fightclub.references.Scoreboards;

public class PlayerJoin implements Listener {

	@EventHandler
	public void onPlayerJoin(final PlayerJoinEvent event) {
		final Player player = event.getPlayer();
		final Game game = Main.getInstance().getGame();
		
		event.setJoinMessage(null);
		player.teleport(Locations.WATING_AREA.getContent());
		player.setHealth(20); player.setFoodLevel(20);
		player.setLevel(0); player.setExp(0);
		player.getInventory().clear();
		player.getInventory().setArmorContents(null);
		player.spigot().respawn();
		for(PotionEffect potionEffect : player.getActivePotionEffects()) player.removePotionEffect(potionEffect.getType()); 
		
		new ScoreboardSign(player, Scoreboards.DEFAULT).create();;
		
		/* Check if player is in spectators list or not */
		if(!game.getCache().getSpectators().contains(player) && !game.getCache().getPlayers().contains(player)) {
			// Player Mode
			game.getCache().getPlayers().add(player);
			
			player.setGameMode(GameMode.ADVENTURE);
			//player.getInventory().setItem(8, new ItemBuilder(Material.DARK_OAK_DOOR_ITEM).setName("�cQuitter �8- �7Clic-droit").toItemStack());
			
			new ActionBar(("�f"+player.getDisplayName()+" �7a rejoint la partie �8(�a"+game.getCache().getPlayers().size()+"�8/�c"+Config.MAX_PLAYERS.getValue()+"�8)")).send(game.getCache().getPlayers(), 2, 5, 2);
			if(game.getCache().getPlayers().size() >= Config.MIN_PLAYERS.getValue()) game.launch();
		} else if(game.getCache().getSpectators().contains(player)) {
			// Spectator Mode
			player.setGameMode(GameMode.SPECTATOR);
		}
		
		for(ScoreboardSign scoreboardSign : Main.getInstance().getScoreboards().values()) {
			scoreboardSign.setLine(2, "�fJoueurs �e"+game.getCache().getPlayers().size());
		}
	}
}
