package fr.jielos.fightclub.listeners;

import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import fr.jielos.fightclub.Main;
import fr.jielos.fightclub.components.ActionBar;
import fr.jielos.fightclub.components.ScoreboardSign;
import fr.jielos.fightclub.game.Game;
import fr.jielos.fightclub.references.Config;
import fr.jielos.fightclub.references.Items;
import fr.jielos.fightclub.references.State;

public class PlayerQuit implements Listener {

	@EventHandler
	public void onPlayerQuit(final PlayerQuitEvent event) {
		final Player player = event.getPlayer();
		final Game game = Main.getInstance().getGame();
		
		event.setQuitMessage(null);
		if(Main.getInstance().getScoreboards().containsKey(player)) {
			Main.getInstance().getScoreboards().get(player).destroy();
		}
		
		/* Check if player is in spectators list or not */
		if(!game.getCache().getSpectators().contains(player) && game.getCache().getPlayers().contains(player)) {
			if(game.getState() == State.PLAYING) {
				Main.getInstance().getServer().broadcastMessage("�f"+player.getDisplayName()+" �7est mort en se d�connectant.");
				
				final List<List<Player>> duels = game.getDuels().stream().filter(d -> d.contains(player)).collect(Collectors.toList());
				final List<Player> duel = (!duels.isEmpty() ? duels.get(0) : null);
				
				if(duel != null) {
					final Player opponent = duel.stream().filter(p -> p != player).collect(Collectors.toList()).get(0);
					if(opponent != null) opponent.getInventory().addItem(Items.EQUIPEMENT.getContent());
				}
			} else if(game.getState() != State.FINISH) {
				new ActionBar(("�f"+player.getDisplayName()+" �7a quitt� la partie �8(�a"+(game.getCache().getPlayers().size()-1)+"�8/�c"+Config.MAX_PLAYERS.getValue()+"�8)")).send(game.getCache().getPlayers(), 2, 5, 2);
			}
			
			game.getCache().getPlayers().remove(player);
			game.getCache().getSpectators().add(player);
			if(game.getState() == State.PLAYING) if(game.getCache().getPlayers().size() <= 1) game.end();
		}
		
		for(ScoreboardSign scoreboardSign : Main.getInstance().getScoreboards().values()) {
			scoreboardSign.setLine(2, "�fJoueurs �e"+game.getCache().getPlayers().size());
		}
	}
}
