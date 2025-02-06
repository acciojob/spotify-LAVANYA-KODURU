package com.driver;

import java.util.*;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("spotify")
public class SpotifyController {

    // Autowire the SpotifyService class
    SpotifyService spotifyService = new SpotifyService();

    @PostMapping("/add-user")
    public String createUser(@RequestParam(name = "name") String name, @RequestParam(name = "mobile") String mobile){
        // Create the user with the given name and mobile number
        spotifyService.createUser(name, mobile);
        return "Success";
    }

    @PostMapping("/add-artist")
    public String createArtist(@RequestParam(name = "name") String name){
        // Create the artist with the given name
        spotifyService.createArtist(name);
        return "Success";
    }

    @PostMapping("/add-album")
    public String createAlbum(@RequestParam(name = "title") String title, @RequestParam(name = "artistName") String artistName){
        // If the artist does not exist, create an artist with the given name
        // Create an album with the given title and artist
        spotifyService.createAlbum(title, artistName);
        return "Success";
    }

    @PostMapping("/add-song")
    public String createSong(@RequestParam(name = "title") String title, @RequestParam(name = "albumName") String albumName, @RequestParam(name = "length") int length) throws Exception{
        // If the album does not exist, throw an exception
        // Create and add the song to the respective album
        spotifyService.createSong(title, albumName, length);
        return "Success";
    }

    @PostMapping("/add-playlist-on-length")
    public String createPlaylistOnLength(@RequestParam(name = "mobile") String mobile, @RequestParam(name = "title") String title, @RequestParam(name = "length") int length) throws Exception{
        // Create a playlist with the given title and add all songs having the given length to that playlist
        // The creator of the playlist will be the given user
        spotifyService.createPlaylistOnLength(mobile, title, length);
        return "Success";
    }

    @PostMapping("/add-playlist-on-name")
    public String createPlaylistOnName(@RequestParam(name = "mobile") String mobile, @RequestParam(name = "title") String title, @RequestParam(name = "songTitles") List<String> songTitles) throws Exception{
        // Create a playlist with the given title and add all songs having the given titles to that playlist
        // The creator of the playlist will be the given user
        spotifyService.createPlaylistOnName(mobile, title, songTitles);
        return "Success";
    }

    @PutMapping("/find-playlist")
    public String findPlaylist(@RequestParam(name = "mobile") String mobile, @RequestParam(name = "playlistTitle") String playlistTitle) throws Exception{
        // Find the playlist with the given title and add the user as a listener of that playlist
        // If the user does not exist, throw an exception
        // If the playlist does not exist, throw an exception
        spotifyService.findPlaylist(mobile, playlistTitle);
        return "Success";
    }

    @PutMapping("/like-song")
    public String likeSong(@RequestParam(name = "mobile") String mobile, @RequestParam(name = "songTitle") String songTitle) throws Exception{
        // The user likes the given song. The corresponding artist of the song gets auto-liked
        // If the user does not exist, throw an exception
        // If the song does not exist, throw an exception
        spotifyService.likeSong(mobile, songTitle);
        return "Success";
    }

    @GetMapping("/popular-artist")
    public String mostPopularArtist(){
        // Return the artist name with maximum likes
        return spotifyService.getMostPopularArtist();
    }

    @GetMapping("/popular-song")
    public String mostPopularSong(){
        // Return the song title with maximum likes
        return spotifyService.getMostPopularSong();
    }
}
