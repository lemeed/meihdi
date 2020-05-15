package atlg4.g49262.message;

import atlg4.g49262.users.User;

/**
 * @author El Amouri Meihdi <49262@etu.he2b.be>
 * @author Nicolas Rossitto, <49282@etu.he2b.be>
 */
public class MessageProfile implements Message {

    private final User author;

    public MessageProfile(int id, String name) {
        author = new User(id, name);
    }

    public User getAuthor() {
        return author;
    }

    public User getRecipient() {
        return User.ADMIN;
    }

    @Override
    public Type getType() {
        return Type.PROFILE;
    }

    @Override
    public Object getContent() {
        return author;
    }

}
