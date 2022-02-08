package fr.jielos.fightclub.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

import fr.jielos.fightclub.Main;
import fr.jielos.fightclub.game.Game;
import fr.jielos.fightclub.references.State;

public class FoodLevelChange implements Listener {
	
	@EventHandler
	public void onFoodLevelChange(final FoodLevelChangeEvent event) {
		final Game game = Main.getInstance().getGame();
		if(game.getState() != State.PLAYING && game.getState() != State.FINISH) {
			event.setCancelled(true);
		}
	}

}
