package camix.service;

import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.lang.reflect.Field;
import java.util.Hashtable;

import static org.mockito.Mockito.*;

public class CanalChatTestEx1Mockito {
    private CanalChat canalChat;
    private ClientChat clientChatMock;

    @Before
    public void setUp() throws Exception {
        clientChatMock = Mockito.mock(ClientChat.class);
        CanalChat canalChatTempo = new CanalChat("name");
        canalChat = Mockito.spy(canalChatTempo);
    }

    @Test
    public void testAjoutClientNonPresent() throws Exception {
        final String message = "Ajout client :";
        final String id = "1";
        Mockito.doReturn(true).when(canalChat).estPresent(clientChatMock);
        when(clientChatMock.donneId()).thenReturn(id);

        /* Pour l'accès au field caché (privé) */
        String attributConcerne = "clients";
        Field attribut;

        attribut = CanalChat.class.getDeclaredField(attributConcerne);
        attribut.setAccessible(true);
        Hashtable<String, ClientChat> table = (Hashtable<String, ClientChat>) attribut.get(this.canalChat);
        table.put(clientChatMock.donneId(), clientChatMock);

        Assert.assertEquals(1, table.size());
        Assert.assertTrue(table.contains(this.clientChatMock));
        verify(this.clientChatMock);
    }

}
