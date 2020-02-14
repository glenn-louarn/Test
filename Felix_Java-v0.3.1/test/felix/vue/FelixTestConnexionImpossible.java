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
import org.netbeans.jemmy.util.NameComponentChooser;


public class FelixTestConnexionImpossible {

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

            final Long timeoutObs = Long.valueOf(3000);
            Thread.sleep(timeoutObs);
        }
        catch (ClassNotFoundException | InvocationTargetException | NoSuchMethodException e) {
            Assert.fail("Problème de lancement de Felix : " + e.getMessage());
            throw e;
        }

        this.accesInterface();
    }

    private void accesInterface() {
        this.accesVueConnexion();
    }

    private void accesVueConnexion() {

        try {
            this.fenetreConnexion = new JFrameOperator(Felix.IHM.getString("FENETRE_CONNEXION_TITRE"));
        }
        catch (TimeoutExpiredException e) {
            Assert.fail("La fenêtre de la vue connexion n'est pas accessible : " + e.getMessage());
        }

        try {
            this.texteIp = new JTextFieldOperator(this.fenetreConnexion,
                    new NameComponentChooser(Felix.IHM.getString("FENETRE_CONNEXION_NOM_IP")));

            this.textePort = new JTextFieldOperator(this.fenetreConnexion,
                    new NameComponentChooser(Felix.IHM.getString("FENETRE_CONNEXION_NOM_PORT")));

            this.texteInformation = new JTextFieldOperator(this.fenetreConnexion,
                    new NameComponentChooser(Felix.IHM.getString("FENETRE_CONNEXION_NOM_INFORMATION")));

            this.boutonConnexion = new JButtonOperator(this.fenetreConnexion,
                    new NameComponentChooser(Felix.IHM.getString("FENETRE_CONNEXION_NOM_CONNEXION")));
        }
        catch (TimeoutExpiredException e) {
            Assert.fail("Problème d'accès à un composant de la vue connexion : " + e.getMessage());
        }
    }

    @After
    public void tearDown() throws Exception {
        final Long timeout = Long.valueOf(3000);
        Thread.sleep(timeout);

        if (this.fenetreConnexion != null) {
            this.fenetreConnexion.dispose();
        }
    }

    @Test
    public void testConnexionImpossible() throws InterruptedException {
        final String texteDefaultAttendu = Felix.IHM.getString("FENETRE_CONNEXION_MESSAGE_DEFAUT");

        try {
            this.texteInformation.waitText(texteDefaultAttendu);
        } catch (TimeoutExpiredException e) {
            Assert.fail("Information affichée invalide.");
        }

        this.boutonConnexion.clickMouse();

        final String texteConnexionAttendu = String.format(
                Felix.IHM.getString("FENETRE_CONNEXION_MESSAGE_CONNEXION"),
                Felix.CONFIGURATION.getString("ADRESSE_CHAT"),
                Felix.CONFIGURATION.getString("PORT_CHAT"));

        try {
            this.texteInformation.waitText(texteConnexionAttendu);
        } catch (TimeoutExpiredException e) {
            Assert.fail("Information affichée invalide.");
        }
    }

    @Test
    public void testConnexionImpossibleIp() throws InterruptedException {
        final Long timeout = Long.valueOf(1000);

        Thread.sleep(timeout);

        final String texteDefaultAttendu = Felix.IHM.getString("FENETRE_CONNEXION_MESSAGE_DEFAUT");

        try {
            this.texteInformation.waitText(texteDefaultAttendu);
        } catch (TimeoutExpiredException e) {
            Assert.fail("Information affichée invalide.");
        }

        final String newIp = "129.0.0.1";

        this.texteIp.clickMouse();
        this.texteIp.clearText();
        this.texteIp.typeText(newIp);

        Thread.sleep(timeout);

        this.boutonConnexion.clickMouse();

        Thread.sleep(Long.valueOf(3000));

        final String texteErreurAttendu = String.format(
                Felix.IHM.getString("FENETRE_CONNEXION_MESSAGE_CONNEXION"),
                newIp,
                Felix.CONFIGURATION.getString("PORT_CHAT"));

        try {
            this.texteInformation.waitText(texteErreurAttendu);
        } catch (TimeoutExpiredException e) {
            Assert.fail("Information affichée invalide.");
        }

    }

    @Test
    public void testConnexionImpossiblePort() throws InterruptedException {
        final Long timeout = Long.valueOf(1000);

        Thread.sleep(timeout);

        final String texteDefaultAttendu = Felix.IHM.getString("FENETRE_CONNEXION_MESSAGE_DEFAUT");

        try {
            this.texteInformation.waitText(texteDefaultAttendu);
        } catch (TimeoutExpiredException e) {
            Assert.fail("Information affichée invalide.");
        }

        final String newPort = "54321";

        this.textePort.clickMouse();
        this.textePort.clearText();
        this.textePort.typeText(newPort);

        Thread.sleep(timeout);

        this.boutonConnexion.clickMouse();

        Thread.sleep(Long.valueOf(3000));

        final String texteErreurAttendu = String.format(
                Felix.IHM.getString("FENETRE_CONNEXION_MESSAGE_CONNEXION_IMPOSSIBLE"),
                Felix.CONFIGURATION.getString("ADRESSE_CHAT"),
                newPort);

        try {
            this.texteInformation.waitText(texteErreurAttendu);
        } catch (TimeoutExpiredException e) {
            Assert.fail("Information affichée invalide.");
        }

    }

    @Test
    public void testConnexionImpossibleIpAndPort() throws InterruptedException {
        final Long timeout = Long.valueOf(1000);

        Thread.sleep(timeout);

        final String texteDefaultAttendu = Felix.IHM.getString("FENETRE_CONNEXION_MESSAGE_DEFAUT");

        try {
            this.texteInformation.waitText(texteDefaultAttendu);
        } catch (TimeoutExpiredException e) {
            Assert.fail("Information affichée invalide.");
        }

        final String newIp = "129.0.0.1";
        final String newPort = "54321";

        this.texteIp.clickMouse();
        this.texteIp.clearText();
        this.texteIp.typeText(newIp);

        Thread.sleep(timeout);

        this.textePort.clickMouse();
        this.textePort.clearText();
        this.textePort.typeText(newPort);

        Thread.sleep(timeout);

        this.boutonConnexion.clickMouse();

        Thread.sleep(Long.valueOf(20000));

        final String texteErreurAttendu = String.format(
                Felix.IHM.getString("FENETRE_CONNEXION_MESSAGE_CONNEXION_IMPOSSIBLE"),
                newIp,
                newPort);

        try {
            this.texteInformation.waitText(texteErreurAttendu);
        } catch (TimeoutExpiredException e) {
            Assert.fail("Information affichée invalide.");
        }

    }
}
