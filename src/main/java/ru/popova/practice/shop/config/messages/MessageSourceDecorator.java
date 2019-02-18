package ru.popova.practice.shop.config.messages;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Component;

import java.util.Locale;


/**
 * класс для получения сообщений
 */
@Component
public class MessageSourceDecorator {

    private MessageSource messageSource;

    @Autowired
    public MessageSourceDecorator(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    /**
     * получение сообщения
     * @param key ключ, по которому ищется сообщение
     * @return сообщение или ключ, если сообщение не было найдено
     */
    public String getMessage(String key) {
        try {
            return messageSource.getMessage(key, null, Locale.getDefault());
        } catch (NoSuchMessageException e) {
            return key;
        }
    }
}
