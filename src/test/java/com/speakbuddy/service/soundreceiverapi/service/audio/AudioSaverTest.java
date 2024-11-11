package com.speakbuddy.service.soundreceiverapi.service.audio;

import com.speakbuddy.service.soundreceiverapi.repository.MappingUserPhraseRepository;
import com.speakbuddy.service.soundreceiverapi.repository.MasterPhraseRepository;
import com.speakbuddy.service.soundreceiverapi.repository.MasterUserRepositories;
import com.speakbuddy.service.soundreceiverapi.repository.models.MasterPhrase;
import com.speakbuddy.service.soundreceiverapi.repository.models.MasterUser;
import com.speakbuddy.service.soundreceiverapi.service.audio.converter.AsyncConverter;
import com.speakbuddy.service.soundreceiverapi.service.audio.parameter.AudioSaverInput;
import com.speakbuddy.service.soundreceiverapi.service.audio.storage.AudioStorage;
import com.speakbuddy.service.soundreceiverapi.service.audio.storage.parameter.StorageOutput;
import org.jobrunr.scheduling.JobScheduler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class AudioSaverTest {

    @Mock
    private MasterPhraseRepository masterPhraseRepository;
    @Mock
    private MasterUserRepositories masterUserRepositories;
    @Mock
    private MappingUserPhraseRepository mappingUserPhraseRepository;
    @Mock
    private AudioStorage audioStorage;
    @Mock
    private JobScheduler jobScheduler;
    @Mock
    private AsyncConverter asyncConverter;

    @InjectMocks
    private AudioSaver audioSaver;


    @Test
    void save_ThrowsRuntimeException_WhenUserNotFound() {
        AudioSaverInput audioSaverInput = new AudioSaverInput(1, 1, new MockMultipartFile("sampleFile", new byte[0]));
        when(masterUserRepositories.findById(any())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> audioSaver.save(audioSaverInput));
    }

    @Test
    void save_ThrowsRuntimeException_WhenPhraseNotFound() {
        AudioSaverInput audioSaverInput = new AudioSaverInput(1, 1, new MockMultipartFile("sampleFile", new byte[0]));
        when(masterUserRepositories.findById(any())).thenReturn(Optional.of(new MasterUser()));
        when(masterPhraseRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> audioSaver.save(audioSaverInput));
    }

    @Test
    void save_shouldTriggered_WhenUserAndPhraseFound() throws IOException {
        AudioSaverInput audioSaverInput = new AudioSaverInput(1, 1, new MockMultipartFile("sampleFile", new byte[0]));
        when(masterUserRepositories.findById(any())).thenReturn(Optional.of(new MasterUser()));
        when(masterPhraseRepository.findById(any())).thenReturn(Optional.of(new MasterPhrase()));
        when(audioStorage.save(any())).thenReturn(new StorageOutput(Path.of("path/to/audio.mp3")));

        audioSaver.save(audioSaverInput);

        verify(mappingUserPhraseRepository).save(any());
    }

}