package com.speakbuddy.service.soundreceiverapi.utils;


import com.speakbuddy.service.soundreceiverapi.service.audio.parameter.AudioSaverInput;

import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUtils {

    public static final String ORIGINAL_FILE_PATH_FORMAT = "%s\\original_%s_%s.m4a";
    public static final String DESTINATION_FILE_PATH_FORMAT = "%s\\converted_%s_%s.wav";

    public static String getFilenameFrom(Path rootLocation, AudioSaverInput input) {
        return String.format(ORIGINAL_FILE_PATH_FORMAT, rootLocation, input.getUserId(),input.getPhraseId());
    }

    public static Path getFilenamePathFrom(Path rootLocation, AudioSaverInput input) {
        return Paths.get(getFilenameFrom(rootLocation, input)).toAbsolutePath();
    }

    public static String getDestinationFilenameFrom(Path rootLocation, AudioSaverInput input) {
        return getConvertDestinationPathFrom(rootLocation, input).toAbsolutePath().toString();
    }

    public static Path getConvertDestinationPathFrom(Path rootLocation, AudioSaverInput input){
        String pathInString = String.format(DESTINATION_FILE_PATH_FORMAT, rootLocation, input.getUserId(),input.getPhraseId());
        return Paths.get(pathInString);
    }
}
