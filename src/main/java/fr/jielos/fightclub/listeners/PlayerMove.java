package fr.jielos.fightclub.listeners;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import fr.jielos.fightclub.Main;
import fr.jielos.fightclub.game.Game;
import fr.jielos.fightclub.references.Locations;
import fr.jielos.fightclub.references.State;

public class PlayerMove implements Listener {
	
	@EventHandler
	public void onPlayerMove(final PlayerMoveEvent event) {
		final Player player = event.getPlayer();
		final Game game = Main.getInstance().getGame();
		
		if(game.getState() != State.PLAYING) {
			if(player.getLocation().getY() <= (Locations.WATING_AREA.getContent().getY()-10)) {
				player.teleport(Locations.WATING_AREA.getContent());
			}
		} if(game.getState() == State.TELEPORTING) {
			final Location from = event.getFrom();
			final Location to = event.getTo();
			if(from.getX() != to.getX() || from.getY() != to.getY() || from.getZ() != to.getZ()) {
				player.teleport(from);
			}
		}
	}

}
