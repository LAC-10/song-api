package com.canlas.songapi.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/songs")
@CrossOrigin(origins = "*")
public class SongController {

    @GetMapping
    public String getAllSongs() {
        return "Hello from Song API";
    }

    @GetMapping("/{id}")
    public String getSongById(@PathVariable String id) {
        return "Song: " + id;
    }
}
