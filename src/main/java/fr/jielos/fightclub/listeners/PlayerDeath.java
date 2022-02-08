package fr.jielos.fightclub.listeners;

import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.GameMode;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import fr.jielos.fightclub.Main;
import fr.jielos.fightclub.components.ScoreboardSign;
import fr.jielos.fightclub.game.Game;
import fr.jielos.fightclub.references.Items;
import fr.jielos.fightclub.references.State;

public class PlayerDeath implements Listener {

	@EventHandler
	public void onPlayerDeath(final PlayerDeathEvent event) {
		final Player player = event.getEntity();
		final Game game = Main.getInstance().getGame();
		event.setDeathMessage(null);
		
		if(game.getState() != State.PLAYING) return;
		final List<Player> duel = game.getDuels().stream().filter(d -> d.contains(player)).collect(Collectors.toList()).get(0);
		final Player opponent = duel.stream().filter(p -> p != player).collect(Collectors.toList()).get(0);
		
		game.getCache().getPlayers().remove(player);
		game.getCache().getSpectators().add(player);
		player.setGameMode(GameMode.SPECTATOR);
		
		if(opponent != null) {
			opponent.getInventory().addItem(Items.EQUIPEMENT.getContent());
			opponent.updateInventory();
		}
		
		if(player.getLastDamageCause() instanceof EntityDamageByEntityEvent) {
			final EntityDamageByEntityEvent entityDamage = (EntityDamageByEntityEvent) player.getLastDamageCause();
			
			Player killer = null;
			if(entityDamage.getDamager() instanceof Player) {
				killer = (Player) entityDamage.getDamager();
			} else if(entityDamage.getDamager() instanceof Arrow) {
				final Arrow arrow = (Arrow) entityDamage.getDamager();
				if(arrow.getShooter() instanceof Player) {
					killer = (Player) arrow.getShooter();
				}
			}
			
			if(killer != null) {
				Main.getInstance().getServer().broadcastMessage("�f"+player.getDisplayName()+" �7a �t� tu� par �b"+killer.getDisplayName()+"�7.");
			}
		} else Main.getInstance().getServer().broadcastMessage("�f"+player.getDisplayName()+" �7est mort.");
		
		for(ScoreboardSign scoreboardSign : Main.getInstance().getScoreboards().values()) {
			scoreboardSign.setLine(2, "�fJoueurs �e"+game.getCache().getPlayers().size());
		}
		if(game.getCache().getPlayers().size() <= 1) game.end();
		
		Main.getInstance().getServer().getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
			@Override
			public void run() {
				player.spigot().respawn();
			}
		}, 1);
	}
}
