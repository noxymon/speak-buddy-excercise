package com.speakbuddy.service.soundreceiverapi.service.audio.parameter;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamSource;

import java.io.InputStream;

@Getter
@RequiredArgsConstructor
public class AudioSaverInput {
    private final Integer userId;
    private final Integer phraseId;
    private final InputStreamSource file;
}
