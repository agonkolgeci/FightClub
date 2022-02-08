package fr.jielos.fightclub.schedulers;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import fr.jielos.fightclub.Main;
import fr.jielos.fightclub.components.ScoreboardSign;
import fr.jielos.fightclub.game.Game;
import fr.jielos.fightclub.references.State;

public class Teleporting extends BukkitRunnable {

	final Game game;
	public Teleporting(final Game game) {
		this.game = game;
		if(game.getState() == State.PLAYING) this.isPlaying = true;
		this.game.setState(State.TELEPORTING);
		this.runTaskTimer(Main.getInstance(), 0, 20);
	}
	
	boolean isPlaying = false;
	int seconds = 4;
	
	@Override
	public void run() {
		if(seconds == 4) {
			for(Player player : game.getCache().getPlayers()) {
				player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 5*20, 0));
				player.setAllowFlight(true);
			}
			
			for(ScoreboardSign scoreboardSign : Main.getInstance().getScoreboards().values()) {
				scoreboardSign.setLine(3, "�7�oT�l�portation...");
			}
		}
		
		if(seconds == 2) {
			for(Player player : game.getCache().getPlayers()) {
				player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 3*20+10, 0));
				player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 4*20, 10));
			}
		}
		
		if(seconds <= 0) {
			if(!isPlaying) game.start();
			else game.teleport(); 
			
			this.cancel();
		}
		
		seconds--;
	}

}
