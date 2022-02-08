package fr.jielos.fightclub.listeners;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import fr.jielos.fightclub.Main;
import fr.jielos.fightclub.game.Game;
import fr.jielos.fightclub.references.Locations;
import fr.jielos.fightclub.references.State;

public class EntityDamage implements Listener {

	@EventHandler
	public void onEntityDamage(final EntityDamageEvent event) {
		final Entity entity = event.getEntity();
		if(!(entity instanceof Player)) return;
		
		final Player player = (Player) entity;
		final Game game = Main.getInstance().getGame();
		
		if(game.getState() != State.PLAYING) {
			event.setCancelled(true);
			if(event.getCause() == DamageCause.VOID) {
				player.teleport(Locations.WATING_AREA.getContent());
			}
		}
	}
	
}
