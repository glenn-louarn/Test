package felix.vue;

import java.awt.*;
import java.io.IOException;
import javax.swing.*;

import felix.Felix;
import felix.controleur.ControleurFelix;
import felix.controleur.VueFelix;

/**
 * Classe de la vue chat de Felix.
 *
 * Cette vue permet d'échanger des messages avec d'autres utilisateurs du chat.
 *
 * Cette vue est une vue active : elle possède une méthode d'activation qui lance
 * un thread de réception des messages du chat.
 *
 * @version 0.3.1
 * @author Matthias Brun
 *
 */
public class VueConnexion extends VueFelix
{
    /**
     * La fenêtre de la vue.
     */
    private Fenetre	fenetre;

    /**
     * Le conteneur de la vue.
     */
    private Container contenu;

    private JLabel topLabel;

    private JLabel ipLabel;

    private JTextField ipField;

    private JLabel portLabel;

    private JTextField portField;

    private JButton connectButton;

    /**
     * Constructeur de la vue chat.
     *
     * @param controleur le contrôleur du chat auquel appartient la vue.
     */
    public VueConnexion(ControleurFelix controleur)
    {
        super(controleur);

        final Integer largeur = Integer.parseInt(Felix.IHM.getString("FENETRE_CHAT_LARGEUR"));
        final Integer hauteur = Integer.parseInt(Felix.IHM.getString("FENETRE_CHAT_HAUTEUR"));

        this.fenetre = new Fenetre(largeur, hauteur, Felix.IHM.getString("FENETRE_CHAT_TITRE"));

        this.construireFenetre(largeur, hauteur);
    }

    /**
     * Construire les panneaux et les widgets de contrôle de la vue.
     *
     * @param largeur la largeur de la fenêtre.
     * @param hauteur la hauteur de la fenêtre.
     */
    private void construireFenetre(Integer largeur, Integer hauteur)
    {
        this.contenu = this.fenetre.getContentPane();
        this.contenu.setLayout(new FlowLayout(1, 5, 30));

        this.topLabel = new JLabel("Saisir l'adresse et le port du serveur chat.");

        this.ipLabel = new JLabel("IP");
        this.ipField = new JTextField(Felix.CONFIGURATION.getString("ADRESSE_CHAT"));

        this.ipLabel.setPreferredSize(new Dimension(largeur/2 - 10, ipLabel.getPreferredSize().height));
        this.ipField.setPreferredSize(new Dimension(largeur/2 - 10, ipField.getPreferredSize().height));

        this.portLabel = new JLabel("PORT");
        this.portField = new JTextField(Felix.CONFIGURATION.getString("PORT_CHAT"));

        this.portLabel.setPreferredSize(new Dimension(largeur/2 - 10, portLabel.getPreferredSize().height));
        this.portField.setPreferredSize(new Dimension(largeur/2 - 10, portField.getPreferredSize().height));

        this.connectButton = new JButton("Connexion");

        this.connectButton.addActionListener(actionEvent -> {
            this.topLabel.setText("Connexion au chat @"+this.ipField.getText()+":"+this.portField.getText());
            this.donneControleur().connecteCamix(this.ipField.getText(), this.portField.getText());
        });

        this.contenu.add(topLabel);
        this.contenu.add(this.ipLabel);
        this.contenu.add(this.ipField);
        this.contenu.add(this.portLabel);
        this.contenu.add(this.portField);
        this.contenu.add(this.connectButton);
    }

    public void setErrorMessage() {
        this.topLabel.setText("Connexion au chat @"+this.ipField.getText()+":"+this.portField.getText() + " impossible");
    }

    /**
     * {@inheritDoc}
     *
     * @see felix.controleur.VueFelix
     */
    @Override
    public void affiche()
    {
        this.fenetre.setVisible(true);
    }

    /**
     * {@inheritDoc}
     *
     * @see felix.controleur.VueFelix
     */
    @Override
    public void ferme()
    {
        this.fenetre.dispose();
    }


}
