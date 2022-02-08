package fr.jielos.fightclub.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import fr.jielos.fightclub.Main;
import fr.jielos.fightclub.game.Game;
import fr.jielos.fightclub.references.State;

public class CommandForcestart implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		final Game game = Main.getInstance().getGame();
		if(game.getState() == State.LAUNCHING) {
			if(game.getLaunching().getSeconds() > 5) {
				game.getLaunching().setSeconds(5);
				sender.sendMessage("�7Vous avez �acorrectement �7forcer le d�marrage de la partie.");
				return true;
			}
			
			sender.sendMessage("�c�oSoyez patient, la partie va d�marrer dans moins de 5 secondes!");
			return false;
		}
		
		sender.sendMessage("�c�oMalheureusement, vous ne pouvez pas forcer le d�marrage de la partie puisque celle-ci est d�j� d�marrer ou en attente de joueurs.");
		return false;
	}

}
