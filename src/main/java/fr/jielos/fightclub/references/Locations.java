package fr.jielos.fightclub.references;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public enum Locations {

	WATING_AREA(new Location(Bukkit.getWorlds().get(0), 0.5, 51, 0.5, 90, 0));
	
	final Location location;
	Locations(final Location location) {
		this.location = location;
	}
	
	public Location getContent() {
		return location;
	}
}
