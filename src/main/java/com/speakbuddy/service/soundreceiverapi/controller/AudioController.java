package com.speakbuddy.service.soundreceiverapi.controller;


import com.speakbuddy.service.soundreceiverapi.service.audio.AudioRetriever;
import com.speakbuddy.service.soundreceiverapi.service.audio.AudioSaver;
import com.speakbuddy.service.soundreceiverapi.service.audio.parameter.AudioRetrieverInput;
import com.speakbuddy.service.soundreceiverapi.service.audio.parameter.AudioRetrieverOutput;
import com.speakbuddy.service.soundreceiverapi.service.audio.parameter.AudioSaverInput;
import io.micrometer.core.instrument.util.IOUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;

@RestController
@RequestMapping("/api/v1/audio")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
public class AudioController {

    private final AudioSaver audioSaver;
    private final AudioRetriever audioRetriever;

    @PostMapping("/user/{userId}/phrase/{phraseId}")
    public ResponseEntity storeAudio(@PathVariable("userId") int userId, @PathVariable("phraseId") int phraseId,
                                     @RequestParam("audio_file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Empty file");
        }

        try {
            AudioSaverInput audioSaverInput = new AudioSaverInput(userId, phraseId,file);
            audioSaver.save(audioSaverInput);
        } catch (IOException e) {
           return ResponseEntity.badRequest().body(e.getMessage());
        }

        return ResponseEntity.accepted().build();
    }

    @GetMapping("/user/{userId}/phrase/{phraseId}/{format}")
    public ResponseEntity getAudioByFormat(@PathVariable("userId") int userId, @PathVariable("phraseId") int phraseId,
                                           @PathVariable("format") String format){
        AudioRetrieverInput request = new AudioRetrieverInput(userId, phraseId, format);

        try {
            AudioRetrieverOutput audioRetrieverOutput = audioRetriever.GetAudioFrom(request);
            byte[] contentFile = FileUtils.readFileToByteArray(audioRetrieverOutput.getAudioFile());
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType("audio/wav"))
                    .header("Accept-Ranges", "bytes")
                    .body(contentFile);
        } catch (RuntimeException | IOException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}