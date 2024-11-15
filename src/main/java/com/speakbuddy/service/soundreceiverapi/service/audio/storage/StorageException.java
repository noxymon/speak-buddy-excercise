package com.speakbuddy.service.soundreceiverapi.service.audio.storage;

import com.speakbuddy.service.soundreceiverapi.service.audio.ServiceException;

public class StorageException extends ServiceException {
    public StorageException(String message) {
        super(message);
    }

    public StorageException(Throwable cause) {
        super(cause);
    }
}
