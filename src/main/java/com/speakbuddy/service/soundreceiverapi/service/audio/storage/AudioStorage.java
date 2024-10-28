package com.speakbuddy.service.soundreceiverapi.service.audio.storage;

import com.speakbuddy.service.soundreceiverapi.service.audio.parameter.AudioSaverInput;
import com.speakbuddy.service.soundreceiverapi.service.audio.storage.parameter.StorageOutput;

import java.io.IOException;

public interface AudioStorage {
    StorageOutput save(AudioSaverInput input) throws IOException;
    StorageOutput retrieve(String filename) throws IOException;
}
