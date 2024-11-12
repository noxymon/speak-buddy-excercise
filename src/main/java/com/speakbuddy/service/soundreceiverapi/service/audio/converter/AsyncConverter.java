package com.speakbuddy.service.soundreceiverapi.service.audio.converter;

/**
 * Interface for asynchronous audio conversion.
 */
public interface AsyncConverter {

    /**
     * Converts an audio file from the source path to the destination path.
     *
     * @param sourceAbsolutePath the absolute path of the source audio file
     * @param destinationAbsolutePath the absolute path of the destination audio file
     * @throws ConvertException if an error occurs during conversion
     */
    void convert(String sourceAbsolutePath, String destinationAbsolutePath) throws ConvertException;
}
