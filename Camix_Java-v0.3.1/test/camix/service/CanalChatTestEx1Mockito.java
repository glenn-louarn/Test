package camix.service;

import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class CanalChatTestEx1Mockito {
    private ClientChat clientChatMock;

    @Before
    public void setUp() throws Exception {
        this.clientChatMock = EasyMock.createMock(ClientChat.class);
    }
    @Test
    public void testAjouteClientNonPresent() {
        final CanalChat canalChat = new CanalChat("test");

        final String id = "id client ";

        final int repetition = 3;

        EasyMock.expect(this.clientChatMock.donneId()).andReturn(id).times(repetition);

        EasyMock.replay(this.clientChatMock);

        canalChat.ajouteClient(clientChatMock);

        Assert.assertEquals(
                "produit non conforme",
                1,
                (int) canalChat.donneNombreClients()
        );
        Assert.assertTrue(
                "produit non conforme",
                canalChat.estPresent(clientChatMock)
        );

        EasyMock.verify((this.clientChatMock));

    }

    @Test
    public void testAjouterClient_Present(){
        CanalChat canalChat = new CanalChat("un canal");

        EasyMock.expect(
                this.clientChatMock.donneId()
        ).andReturn(
                "ID1"
        ).times(5);
        EasyMock.replay(this.clientChatMock);
        canalChat.ajouteClient(this.clientChatMock);
        Assert.assertEquals((int)canalChat.donneNombreClients(), 1);
        Assert.assertEquals((boolean)canalChat.estPresent(this.clientChatMock), true);
        canalChat.ajouteClient(this.clientChatMock);
        Assert.assertEquals((int)canalChat.donneNombreClients(), 1);
        Assert.assertEquals((boolean)canalChat.estPresent(this.clientChatMock), true);
        EasyMock.verify(this.clientChatMock);
    }
}
