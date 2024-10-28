package com.speakbuddy.service.soundreceiverapi.service.audio.converter;

import lombok.extern.slf4j.Slf4j;
import ws.schild.jave.info.MultimediaInfo;
import ws.schild.jave.progress.EncoderProgressListener;

@Slf4j
public class ConvertProgressListener implements EncoderProgressListener {

    public ConvertProgressListener() {
        //code
    }

    public void message(String m) {
        //code
    }

    @Override
    public void sourceInfo(MultimediaInfo multimediaInfo) {

    }

    public void progress(int p) {
        double progress = p / 10;
        log.info(progress + "%");
    }
}
