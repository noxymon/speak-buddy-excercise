package com.speakbuddy.service.soundreceiverapi.service.audio.parameter;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AudioRetrieverInput {
    private final int userId;
    private final int phraseId;
    private final String audioFormat;
}
