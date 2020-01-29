package camix.service;

import org.easymock.*;
import org.junit.*;

import java.io.IOException;


public class ServiceChatEasyMock {

    @Mock
    private ClientChat clientMock;

    @TestSubject
    private ServiceChat serviceChat;

    @Before
    public void setup() {
        this.clientMock = EasyMock.createMock(ClientChat.class);
    }

    /**
    @Test(timeout = 2000)
    public void testInformeDepartClient() throws IOException {
        final String clientSurnom = "surnom du client";

        EasyMock.expect(
                this.clientMock.donneSurnom()
        ).andReturn(
                clientSurnom
        );

        serviceChat = new ServiceChat("");

        EasyMock.replay(this.clientMock);

        this.serviceChat.informeDepartClient(this.clientMock);

        EasyMock.verify(this.clientMock);
    }
    **/
}