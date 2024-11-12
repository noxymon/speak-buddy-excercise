package com.speakbuddy.service.soundreceiverapi.service.audio.storage;

import com.speakbuddy.service.soundreceiverapi.service.audio.parameter.AudioSaverInput;
import com.speakbuddy.service.soundreceiverapi.service.audio.storage.parameter.StorageOutput;
import com.speakbuddy.service.soundreceiverapi.utils.FileUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * Service for persisting audio files to disk storage.
 */
@Service
@RequiredArgsConstructor
public class PersistenceDiskStorage implements AudioStorage {

    @Value("${application.audio.root}")
    private Path rootLocation;

    /**
     * Saves the audio file to the disk.
     *
     * @param audioSaverInput the input containing the audio file to be saved
     * @return the output containing the path to the saved file
     * @throws IOException if an I/O error occurs
     */
    @Override
    public StorageOutput save(AudioSaverInput audioSaverInput) throws IOException {
        Path destinationFile = FileUtils.getFilenamePathFrom(rootLocation, audioSaverInput);

        try (InputStream inputStream = audioSaverInput.getFile().getInputStream()) {
            Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
        }

        return new StorageOutput(destinationFile);
    }

    /**
     * Retrieves the audio file from the disk.
     *
     * @param filename the name of the file to be retrieved
     * @return the output containing the path to the retrieved file
     * @throws IOException if an I/O error occurs
     */
    @Override
    public StorageOutput retrieve(String filename) throws IOException {
        return new StorageOutput(Paths.get(filename));
    }
}