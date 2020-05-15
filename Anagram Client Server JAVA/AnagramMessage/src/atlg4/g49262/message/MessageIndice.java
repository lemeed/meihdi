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
public class MessageIndice implements Message {
    public char name;
    private final Type type;

    public MessageIndice(char text) {
        this.name = text;
        this.type = Type.INDICE;
    }

    @Override
    public Type getType() {
        return type;
    }

    @Override
    public Object getContent() {
        return name;
    }

    @Override
    public User getAuthor() {
        return User.ADMIN;
    }

    @Override
    public User getRecipient() {
        return User.EVERYBODY;
    }

   
}


