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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
public class AudioRetriever {

    private final String outputFormat;
    private final AudioStorage audioStorage;
    private final MasterPhraseRepository masterPhraseRepository;
    private final MasterUserRepositories masterUserRepositories;
    private final MappingUserPhraseRepository mappingUserPhraseRepository;

    @Autowired
    public AudioRetriever(@Value("${application.audio.output.format}") String outputFormat, AudioStorage audioStorage, MasterPhraseRepository masterPhraseRepository, MasterUserRepositories masterUserRepositories, MappingUserPhraseRepository mappingUserPhraseRepository) {
        this.outputFormat = outputFormat;
        this.audioStorage = audioStorage;
        this.masterPhraseRepository = masterPhraseRepository;
        this.masterUserRepositories = masterUserRepositories;
        this.mappingUserPhraseRepository = mappingUserPhraseRepository;
    }

    public AudioRetrieverOutput GetAudioFrom(AudioRetrieverInput request) throws IOException {
        if (!outputFormat.equals(request.getAudioFormat())) {
            throw new RuntimeException("Audio format not supported");
        }

        Optional<MasterUser> anyMasterUser = masterUserRepositories.findById(request.getUserId());
        if (anyMasterUser.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        Optional<MasterPhrase> anyMasterPhrase = masterPhraseRepository.findById(request.getPhraseId());
        if (anyMasterPhrase.isEmpty()) {
            throw new RuntimeException("Phrase not found");
        }

        Optional<MappingUsersPhrase> anyMappingUserPhrase = findAnyMappingUserPhraseFrom(request);
        return composeAudioRetrieverOutputFrom(anyMappingUserPhrase);
    }

    private AudioRetrieverOutput composeAudioRetrieverOutputFrom(Optional<MappingUsersPhrase> anyMappingUserPhrase) throws IOException {
        String convertedFilePath = anyMappingUserPhrase.get().getConvertedFilePath();
        StorageOutput retrieve = audioStorage.retrieve(convertedFilePath);
        return new AudioRetrieverOutput(retrieve.getFilename().toFile());
    }

    private Optional<MappingUsersPhrase> findAnyMappingUserPhraseFrom(AudioRetrieverInput request) {
        MappingUsersPhraseId mappingUsersPhraseId = new MappingUsersPhraseId();
        mappingUsersPhraseId.setUserId(request.getUserId());
        mappingUsersPhraseId.setPhraseId(request.getPhraseId());

        Optional<MappingUsersPhrase> anyMappingUserPhrase = mappingUserPhraseRepository.findById(mappingUsersPhraseId);
        if (anyMappingUserPhrase.isEmpty()) {
            throw new RuntimeException("User have not record this phrase yet");
        }
        return anyMappingUserPhrase;
    }


}
