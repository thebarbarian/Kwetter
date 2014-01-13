/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kwetter.beans;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import kwetter.dao.KwetterService;
import kwetter.domain.Tweet;
import kwetter.domain.TweetUser;

/**
 *
 * @author grave
 */
@MessageDriven(activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
    @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "jms/KwetterReceiveQueue")
})
public class KwetterMessageBeanListener implements MessageListener {

    @EJB
    private KwetterService kwetterService;
    private String msgFromQueue;

    /**
     * @param <T> the return type
     * @param retvalClass the returned value's {@link Class}
     * @param jndi the JNDI path to the resource
     * @return the resource at the specified {@code jndi} path
     */
    private static <T> T lookup(Class<T> retvalClass, String jndi) {
        try {
            return retvalClass.cast(InitialContext.doLookup(jndi));
        } catch (NamingException ex) {
            throw new IllegalArgumentException("failed to lookup instance of " + retvalClass + " at " + jndi, ex);
        }
    }

    @Override
    public void onMessage(Message message) {
        String eol = System.getProperty("line.separator");

        try {
            String messageData = message.getBody(String.class);

            System.out.println(messageData + eol
                    + "Message ID: " + message.getJMSMessageID() + eol
                    + "DeliveryMode: " + message.getJMSDeliveryMode() + eol
                    + "Expiration: " + message.getJMSExpiration() + eol
                    + "Message type: " + message.getJMSType() + eol);

            String username = messageData.substring(messageData.indexOf(':') + 1, messageData.indexOf("says :") - 1).trim();
            String usermessage = messageData.substring(messageData.indexOf("says :") + "says :".length()).trim();
            System.out.println("Username: " + username);
            System.out.println("Usermessage: " + usermessage);
            TweetUser myFirstUser = kwetterService.findUser(username);
            kwetterService.createTweet(myFirstUser, usermessage);
        } catch (JMSException ex) {
            Logger.getLogger(KwetterMessageBeanListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
