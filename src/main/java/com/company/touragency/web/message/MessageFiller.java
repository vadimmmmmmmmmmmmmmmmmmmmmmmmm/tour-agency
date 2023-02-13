package com.razkuuuuuuu.touragency.web.message;

import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;

import static com.razkuuuuuuu.touragency.constants.SessionAttributes.MESSAGES;

public interface MessageFiller {
    /**
     * add message to message list, or create new if it's null
     * @param session user's HTTP session
     * @param type type of message
     * @param messageCode code to be used for localisation purposes in jsp
     */
    default void addMessage(HttpSession session, Message.MessageType type, String messageCode) {
        List<Message> messages = (List<Message>) session.getAttribute(MESSAGES);
        if (messages==null) {
            messages = new ArrayList<>();
        }

        Message message = new Message();
        message.setMessageType(type);
        message.setMessageCode(messageCode);
        messages.add(message);

        session.setAttribute(MESSAGES, messages);
    }
}
