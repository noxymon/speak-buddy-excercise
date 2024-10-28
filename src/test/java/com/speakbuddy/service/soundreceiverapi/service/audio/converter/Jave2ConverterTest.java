package com.speakbuddy.service.soundreceiverapi.service.audio.converter;

import com.speakbuddy.service.soundreceiverapi.repository.MappingUserPhraseRepository;
import com.speakbuddy.service.soundreceiverapi.repository.models.MappingUsersPhraseId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ws.schild.jave.Encoder;

import java.io.File;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class Jave2ConverterTest {

    @Mock
    private Encoder encoder;

    @Mock
    private MappingUserPhraseRepository mappingUserPhraseRepository;

    @InjectMocks
    private Jave2Converter jave2Converter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void convert_ThrowsException_WhenSourceFileNotFound() {
        String sourcePath = "nonexistent/source/path.wav";
        String destinationPath = "destination/path.wav";

        assertThrows(RuntimeException.class, () -> jave2Converter.convert(sourcePath, destinationPath));
    }

    @Test
    void convert_ThrowsException_WhenMappingUserPhraseNotFound() {
        String sourcePath = "source/path.wav";
        String destinationPath = "destination/path_user_1_phrase_1.wav";

        File sourceFile = mock(File.class);
        when(sourceFile.exists()).thenReturn(true);
        when(mappingUserPhraseRepository.findById(any(MappingUsersPhraseId.class))).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> jave2Converter.convert(sourcePath, destinationPath));
    }
}