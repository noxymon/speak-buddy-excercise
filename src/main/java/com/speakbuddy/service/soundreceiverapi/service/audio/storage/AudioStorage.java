package com.speakbuddy.service.soundreceiverapi.service.audio.storage;

import com.speakbuddy.service.soundreceiverapi.service.audio.parameter.AudioSaverInput;
import com.speakbuddy.service.soundreceiverapi.service.audio.storage.parameter.StorageOutput;

import java.io.IOException;

/**
 * Interface for audio storage operations.
 */
public interface AudioStorage {

    /**
     * Saves the audio input to storage.
     *
     * @param input the audio saver input containing the audio data and metadata
     * @return the storage output containing information about the saved file
     * @throws IOException if an I/O error occurs during saving
     */
    StorageOutput save(AudioSaverInput input) throws StorageException;

    /**
     * Retrieves the audio file from storage.
     *
     * @param filename the name of the file to retrieve
     * @return the storage output containing the retrieved file
     * @throws IOException if an I/O error occurs during retrieval
     */
    StorageOutput retrieve(String filename) throws StorageException;
}