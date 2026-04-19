package com.canlas.song;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/canlas/songs")
public class SongController {

    private final SongRepository songRepository;

    public SongController(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    // GET /canlas/songs - retrieve all songs
    @GetMapping
    public List<Song> getAllSongs() {
        return songRepository.findAll();
    }

    // POST /canlas/songs - add a new song
    @PostMapping
    public Song addSong(@RequestBody Song song) {
        return songRepository.save(song);
    }

    // GET /canlas/songs/{id} - retrieve a song by id
    @GetMapping("/{id}")
    public Song getSongById(@PathVariable Long id) {
        return songRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Song not found"));
    }

    // DELETE /canlas/songs/{id} - delete a song by id
    @DeleteMapping("/{id}")
    public String deleteSong(@PathVariable Long id) {
        if (!songRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Song not found");
        }
        songRepository.deleteById(id);
        return "Song with ID " + id + " deleted.";
    }

    // PUT /canlas/songs/{id} - fully update an existing song
    @PutMapping("/{id}")
    public Song updateSong(@PathVariable Long id, @RequestBody Song songDetails) {
        Song existingSong = songRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Song not found"));

        existingSong.setTitle(songDetails.getTitle());
        existingSong.setArtist(songDetails.getArtist());
        existingSong.setAlbum(songDetails.getAlbum());
        existingSong.setGenre(songDetails.getGenre());
        existingSong.setUrl(songDetails.getUrl());

        return songRepository.save(existingSong);
    }

    // GET /canlas/songs/search/{keyword} - search songs by keyword in title, artist, album, or genre (case‑insensitive)
    @GetMapping("/search/{keyword}")
    public List<Song> searchSongs(@PathVariable String keyword) {
        return songRepository.findByTitleContainingIgnoreCaseOrArtistContainingIgnoreCaseOrAlbumContainingIgnoreCaseOrGenreContainingIgnoreCase(
                keyword, keyword, keyword, keyword);
    }
}