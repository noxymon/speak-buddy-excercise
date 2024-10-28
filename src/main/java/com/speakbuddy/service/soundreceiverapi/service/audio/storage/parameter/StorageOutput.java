package com.speakbuddy.service.soundreceiverapi.service.audio.storage.parameter;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.nio.file.Path;

@Getter
@RequiredArgsConstructor
public class StorageOutput {
    private final Path filename;

    public String absolutePathToString(){
        return filename.toAbsolutePath().toString();
    }
}
