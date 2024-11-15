package com.speakbuddy.service.soundreceiverapi.service.audio;

import com.speakbuddy.service.soundreceiverapi.repository.MappingUserPhraseRepository;
import com.speakbuddy.service.soundreceiverapi.repository.MasterPhraseRepository;
import com.speakbuddy.service.soundreceiverapi.repository.MasterUserRepositories;
import com.speakbuddy.service.soundreceiverapi.repository.models.MappingUsersPhrase;
import com.speakbuddy.service.soundreceiverapi.repository.models.MappingUsersPhraseId;
import com.speakbuddy.service.soundreceiverapi.repository.models.MasterPhrase;
import com.speakbuddy.service.soundreceiverapi.repository.models.MasterUser;
import com.speakbuddy.service.soundreceiverapi.service.audio.converter.AsyncConverter;
import com.speakbuddy.service.soundreceiverapi.service.audio.parameter.AudioSaverInput;
import com.speakbuddy.service.soundreceiverapi.service.audio.storage.AudioStorage;
import com.speakbuddy.service.soundreceiverapi.service.audio.storage.StorageException;
import com.speakbuddy.service.soundreceiverapi.service.audio.storage.parameter.StorageOutput;
import com.speakbuddy.service.soundreceiverapi.utils.FileUtils;
import lombok.RequiredArgsConstructor;
import org.jobrunr.scheduling.JobScheduler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AudioSaver {

    private final AsyncConverter converter;
    private final AudioStorage audioStorage;
    private final JobScheduler jobScheduler;
    private final MasterPhraseRepository masterPhraseRepository;
    private final MasterUserRepositories masterUserRepositories;
    private final MappingUserPhraseRepository mappingUserPhraseRepository;

    @Value("${application.audio.root}")
    private Path rootLocation;

    public void save(AudioSaverInput audioSaverInput) {
        validateThenSaveUserPhraseMapping(audioSaverInput);

        String sourceAbsolutePath = saveAndGetSourceAbsolutePath(audioSaverInput);
        String convertDestinationAbsolutePath = FileUtils.getDestinationFilenameFrom(rootLocation, audioSaverInput);

        jobScheduler.enqueue(() -> converter.convert(sourceAbsolutePath, convertDestinationAbsolutePath));
    }

    private String saveAndGetSourceAbsolutePath(AudioSaverInput audioSaverInput) throws StorageException {
        StorageOutput storageOutput = audioStorage.save(audioSaverInput);
        return storageOutput.absolutePathToString();
    }

    private void validateThenSaveUserPhraseMapping(AudioSaverInput audioSaverInput) {
        Optional<MasterUser> anyMasterUser = masterUserRepositories.findById(audioSaverInput.getUserId());
        if (anyMasterUser.isEmpty()) {
            throw new ServiceException("User not found");
        }

        Optional<MasterPhrase> anyMasterPhrase = masterPhraseRepository.findById(audioSaverInput.getPhraseId());
        if (anyMasterPhrase.isEmpty()) {
            throw new ServiceException("Phrase not found");
        }

        saveMappingUserWithPhrase(audioSaverInput, anyMasterUser);
    }

    private void saveMappingUserWithPhrase(AudioSaverInput audioSaverInput, Optional<MasterUser> anyMasterUser) {
        MappingUsersPhraseId mappingUsersPhraseId = new MappingUsersPhraseId();
        mappingUsersPhraseId.setPhraseId(audioSaverInput.getPhraseId());
        mappingUsersPhraseId.setUserId(anyMasterUser.get().getId());

        MappingUsersPhrase mappingUsersPhrase = new MappingUsersPhrase();
        mappingUsersPhrase.setId(mappingUsersPhraseId);
        mappingUsersPhrase.setOriginalFilePath(FileUtils.getFilenameFrom(rootLocation, audioSaverInput));
        mappingUserPhraseRepository.save(mappingUsersPhrase);
    }
}
