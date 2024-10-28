package com.speakbuddy.service.soundreceiverapi.service.audio.converter;

public class ConvertException extends RuntimeException {
    public ConvertException(String message) {
        super(message);
    }

    public ConvertException(Throwable cause) {
        super(cause);
    }
}
