/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atlg4.g49262.message;

import atlg4.g49262.users.User;

/**
 *
 * @author Meihdi El Amouri <49262@etu.he2b.be>
 */
public class MessageCheckWordFound implements Message {

    Type type;

    String name;

    public MessageCheckWordFound(String name) {
        type = Type.NB_FOUND_USER;
        this.name = name;
    }

    @Override
    public Type getType() {
        return type;
    }

    @Override
    public User getAuthor() {
        return User.ADMIN;
    }

    @Override
    public User getRecipient() {
        return User.EVERYBODY;
    }

    @Override
    public Object getContent() {
        return name;
    }
}
