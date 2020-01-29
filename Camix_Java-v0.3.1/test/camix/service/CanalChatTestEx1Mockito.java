package camix.service;

import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class CanalChatTestEx1Mockito {

    private ClientChat clientMock;

    private CanalChat canalChat;

    @Before
    public void setUp() throws Exception {
        this.clientMock = Mockito.mock(ClientChat.class);
        this.canalChat = new CanalChat("Canal testé");
    }

    @Test
    public void ajouteClient() {

        final String id = "id client";

        final int repetitions = 3;

        Mockito.when(
                this.clientMock.donneId()
        ).thenReturn(
                id
        );

        this.canalChat.ajouteClient(this.clientMock);

        Assert.assertEquals("Nombre de clients incompatible", 1, (int) canalChat.donneNombreClients());
        Assert.assertTrue("Le client n'est pas dans le canal", canalChat.estPresent(this.clientMock));

        Mockito.verify(this.clientMock, Mockito.times(3)).donneId();
        Mockito.verifyNoMoreInteractions(this.clientMock);
    }
}
