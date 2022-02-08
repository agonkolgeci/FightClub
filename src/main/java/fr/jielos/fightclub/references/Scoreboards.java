package fr.jielos.fightclub.references;

import fr.jielos.fightclub.Main;

public enum Scoreboards {

	DEFAULT(("�8- �4�lFight�c�lClub �8-"), new String[]{"", "�fJoueurs �e?", "�7�oEn attente...", " ", "�7Id �f"+Main.getInstance().getServer().getMotd(), "�emc.sunigames.ga"});
	
	final String name;
	final String[] lines;
	Scoreboards(final String name, final String... lines) {
		this.name = name;
		this.lines = lines;
	}
	
	public String getName() {
		return name;
	}
	
	public String[] getLines() {
		return lines;
	}
}
