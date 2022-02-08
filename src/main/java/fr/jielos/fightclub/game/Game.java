package fr.jielos.fightclub.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.common.collect.Lists;

import fr.jielos.fightclub.Main;
import fr.jielos.fightclub.components.Cuboid;
import fr.jielos.fightclub.components.ScoreboardSign;
import fr.jielos.fightclub.components.Title;
import fr.jielos.fightclub.references.Config;
import fr.jielos.fightclub.references.Items;
import fr.jielos.fightclub.references.State;
import fr.jielos.fightclub.schedulers.Finishing;
import fr.jielos.fightclub.schedulers.Launching;
import fr.jielos.fightclub.schedulers.Swapping;

public class Game {

	final JavaPlugin instance;
	final Cache cache;
	public Game(final JavaPlugin instance) {
		this.instance = instance;
		this.cache = new Cache(this);
		this.state = State.WAITING;
	}
	
	List<List<Player>> duels = new ArrayList<>();
	
	Launching launching;
	Swapping swapping;
	
	State state;
	
	public void launch() {
		if(state != State.WAITING) return;
		if(cache.getPlayers().size() < Config.MIN_PLAYERS.getValue()) return;
		
		this.state = State.LAUNCHING;
		this.launching = new Launching(this);
		this.launching.runTaskTimer(instance, 0, 20);
	}
	
	public void start() {
		if(state == State.PLAYING) return;
		if(cache.getPlayers().size() < Config.MIN_PLAYERS.getValue()) return;
		
		this.state = State.PLAYING;
		for(Player player : cache.getPlayers()) {
			player.getInventory().setArmorContents(new ItemStack[]{new ItemStack(Material.IRON_BOOTS), new ItemStack(Material.IRON_LEGGINGS), new ItemStack(Material.IRON_CHESTPLATE), new ItemStack(Material.IRON_HELMET)});
			player.getInventory().setItem(0, new ItemStack(Material.IRON_SWORD));
			player.getInventory().setItem(1, new ItemStack(Material.COOKED_BEEF, 32));
			player.sendMessage("�6Vous serez r�guli�rement swap� toutes les �91m30s �6� �92m30s�6.");;
		}
		this.teleport();
	}
	
	public void teleport() {
		this.state = State.PLAYING;
		this.duels = Lists.partition(cache.getPlayers(), 2);
		
		final List<Integer> integers = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15));
		for(List<Player> players : duels) {
			int randomValue = new Random().nextInt(integers.size());
			int randomInteger = integers.get(randomValue);
			integers.remove(randomValue);
			
			final Location landmark1 = new Location(Bukkit.getWorlds().get(0), randomInteger*1000, 50, randomInteger*1000);
			final Location landmark2 = new Location(Bukkit.getWorlds().get(0), randomInteger*1000, 50, randomInteger*1000).add(200, 50, 100);
			final Cuboid map = new Cuboid(landmark1, landmark2);
			
			final List<Location> locations = new ArrayList<>();
			map.blockList().forEachRemaining(block -> {
				if(block.getType() == Material.PISTON_BASE) {
					final Location location = block.getLocation().add(0.5, 2, 0.5);
					locations.add(location);
				}
			});
			
			final Map<Player, Location> data = new HashMap<>();
			for(Player player : players) data.put(player, locations.get(players.indexOf(player)));
			
			for(Entry<Player, Location> duel : data.entrySet()) {
				duel.getKey().setAllowFlight(false);
				duel.getKey().teleport(duel.getValue());
			}
		}
		
		final List<List<Player>> ps = duels.stream().filter(e -> e.size() == 1).collect(Collectors.toList());
		final Player solo = (!ps.isEmpty() ? ps.get(0).get(0) : null);
		if(solo != null) {
			solo.getInventory().addItem(Items.EQUIPEMENT.getContent());
			solo.updateInventory();
			solo.sendMessage("�c�oMalheureusement, vous n'avez pas d'adversaire ;/ Vous venez de recevoir l'�quipement requis pour combattre dans votre inventaire.");
		}
		
		for(Entity entity : Main.getInstance().getServer().getWorlds().get(0).getEntities()) if(!(entity instanceof Player)) entity.remove();
		
		this.swapping = new Swapping(this);
		this.swapping.runTaskTimer(instance, 0, 20);
	}
	
	public void end() {
		if(state != State.PLAYING) return;
		this.state = State.FINISH;
		this.swapping.cancel();
		
		final Player winner = cache.getPlayers().get(0);
		new Title("�6�lVICTOIRE!", "�e"+winner.getDisplayName()+" �fgagne la partie!").display(winner, 1, 5, 1);
		new Title("�c�lFIN DE LA PARTIE!", "�e"+winner.getDisplayName()+" �fgagne la partie!").display(instance.getServer().getOnlinePlayers().stream().filter(e -> e.getPlayer() != winner).collect(Collectors.toList()), 1, 5, 1);
		instance.getServer().broadcastMessage("�6�lVictoire de " + winner.getDisplayName() + " �b�k|�c�k|�d�k|�6�k|�b�l F�licitations �b�k|�c�k|�d�k|�6�k|");
	
		for(ScoreboardSign scoreboardSign : Main.getInstance().getScoreboards().values()) {
			scoreboardSign.setLine(3, "�7�oPartie termin�e !");
		}
		
		new Finishing(this).runTaskLater(instance, 12*20);
	}
	
	public Cache getCache() { return cache; }
	public void setState(State state) { this.state = state; }
	public State getState() { return state; }
	public List<List<Player>> getDuels() { return duels; }
	public Launching getLaunching() { return launching; }
}
