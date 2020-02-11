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


public class FelixTestConnexionMultiPossible {

    final int NB_INSTANCES = 2;

    private JFrameOperator[] fenetreConnexion = new JFrameOperator[NB_INSTANCES];

    private JFrameOperator[] fenetreChat = new JFrameOperator[NB_INSTANCES];

    private JTextFieldOperator[] textChatMessage = new JTextFieldOperator[NB_INSTANCES];

    @SuppressWarnings("unused")
    private JButtonOperator[] boutonConnexion = new JButtonOperator[NB_INSTANCES];

    private JTextFieldOperator[] texteIp = new JTextFieldOperator[NB_INSTANCES];

    private JTextFieldOperator[] textePort = new JTextFieldOperator[NB_INSTANCES];

    private JTextFieldOperator[] texteInformation = new JTextFieldOperator[NB_INSTANCES];

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

        this.accesInterfaces();
    }

    private void accesInterfaces() {
        for (int i = 0; i < this.NB_INSTANCES; i++) {
            this.accesVueConnexion(i);
        }
    }

    private void accesVueConnexion(int index) {

        try {
            this.fenetreConnexion[index] = new JFrameOperator(Felix.IHM.getString("FENETRE_CONNEXION_TITRE"));
        } catch (TimeoutExpiredException e) {
            Assert.fail("La fenêtre de la vue connexion  " + index + " est pas accessible : " + e.getMessage());
        }

        try {
            this.texteIp[index] = new JTextFieldOperator(this.fenetreConnexion[index],
                    new NameComponentChooser(Felix.IHM.getString("FENETRE_CONNEXION_NOM_IP")));

            this.textePort[index] = new JTextFieldOperator(this.fenetreConnexion[index],
                    new NameComponentChooser(Felix.IHM.getString("FENETRE_CONNEXION_NOM_PORT")));

            this.texteInformation[index] = new JTextFieldOperator(this.fenetreConnexion[index],
                    new NameComponentChooser(Felix.IHM.getString("FENETRE_CONNEXION_NOM_INFORMATION")));

            this.boutonConnexion[index] = new JButtonOperator(this.fenetreConnexion[index],
                    new NameComponentChooser(Felix.IHM.getString("FENETRE_CONNEXION_NOM_CONNEXION")));
        } catch (TimeoutExpiredException e) {
            Assert.fail("Problème d'accès à un composant de la vue connexion : " + e.getMessage());
        }
    }

    @After
    public void tearDown() throws Exception {
        final Long timeout = Long.valueOf(5000);
        Thread.sleep(timeout);

        if (this.fenetreConnexion[0] != null) {
            this.fenetreConnexion[0].dispose();
        }
    }

    @Test
    public void testConnexionChat() throws InterruptedException {
        final Long timeout = Long.valueOf(1500);

        Thread.sleep(timeout);

        this.boutonConnexion[0].clickMouse();

        this.boutonConnexion[1].clickMouse();

        final String texteConnexionAttendu = String.format(
                Felix.IHM.getString("FENETRE_CONNEXION_MESSAGE_CONNEXION"), Felix.CONFIGURATION.getString("ADRESSE_CHAT"), "12345");
        ;

        try {
            this.texteInformation[0].waitText(texteConnexionAttendu);
            this.texteInformation[1].waitText(texteConnexionAttendu);
            Assert.assertEquals(texteConnexionAttendu, this.texteInformation[0].getDisplayedText());
        } catch (TimeoutExpiredException e) {
        }

        try {
            this.fenetreConnexion[0].waitClosed();
            Assert.assertFalse(this.fenetreConnexion[0].isVisible());
            this.fenetreConnexion[1].waitClosed();
            Assert.assertFalse(this.fenetreConnexion[1].isVisible());
        } catch (Exception e) {
            e.printStackTrace();
        }
        Thread.sleep(timeout);
        try {
            this.fenetreChat[0] = new JFrameOperator(Felix.IHM.getString("FENETRE_CHAT_TITRE"));
            Assert.assertTrue(this.fenetreChat[0].isVisible());
            this.fenetreChat[1] = new JFrameOperator(Felix.IHM.getString("FENETRE_CHAT_TITRE"));
            Assert.assertTrue(this.fenetreChat[1].isVisible());
        } catch (TimeoutExpiredException e) {
            Assert.fail("La fenêtre de la vue chat n'est pas accessible : " + e.getMessage());
        }

        try {
            this.textChatMessage[0] = new JTextFieldOperator(this.fenetreChat[0],
                    new NameComponentChooser(Felix.IHM.getString("FENETRE_CHAT_NOM_SAISIE")));
            this.textChatMessage[1] = new JTextFieldOperator(this.fenetreChat[1],
                    new NameComponentChooser(Felix.IHM.getString("FENETRE_CHAT_NOM_SAISIE")));
        } catch (TimeoutExpiredException e) {
            Assert.fail("Problème d'accès à un composant de la vue connexion : " + e.getMessage());
        }
    }
}