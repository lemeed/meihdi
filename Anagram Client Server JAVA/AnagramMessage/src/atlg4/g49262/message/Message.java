
package atlg4.g49262.message;

import atlg4.g49262.users.User;
import java.io.Serializable;

/**
 *@author El Amouri Meihdi <49262@etu.he2b.be>
 * @author Nicolas Rossitto, <49282@etu.he2b.be>
 */
    
public interface Message extends Serializable {

    /**
     * Return the message type.
     *
     * @return the message type.
     */
    Type getType();

    /**
     * Return the message author.
     *
     * @return the message author.
     */
    User getAuthor();

    /**
     * Return the message recipient.
     *
     * @return the message recipient.
     */
    User getRecipient();

    /**
     * Return the message content.
     *
     * @return the message content.
     */
    Object getContent();

    
}
