package carmix.service;

import camix.service.ClientChat;
import camix.service.ServiceChat;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.omg.CORBA.TIMEOUT;

import javax.xml.ws.Service;
import java.io.IOException;

public class ServiceChatTestEx2EasyMock {
     ClientChat clientChat;

     @Test(timeout= 2000)
    public void testinformeDepartClient() throws IOException {
        final ServiceChat service = new ServiceChat("", 12345);
        clientChat = EasyMock.createMock(ClientChat.class);
        service.informeDepartClient(clientChat);
        clientChat.changeSurnom("newNom");
        EasyMock.expect(
               this.clientChat.donneSurnom()
        ).andReturn("newNom");
        EasyMock.replay(this.clientChat);
        EasyMock.verify();
    }
}
