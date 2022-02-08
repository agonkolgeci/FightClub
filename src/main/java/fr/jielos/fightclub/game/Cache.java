package fr.jielos.fightclub.game;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

public class Cache {

	final Game game;
	public Cache(final Game game) {
		this.game = game;
	}
	
	List<Player> players = new ArrayList<>();
	List<Player> spectators = new ArrayList<>();

	public List<Player> getPlayers() {
		return players;
	}
	
	public List<Player> getSpectators() {
		return spectators;
	}
}