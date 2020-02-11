package camix.service;

import org.easymock.*;
import org.junit.*;

import java.lang.reflect.Field;
import java.util.Hashtable;

public class CanalChatTestEx1EasyMock {

    @Mock
    private ClientChat clientMock;

    @TestSubject
    private CanalChat canalChat;

    @Before
    public void setup() throws Exception{
        this.clientMock = EasyMock.createMock(ClientChat.class);
        this.canalChat = EasyMock.partialMockBuilder(CanalChat.class).addMockedMethod("estPresent").withConstructor("test").createMock();
    }
    @Test
    public void testAjoutClientNonPresent() throws Exception {
        final String id = "1";

        EasyMock.expect(this.canalChat.estPresent(this.clientMock)).andReturn(true);
        EasyMock.expect(this.clientMock.donneId()).andReturn(id).times(1);

        EasyMock.replay(canalChat);
        EasyMock.replay(clientMock);
        // Pour l'accès au field caché (privé)
        String attributConcerne = "clients";
        Field attribut;

        attribut = CanalChat.class.getDeclaredField(attributConcerne);
        attribut.setAccessible(true);
        Hashtable<String, ClientChat> table = (Hashtable<String, ClientChat>) attribut.get(this.canalChat);
        table.put(clientMock.donneId(), clientMock);
        Assert.assertEquals(1, table.size());
        Assert.assertTrue(table.contains(this.clientMock));
        EasyMock.verify(this.clientMock);
    }

 }