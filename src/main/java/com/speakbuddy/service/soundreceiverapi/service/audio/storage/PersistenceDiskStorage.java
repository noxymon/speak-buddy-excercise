package com.speakbuddy.service.soundreceiverapi.service.audio.storage;

import com.speakbuddy.service.soundreceiverapi.service.audio.parameter.AudioSaverInput;
import com.speakbuddy.service.soundreceiverapi.service.audio.storage.parameter.StorageOutput;
import com.speakbuddy.service.soundreceiverapi.utils.FileUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
@RequiredArgsConstructor
public class PersistenceDiskStorage implements AudioStorage {

    @Value("${application.audio.root}")
    private Path rootLocation;

    @Override
    public StorageOutput save(AudioSaverInput audioSaverInput) throws IOException {
        Path destinationFile = FileUtils.getFilenamePathFrom(rootLocation, audioSaverInput);

        try (InputStream inputStream = audioSaverInput.getFile().getInputStream()) {
            Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
        }

        return new StorageOutput(destinationFile);
    }

    @Override
    public StorageOutput retrieve(String filename) throws IOException {
        return new StorageOutput(Paths.get(filename));
    }
}
