package atlg4.g49262.message;

import atlg4.g49262.users.User;

/**
 * @author El Amouri Meihdi <49262@etu.he2b.be>
 * @author Nicolas Rossitto, <49282@etu.he2b.be>
 */
public class MessageToRecipient implements Message {

    private final Type type;
    private final User author;
    private final User recipient;
    private final String text;

    public MessageToRecipient(Type type, User author, User recipient, String text) {
        this.type = type;
        this.author = author;
        this.recipient = recipient;
        this.text = text;
    }

    @Override
    public Type getType() {
        return type;
    }

    
    public User getAuthor() {
        return author;
    }

   
    public User getRecipient() {
        return recipient;
    }

    @Override
    public Object getContent() {
        return text;
    }

}
