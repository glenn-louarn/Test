package felix.controleur;

import java.io.IOException;

import felix.communication.Connexion;
import felix.vue.VueChat;
import felix.vue.VueConnexion;

/**
 * Classe de contrôleur du chat (architecture MVC). 
 * 
 * Cette classe gère l'instanciation de la connexion au composant Camix,
 * ainsi que l'instanciation des vues et leurs affichages.
 * 
 * @version 0.3.1
 * @author Matthias Brun
 */
public class ControleurFelix
{
	/**
	 * Connexion du chat (connexion à un composant Camix).
	 */
	private Connexion connexion;
	
	/**
	 * Accesseur à la connexion du chat.
	 * 
	 * @return la connexion à Camix du composant Felix.
	 */
	public Connexion donneConnexion()
	{
		return this.connexion;
	}
	
	/**
	 * Vue chat (permettant d'échanger des messages avec d'autres utilisateurs du chat).
	 */
	private VueChat vueChat;

	private VueConnexion vueConnexion;
	
	/**
	 * Constructeur du contrôleur de chat. 
	 */
	public ControleurFelix()
	{
		this.vueConnexion = new VueConnexion(this);
		this.vueChat = new VueChat(this);
		this.vueConnexion.affiche();

	}
	
	/**
	 * Mise en place d'une connexion avec un serveur Camix.
	 * 
	 * @throws IOException erreur d'entrée/sortie.
	 */
	public void connecteCamix(String ip, String port)
	{
		try {
			this.connexion = new Connexion(
				ip, Integer.parseInt(port)
			);
			this.vueConnexion.ferme();
			this.vueChat.affiche();
			this.vueChat.active();
		} catch (IOException e) {
			this.vueConnexion.setErrorMessage();
			//e.printStackTrace();
		}
	}
	
}
