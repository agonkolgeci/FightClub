package fr.jielos.fightclub.utils;

public class Time {

	public static String format(final int remainder) {
		int minutes = remainder / 60;
		int seconds = remainder - (minutes * 60);
		
		return ((minutes > 0 ? minutes+"m" : "")+seconds+"s");
	}
	
}
