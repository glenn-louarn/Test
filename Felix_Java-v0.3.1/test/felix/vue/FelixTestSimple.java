package felix.vue;

import java.lang.reflect.InvocationTargetException;

import felix.Felix;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.netbeans.jemmy.ClassReference;
import org.netbeans.jemmy.JemmyProperties;
import org.netbeans.jemmy.TimeoutExpiredException;
import org.netbeans.jemmy.operators.JButtonOperator;
import org.netbeans.jemmy.operators.JFrameOperator;
import org.netbeans.jemmy.operators.JTextFieldOperator;
import org.netbeans.jemmy.operators.JTextPaneOperator;
import org.netbeans.jemmy.util.NameComponentChooser;


public class FelixTestSimple {

    private JFrameOperator fenetreConnexion;

    @SuppressWarnings("unused")
    private JButtonOperator boutonConnexion;

    private JTextFieldOperator texteIp;

    private JTextFieldOperator textePort;

    private JTextPaneOperator texteHeader;

    @Before
    public void setUp() throws Exception {
        final Integer timeout = 3000;
        JemmyProperties.setCurrentTimeout("FrameWaiter.WaitFrameTimeout", timeout);
        JemmyProperties.setCurrentTimeout("ComponentOperator.WaitComponentTimeout", timeout);
        JemmyProperties.setCurrentTimeout("ComponentOperator.WaitStateTimeout", timeout);

        try {
            new ClassReference("felix.Felix").startApplication();

            final Long timeoutObs = Long.valueOf(3000);
            Thread.sleep(timeoutObs);
        }
        catch (ClassNotFoundException | InvocationTargetException | NoSuchMethodException e) {
            Assert.fail("Problème de lancement de Felix : " + e.getMessage());
            throw e;
        }

        // Accès à l'interface de Monix.
        this.accesInterface();
    }

    private void accesInterface() {
        // Accès à l'interface de la vue caisse.
        this.accesVueConnexion();
    }

    private void accesVueConnexion() {

        try {
            this.fenetreConnexion = new JFrameOperator(Felix.IHM.getString("FENETRE_CONNEXION_TITRE"));
        }
        catch (TimeoutExpiredException e) {
            Assert.fail("La fenêtre de la vue connexion n'est pas accessible : " + e.getMessage());
        }

        // Accès aux composants (widgets) de la vue caisse
        try {
            // Accès au champ de saisie d'un identifiant produit (par son nom).
            this.texteIp = new JTextFieldOperator(this.fenetreConnexion,
                    new NameComponentChooser(Felix.IHM.getString("CAISSE_NOM_SAISIE_ID_PRODUIT")));

            // Accès au champ de saisie de la quantité de produit (par son nom).
            this.textePort = new JTextFieldOperator(this.fenetreConnexion,
                    new NameComponentChooser(Felix.IHM.getString("CAISSE_NOM_SAISIE_QUANTITE_PRODUIT")));

            // Accès au champ de libellé d'un produit (par son nom).
            this.texteHeader = new JTextPaneOperator(this.fenetreConnexion,
                    new NameComponentChooser(Felix.IHM.getString("CAISSE_NOM_AFFICHAGE_LIBELLE_PRODUIT")));

            // Accès au bouton d'ajout d'un produit à la vente (par son nom).
            this.boutonConnexion = new JButtonOperator(this.fenetreConnexion,
                    new NameComponentChooser(Felix.IHM.getString("CAISSE_NOM_BOUTON_AJOUTER")));
        }
        catch (TimeoutExpiredException e) {
            Assert.fail("Problème d'accès à un composant de la vue connexion : " + e.getMessage());
        }
    }

    @After
    public void tearDown() throws Exception {
        final Long timeout = Long.valueOf(5000);
        Thread.sleep(timeout);

        if (this.fenetreConnexion != null) {
            this.fenetreConnexion.dispose();
        }
    }

    @Test
    public void testConnexionChat() throws InterruptedException
    {
        /**
        // 1,5 seconde d'observation par suspension du thread
        // entre chaque action (objectif pédagogique).
        final Long timeout = Long.valueOf(1500);

        // Effacement du champ de saisie de l'ID du produit.
        this.texteId.clickMouse();
        this.texteId.clearText();

        // Observation par suspension du thread (objectif pédagogique).
        Thread.sleep(timeout);

        // Saisie de l'ID du produit.
        this.texteId.typeText("11A");

        // Forcer la perte de focus du champ de saisie de l'identifiant d'un produit
        // en donnant le focus au champ de saisie de la quantité de produit.
        this.texteQuantite.clickMouse();

        // Validation des valeurs des champs libellé et prix du produit, normalement mis
        // à jour après la perte de focus du champ de saisie de l'identifiant du produit.
        final String libelleAttendu = "produit un A";
        final String prixAttendu = String.format("%1$.2f €", 10.0);

        try {
            // Attente du message du libellé.
            this.texteLibelle.waitText(libelleAttendu);
        }
        catch (TimeoutExpiredException e) {
            Assert.fail("Libellé du produit (11A) invalide.");
        }

        try {
            // Attente du message du prix.
            this.textePrix.waitText(prixAttendu);
        }
        catch (TimeoutExpiredException e) {
            Assert.fail("Prix du produit (11A) invalide.");
        }

        // Observation par suspension du thread (objectif pédagogique).
        Thread.sleep(timeout);

        // Effacement du champ de saisie de la quantité de produit.
        this.texteQuantite.clearText();

        // Observation par suspension du thread (objectif pédagogique).
        Thread.sleep(timeout);

        // Saisie de la quantité de produit
        // Remarque : enterText est utilisé pour saisir et formatter la valeur saisie (JFormattedTextField).
        this.texteQuantite.enterText("2");

        // Observation par suspension du thread (objectif pédagogique).
        Thread.sleep(timeout);

        // Ajout du produit à la vente.
        this.boutonAjouter.doClick();

        // Validation des valeurs des champs libellé prix du produit ainsi que du ticket.
        final String infoAttendu = String.format("+ produit un A   x   2  x %1$.2f €", 10.0);
        final String totalAttendu = String.format("%1$.2f €", 20.0);
        final String ticketAttendu = Monix.MESSAGES.getString("CLIENT_TEXTE_TICKET")
                + System.getProperty("line.separator")
                + String.format("produit un A       x 2      x    %1$.2f €", 10.0);
        final String totalClientAttendu = String.format("%1$.2f €", 20.0);

        try {
            // Attente du message d'information de l'achat.
            this.texteInfo.waitText(infoAttendu);
        }
        catch (TimeoutExpiredException e) {
            Assert.fail("Information pour l'achat du produit (11A) invalide.");
        }

        try {
            // Attente du message du prix de l'achat.
            this.texteTotal.waitText(totalAttendu);
        }
        catch (TimeoutExpiredException e) {
            Assert.fail("Prix total pour l'achat du produit (11A) invalide.");
        }

        try {
            // Attente du ticket.
            this.texteTicket.waitText(ticketAttendu);
        }
        catch (TimeoutExpiredException e) {
            Assert.fail("Ticket pour l'achat du produit (11A) invalide.");
        }

        try {
            // Attente du message du prix total de la vente.
            this.texteTotalClient.waitText(totalClientAttendu);
        }
        catch (TimeoutExpiredException e) {
            Assert.fail("Prix total pour la vente du produit (11A) invalide.");
        }

        // Observation par suspension du thread (objectif pédagogique).
        Thread.sleep(timeout);

        // Fin de la vente.
        this.boutonFinVente.doClick();
         **/
    }
}
