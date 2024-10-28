package com.speakbuddy.service.soundreceiverapi.service.audio.parameter;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.File;

@Getter
@RequiredArgsConstructor
public class AudioRetrieverOutput {
    private final File audioFile;
}
