package fr.jielos.fightclub.schedulers;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import fr.jielos.fightclub.Main;
import fr.jielos.fightclub.game.Game;

public class Finishing extends BukkitRunnable {

	final Game game;
	public Finishing(final Game game) {
		this.game = game;
	}
	
	@Override
	public void run() {
		for(Player player : Main.getInstance().getServer().getOnlinePlayers()) {
			final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			final DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
			
			try {
				dataOutputStream.writeUTF("ConnectHub");
			} catch(IOException exception) {
				exception.printStackTrace();
			}
			
			player.sendPluginMessage(Main.getInstance(), ("SuniGames"), byteArrayOutputStream.toByteArray());
		}
		
		Main.getInstance().getServer().reload();
	}
}
