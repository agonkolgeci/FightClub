package fr.jielos.fightclub.references;

public enum Messages {

	UNKNOWN_COMMAND("§c▏ Commande inconnue ! Vérifiez la commande et essayer à nouveau."),
	KICK_WHITELIST("§4Vous n'êtes pas autorisé à rejoindre le serveur, celui-ci est actuellement en phase de développement.");

	final String content;
	Messages(final String content) {
		this.content = content;
	}
	
	public String getContent() {
		return content;
	}
}
