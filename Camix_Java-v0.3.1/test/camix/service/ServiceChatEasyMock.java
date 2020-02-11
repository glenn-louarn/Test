package camix.service;

import camix.communication.ProtocoleChat;
import org.easymock.EasyMock;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Before;
import org.junit.Test;

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
        this.clientMock.envoieMessage(String.format(ProtocoleChat.MESSAGE_DEPART_CHAT, clientMock.donneSurnom()));

        EasyMock.verify(this.clientMock);
    }
}