package camix.service;

import org.easymock.*;
import org.junit.*;

public class CanalChatTestEx1EasyMock {

    @Mock
    private ClientChat clientMock;

    @TestSubject
    private CanalChat canalChat;

    @Before
    public void setup() throws Exception{
        this.clientMock = EasyMock.createMock(ClientChat.class);
        this.canalChat = new CanalChat("Canal testé");
    }

    @Test
    public void ajouteClient() {
        final String id = "id client";

        final int repetitions = 5;

        EasyMock.expect(
                this.clientMock.donneId()
        ).andReturn(
               id
        );

        EasyMock.expectLastCall().times(repetitions);

        EasyMock.replay(this.clientMock);

        this.canalChat.ajouteClient(this.clientMock);

        Assert.assertEquals("Nombre de clients incompatible", 1, (int) canalChat.donneNombreClients());
        Assert.assertTrue("Le client n'est pas dans le canal", canalChat.estPresent(this.clientMock));

        this.canalChat.ajouteClient(this.clientMock);

        Assert.assertEquals("Nombre de clients incompatible", 1, (int) canalChat.donneNombreClients());
        Assert.assertTrue("Le client n'est pas dans le canal", canalChat.estPresent(this.clientMock));

        EasyMock.verify(this.clientMock);
    }

    @Test
    public void ajouteClientSideEffect() {
        final String id = "id client";

        final int repetitions = 5;

        EasyMock.expect(
                this.clientMock.donneId()
        ).andReturn(
                id
        );

        EasyMock.expectLastCall().times(repetitions);

        EasyMock.replay(this.clientMock);

        this.canalChat.ajouteClient(this.clientMock);

        Assert.assertEquals("Nombre de clients incompatible", 1, (int) canalChat.donneNombreClients());
        Assert.assertTrue("Le client n'est pas dans le canal", canalChat.estPresent(this.clientMock));

        this.canalChat.ajouteClient(this.clientMock);

        Assert.assertEquals("Nombre de clients incompatible", 1, (int) canalChat.donneNombreClients());
        Assert.assertTrue("Le client n'est pas dans le canal", canalChat.estPresent(this.clientMock));

        EasyMock.verify(this.clientMock);
    }
 }