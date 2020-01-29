package felix.vue;

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
import org.netbeans.jemmy.util.NameComponentChooser;

import java.lang.reflect.InvocationTargetException;


public class FelixTestConnexionPossible2 {

    private JFrameOperator fenetreConnexion;

    @SuppressWarnings("unused")
    private JButtonOperator boutonConnexion;

    private JTextFieldOperator texteIp;

    private JTextFieldOperator textePort;

    private JTextFieldOperator texteInformation;

    @Before
    public void setUp() throws Exception {
        final Integer timeout = 3000;
        JemmyProperties.setCurrentTimeout("FrameWaiter.WaitFrameTimeout", timeout);
        JemmyProperties.setCurrentTimeout("ComponentOperator.WaitComponentTimeout", timeout);
        JemmyProperties.setCurrentTimeout("ComponentOperator.WaitStateTimeout", timeout);

        try {
            new ClassReference("felix.Felix").startApplication();

            final Long timeoutObs = Long.valueOf(10000);
            Thread.sleep(timeoutObs);
        } catch (ClassNotFoundException | InvocationTargetException | NoSuchMethodException e) {
            Assert.fail("Problème de lancement de Felix : " + e.getMessage());
            throw e;
        }

        this.accesInterface();
    }

    private void accesInterface() {
        // Accès à l'interface de la vue caisse.
        this.accesVueConnexion();
    }

    private void accesVueConnexion() {

        try {
            this.fenetreConnexion = new JFrameOperator(Felix.IHM.getString("FENETRE_CONNEXION_TITRE"));
        } catch (TimeoutExpiredException e) {
            Assert.fail("La fenêtre de la vue connexion n'est pas accessible : " + e.getMessage());
        }

        // Accès aux composants (widgets) de la vue caisse
        try {
            // Accès au champ de saisie d'un identifiant produit (par son nom).
            this.texteIp = new JTextFieldOperator(this.fenetreConnexion,
                    new NameComponentChooser(Felix.IHM.getString("FENETRE_CONNEXION_NOM_IP")));

            // Accès au champ de saisie de la quantité de produit (par son nom).
            this.textePort = new JTextFieldOperator(this.fenetreConnexion,
                    new NameComponentChooser(Felix.IHM.getString("FENETRE_CONNEXION_NOM_PORT")));

            // Accès au champ de libellé d'un produit (par son nom).
            this.texteInformation = new JTextFieldOperator(this.fenetreConnexion,
                    new NameComponentChooser(Felix.IHM.getString("FENETRE_CONNEXION_NOM_INFORMATION")));

            // Accès au bouton d'ajout d'un produit à la vente (par son nom).
            this.boutonConnexion = new JButtonOperator(this.fenetreConnexion,
                    new NameComponentChooser(Felix.IHM.getString("FENETRE_CONNEXION_NOM_CONNEXION")));
        } catch (TimeoutExpiredException e) {
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
    public void testConnexionChat() throws InterruptedException {
        final Long timeout = Long.valueOf(1500);

        Thread.sleep(timeout);

        this.boutonConnexion.clickMouse();

        final String texteConnexionAttendu = String.format(
                Felix.IHM.getString("FENETRE_CONNEXION_MESSAGE_CONNEXION"), Felix.CONFIGURATION.getString("ADRESSE_CHAT"), "12345");
        ;

        try {
            this.texteInformation.waitText(texteConnexionAttendu);
            Assert.assertEquals(texteConnexionAttendu, this.texteInformation.getDisplayedText());
        } catch (TimeoutExpiredException e) {
        }

        try{
            this.fenetreConnexion.waitClosed();
            Assert.assertFalse( this.fenetreConnexion.isVisible());
        } catch (Exception e) {
            e.printStackTrace();
        }
        Thread.sleep(timeout);
        try {
            this.fenetreConnexion = new JFrameOperator(Felix.IHM.getString("FENETRE_CHAT_TITRE"));
            Assert.assertTrue(this.fenetreConnexion.isVisible());
        } catch (TimeoutExpiredException e) {
            Assert.fail("La fenêtre de la vue chat n'est pas accessible : " + e.getMessage());
        }
    }
}