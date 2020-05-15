/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atlg4.g49262.message;

import atlg4.g49262.users.User;

/**
 * Represent the Alert player message.
 * @author Meihdi El Amouri <49262@etu.he2b.be>
 */
public class MessageAlertPlayer implements Message {
    public String name;
    private final Type type;

    public MessageAlertPlayer(String text) {
        this.name = text;
        this.type = Type.ALERT_PLAYER;
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
