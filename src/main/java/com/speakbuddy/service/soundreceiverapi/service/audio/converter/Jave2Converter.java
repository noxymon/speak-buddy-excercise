package com.speakbuddy.service.soundreceiverapi.service.audio.converter;

import com.speakbuddy.service.soundreceiverapi.repository.MappingUserPhraseRepository;
import com.speakbuddy.service.soundreceiverapi.repository.models.MappingUsersPhrase;
import com.speakbuddy.service.soundreceiverapi.repository.models.MappingUsersPhraseId;
import lombok.RequiredArgsConstructor;
import org.jobrunr.jobs.annotations.Job;
import org.springframework.stereotype.Service;
import ws.schild.jave.Encoder;
import ws.schild.jave.EncoderException;
import ws.schild.jave.MultimediaObject;
import ws.schild.jave.encode.AudioAttributes;
import ws.schild.jave.encode.EncodingAttributes;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class Jave2Converter implements AsyncConverter {

    private final Encoder encoder;
    private final MappingUserPhraseRepository mappingUserPhraseRepository;

    @Job(name = "converter job file : %0 to %1")
    public void convert(String sourceAbsolutePath, String destinationAbsolutePath) {
        Path sourcePath = Paths.get(sourceAbsolutePath);
        if (!sourcePath.toFile().exists()) {
            throw new RuntimeException("File not found: " + sourcePath);
        }
        File source = sourcePath.toFile();

        Path destinationPath = Paths.get(destinationAbsolutePath);
        File destination = destinationPath.toFile();

        try {
            engineConvert(source, destination);
            updateMappingUserPhraseWhenExist(destination);
        } catch (EncoderException e) {
            throw new ConvertException(e);
        }
    }

    private void updateMappingUserPhraseWhenExist(File destination) {
        MappingUsersPhraseId mappingUsersPhraseId = constructMappingUserPhraseFrom(destination);
        Optional<MappingUsersPhrase> anyMappingUserPhrase = mappingUserPhraseRepository.findById(mappingUsersPhraseId);
        if (anyMappingUserPhrase.isEmpty()) {
            throw new RuntimeException("No mapping user phrase");
        }

        saveConvertedPathOf(destination, anyMappingUserPhrase);
    }

    private void saveConvertedPathOf(File destination, Optional<MappingUsersPhrase> anyMappingUserPhrase) {
        MappingUsersPhrase mappingUsersPhraseExisting = anyMappingUserPhrase.get();
        mappingUsersPhraseExisting.setConvertedFilePath(destination.getPath());
        mappingUserPhraseRepository.save(mappingUsersPhraseExisting);
    }

    private MappingUsersPhraseId constructMappingUserPhraseFrom(File destination) {
        String[] destinationFileNameInSplittedString = destination.getName()
                .replaceFirst(".wav", "")
                .split("_");
        Integer userId = Integer.valueOf(destinationFileNameInSplittedString[1]);
        Integer phraseId = Integer.valueOf(destinationFileNameInSplittedString[2]);

        MappingUsersPhraseId mappingUsersPhraseId = new MappingUsersPhraseId();
        mappingUsersPhraseId.setUserId(userId);
        mappingUsersPhraseId.setPhraseId(phraseId);
        return mappingUsersPhraseId;
    }

    private void engineConvert(File source, File target ) throws EncoderException {
        AudioAttributes audio = new AudioAttributes();
        audio.setCodec("pcm_s16le");
        audio.setBitRate(128000);
        audio.setChannels(2);
        audio.setSamplingRate(44100);

        EncodingAttributes attrs = new EncodingAttributes();
        attrs.setOutputFormat("wav");
        attrs.setAudioAttributes(audio);

        encoder.encode(new MultimediaObject(source), target, attrs, new ConvertProgressListener());
    }
}
