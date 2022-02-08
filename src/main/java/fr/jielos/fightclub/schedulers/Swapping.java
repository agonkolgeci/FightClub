package fr.jielos.fightclub.schedulers;

import java.util.Random;

import org.bukkit.scheduler.BukkitRunnable;

import fr.jielos.fightclub.Main;
import fr.jielos.fightclub.components.ScoreboardSign;
import fr.jielos.fightclub.game.Game;
import fr.jielos.fightclub.references.State;
import fr.jielos.fightclub.utils.Time;

public class Swapping extends BukkitRunnable {

	final Game game;
	public Swapping(final Game game) {
		this.game = game;
	}
	
	int seconds = (new Random().nextInt(150-90)+90);
	
	@Override
	public void run() {
		if(seconds == 0) { 
			if(game.getState() != State.FINISH) {
				new Teleporting(game);
			}
		} else {
			for(ScoreboardSign scoreboardSign : Main.getInstance().getScoreboards().values()) {
				scoreboardSign.setLine(3, "�fT�l�portation �a"+Time.format(seconds));
			}
		}
		
		seconds--;
	}
}
