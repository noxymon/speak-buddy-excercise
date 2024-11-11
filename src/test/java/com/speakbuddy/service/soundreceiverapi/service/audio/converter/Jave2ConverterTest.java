package com.speakbuddy.service.soundreceiverapi.service.audio.converter;

import com.speakbuddy.service.soundreceiverapi.repository.MappingUserPhraseRepository;
import com.speakbuddy.service.soundreceiverapi.repository.models.MappingUsersPhrase;
import com.speakbuddy.service.soundreceiverapi.repository.models.MappingUsersPhraseId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ws.schild.jave.Encoder;
import ws.schild.jave.EncoderException;
import ws.schild.jave.MultimediaObject;
import ws.schild.jave.encode.EncodingAttributes;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
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

    @Test
    void mappingUserPhrase_shouldSave_WhenMappingUserPhraseExists() throws EncoderException {
        String sourcePath = "source/path.wav";
        String destinationPath = "destination/path_user_1_phrase_1.wav";

        Path pathMock = mock(Path.class);
        mockStatic(Paths.class).when(() -> Paths.get(anyString())).thenReturn(pathMock);

        File mockedFile = mock(File.class);
        when(pathMock.toFile()).thenReturn(mockedFile);

        when(mockedFile.exists()).thenReturn(true);
        when(mockedFile.getName()).thenReturn("original_1_1.wav");

        doNothing().when(encoder).encode(any(MultimediaObject.class), any(File.class), any(EncodingAttributes.class));
        when(mappingUserPhraseRepository.findById(any(MappingUsersPhraseId.class))).thenReturn(Optional.of(new MappingUsersPhrase()));
        when(mappingUserPhraseRepository.findById(any(MappingUsersPhraseId.class))).thenReturn(Optional.of(new MappingUsersPhrase()));

        jave2Converter.convert(sourcePath, destinationPath);
    }
}