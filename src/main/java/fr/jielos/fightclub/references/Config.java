package fr.jielos.fightclub.references;

public enum Config {

	MIN_PLAYERS(2),
	MAX_PLAYERS(30);
	
	final int value;
	Config(final int value) {
		this.value = value;
	}
	
	public int getValue() {
		return (value > 1 ? value : value);
	}
}
