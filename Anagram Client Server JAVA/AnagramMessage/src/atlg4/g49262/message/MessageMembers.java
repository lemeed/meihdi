package atlg4.g49262.message;

import atlg4.g49262.users.Members;
import atlg4.g49262.users.User;

/**
 *
 * @author Nicolas Rossitto, <49282@etu.he2b.be>
 */
public class MessageMembers implements Message {

    private final Members members;

    public MessageMembers(Members members) {
        this.members = members;
    }

    public User getAuthor() {
        return User.ADMIN;
    }

    public User getRecipient() {
        return User.EVERYBODY;
    }

    @Override
    public Type getType() {
        return Type.MEMBERS;
    }

    @Override
    public Object getContent() {
        return members;
    }

    

}
