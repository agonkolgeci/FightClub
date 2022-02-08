package fr.jielos.fightclub.schedulers;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import fr.jielos.fightclub.Main;
import fr.jielos.fightclub.components.ScoreboardSign;
import fr.jielos.fightclub.game.Game;
import fr.jielos.fightclub.references.Config;
import fr.jielos.fightclub.references.State;
import fr.jielos.fightclub.utils.Time;

public class Launching extends BukkitRunnable {

	final Game game;
	public Launching(final Game game) {
		this.game = game;
	}
	
	// Default value: 120
	int seconds = 120;
	public int getSeconds() { return seconds; }
	public void setSeconds(int seconds) { this.seconds = seconds; }
	
	@Override
	public void run() {
		if(seconds <= 0 || game.getCache().getPlayers().size() < Config.MIN_PLAYERS.getValue()) {
			this.cancel();
			
			if(seconds <= 0) {
				new Teleporting(game);
			} else {
				this.game.setState(State.WAITING);
				for(Player player : game.getCache().getPlayers()) {
					player.sendMessage("�cLancement de la partie annul�e, il n'y a pas assez de joueurs pour commencer !");
				}
				
				for(ScoreboardSign scoreboardSign : Main.getInstance().getScoreboards().values()) {
					scoreboardSign.setLine(3, "�7�oEn attente...");
				}
			}
			return;
		}
		
		if(game.getCache().getPlayers().size() == Config.MIN_PLAYERS.getValue() + 4) if(seconds > 60) seconds = 60;
		if(game.getCache().getPlayers().size() == Config.MIN_PLAYERS.getValue() + 8) if(seconds > 30) seconds = 30;
		if(game.getCache().getPlayers().size() == Config.MIN_PLAYERS.getValue() + 12) if(seconds > 10) seconds = 10;
		
		for(Player player : game.getCache().getPlayers()) {
			if(seconds == 120 || seconds == 60 || seconds == 30 || seconds == 20 || seconds == 10 || seconds <= 5) {
				player.sendMessage("�6La partie commence dans �e"+seconds+" �6seconde(s).");
				
			}
			
			if(Main.getInstance().getScoreboards().containsKey(player)) {
				final ScoreboardSign scoreboardSign = Main.getInstance().getScoreboards().get(player);
				scoreboardSign.setLine(3, "�fLancement �a"+Time.format(seconds));
			}
		}
		
		seconds--;
	}
}
