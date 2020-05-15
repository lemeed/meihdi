/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atlg4.g49262.message;

import atlg4.g49262.users.User;
import java.util.List;

/**
 *
 * @author Meihdi El Amouri <49262@etu.he2b.be>
 */
public class MessagePrintWordFoundInHistoric implements Message {
    private List<String> text;
    private final Type type;

    public MessagePrintWordFoundInHistoric(List<String> text) {
        this.text = text;
        this.type = Type.PRINT_WORD_HISTORIC;
    }

    @Override
    public Type getType() {
        return type;
    }

    @Override
    public Object getContent() {
        return text;
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
