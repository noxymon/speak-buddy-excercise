package com.speakbuddy.service.soundreceiverapi.service.audio;

import com.speakbuddy.service.soundreceiverapi.repository.MappingUserPhraseRepository;
import com.speakbuddy.service.soundreceiverapi.repository.MasterPhraseRepository;
import com.speakbuddy.service.soundreceiverapi.repository.MasterUserRepositories;
import com.speakbuddy.service.soundreceiverapi.repository.models.MappingUsersPhrase;
import com.speakbuddy.service.soundreceiverapi.repository.models.MappingUsersPhraseId;
import com.speakbuddy.service.soundreceiverapi.repository.models.MasterPhrase;
import com.speakbuddy.service.soundreceiverapi.repository.models.MasterUser;
import com.speakbuddy.service.soundreceiverapi.service.audio.parameter.AudioRetrieverInput;
import com.speakbuddy.service.soundreceiverapi.service.audio.parameter.AudioRetrieverOutput;
import com.speakbuddy.service.soundreceiverapi.service.audio.storage.AudioStorage;
import com.speakbuddy.service.soundreceiverapi.service.audio.storage.parameter.StorageOutput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class AudioRetrieverTest {

    @Mock
    private AudioStorage audioStorage;

    @Mock
    private MasterPhraseRepository masterPhraseRepository;

    @Mock
    private MasterUserRepositories masterUserRepositories;

    @Mock
    private MappingUserPhraseRepository mappingUserPhraseRepository;

    private AudioRetriever audioRetriever;

    @BeforeEach
    void setUp() {
        String outputFormat = "wav";
        audioRetriever = new AudioRetriever(outputFormat, audioStorage, masterPhraseRepository, masterUserRepositories, mappingUserPhraseRepository);
    }

    @Test
    void GetAudioFrom_ReturnsAudioRetrieverOutput_WhenRequestIsValid() throws IOException {
        AudioRetrieverInput request = new AudioRetrieverInput(1, 1, "wav");

        MasterUser masterUser = new MasterUser();
        MasterPhrase masterPhrase = new MasterPhrase();
        MappingUsersPhrase mappingUsersPhrase = new MappingUsersPhrase();
        mappingUsersPhrase.setConvertedFilePath("path/to/audio.mp3");

        when(masterUserRepositories.findById(1)).thenReturn(Optional.of(masterUser));
        when(masterPhraseRepository.findById(1)).thenReturn(Optional.of(masterPhrase));
        when(mappingUserPhraseRepository.findById(any(MappingUsersPhraseId.class))).thenReturn(Optional.of(mappingUsersPhrase));
        when(audioStorage.retrieve("path/to/audio.mp3")).thenReturn(new StorageOutput(new File("path/to/audio.mp3").toPath()));

        AudioRetrieverOutput output = audioRetriever.GetAudioFrom(request);

        assertNotNull(output);
        assertEquals(new File("path/to/audio.mp3").getPath(), output.getAudioFile().getPath());
    }

    @Test
    void GetAudioFrom_ThrowsException_WhenAudioFormatNotSupported() {
        AudioRetrieverInput request = new AudioRetrieverInput(1, 1, "mp3");
        RuntimeException exception = assertThrows(RuntimeException.class, () -> audioRetriever.GetAudioFrom(request));
        assertEquals("Audio format not supported", exception.getMessage());
    }

    @Test
    void GetAudioFrom_ThrowsException_WhenUserNotFound() {
        AudioRetrieverInput request = new AudioRetrieverInput(1, 1, "wav");
        when(masterUserRepositories.findById(1)).thenReturn(Optional.empty());
        RuntimeException exception = assertThrows(RuntimeException.class, () -> audioRetriever.GetAudioFrom(request));
        assertEquals("User not found", exception.getMessage());
    }

    @Test
    void GetAudioFrom_ThrowsException_WhenPhraseNotFound() {
        AudioRetrieverInput request = new AudioRetrieverInput(1, 1, "wav");

        MasterUser masterUser = new MasterUser();
        when(masterUserRepositories.findById(1)).thenReturn(Optional.of(masterUser));
        when(masterPhraseRepository.findById(1)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> audioRetriever.GetAudioFrom(request));
        assertEquals("Phrase not found", exception.getMessage());
    }

    @Test
    void GetAudioFrom_ThrowsException_WhenMappingUserPhraseNotFound() {
        AudioRetrieverInput request = new AudioRetrieverInput(1, 1, "wav");

        MasterUser masterUser = new MasterUser();
        when(masterUserRepositories.findById(1)).thenReturn(Optional.of(masterUser));

        MasterPhrase masterPhrase = new MasterPhrase();
        when(masterPhraseRepository.findById(1)).thenReturn(Optional.of(masterPhrase));

        when(mappingUserPhraseRepository.findById(any(MappingUsersPhraseId.class))).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> audioRetriever.GetAudioFrom(request));
        assertEquals("User have not record this phrase yet", exception.getMessage());
    }
}