/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atlg4.g49262.message;

import atlg4.g49262.users.User;

/**
 * Represent the check answer message between server and client.
 * @author Meihdi El Amouri <49262@etu.he2b.be>
 */
public class MessageCheckAnswer implements Message {

    public String name;
    private final Type type;

    public MessageCheckAnswer(String text) {
        this.name = text;
        this.type = Type.CHECK_ANSWER;
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

    public void setContent(String mess) {
        this.name = mess;
    }
}
