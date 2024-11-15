package com.speakbuddy.service.soundreceiverapi.controller;


import com.speakbuddy.service.soundreceiverapi.service.audio.AudioRetriever;
import com.speakbuddy.service.soundreceiverapi.service.audio.AudioSaver;
import com.speakbuddy.service.soundreceiverapi.service.audio.ServiceException;
import com.speakbuddy.service.soundreceiverapi.service.audio.parameter.AudioRetrieverInput;
import com.speakbuddy.service.soundreceiverapi.service.audio.parameter.AudioRetrieverOutput;
import com.speakbuddy.service.soundreceiverapi.service.audio.parameter.AudioSaverInput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/api/v1/audio")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
public class AudioController {

    private final AudioSaver audioSaver;
    private final AudioRetriever audioRetriever;

    @Operation(summary = "Upload audio file to server", description = "it needs userId, PhraseId, and audio file through form uploaded multipart. This api also automatically convert the uploaded file to specific format.")
    @ApiResponses(value = {@ApiResponse(description = "Success operation")})
    @PostMapping(value = "/user/{userId}/phrase/{phraseId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity storeAudio(@PathVariable("userId") int userId, @PathVariable("phraseId") int phraseId,
                                     @RequestParam("audio_file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Empty file");
        }

        try {
            AudioSaverInput audioSaverInput = new AudioSaverInput(userId, phraseId,file);
            audioSaver.save(audioSaverInput);
        } catch (ServiceException e) {
           return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            log.error(String.format("[storeAudio][%s][%s] Error occured. Cause: ", userId, phraseId), e);
            return ResponseEntity.internalServerError().body("We'll back soon");
        }

        return ResponseEntity.accepted().build();
    }

    @Operation(summary = "Retrieve audio file by format", description = "Retrieve an audio file for a specific user and phrase in the specified format.")
    @ApiResponses(value = {
            @ApiResponse(
                    description = "Success operation",
                    content = {@Content(mediaType = "audio/wav")}
            )
    })
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
        } catch (ServiceException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }  catch (Exception e) {
            log.error(String.format("[getAudioByFormat][%s][%s] Error occured. Cause: ", userId, phraseId), e);
            return ResponseEntity.internalServerError().body("We'll back soon");
        }
    }
}