package com.speakbuddy.service.soundreceiverapi.service.audio.converter;

import ws.schild.jave.EncoderException;

public interface AsyncConverter {
    void convert(String sourceAbsolutePath, String destinationAbsolutePath) throws ConvertException;
}
