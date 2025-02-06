package com.driver;

import java.util.*;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;

@Repository
public class SpotifyRepository {
    public HashMap<Artist, List<Album>> artistAlbumMap;
    public HashMap<Album, List<Song>> albumSongMap;
    public HashMap<Playlist, List<Song>> playlistSongMap;
    public HashMap<Playlist, List<User>> playlistListenerMap;
    public HashMap<User, Playlist> creatorPlaylistMap;
    public HashMap<User, List<Playlist>> userPlaylistMap;
    public HashMap<Song, List<User>> songLikeMap;

    public List<User> users;
    public List<Song> songs;
    public List<Playlist> playlists;
    public List<Album> albums;
    public List<Artist> artists;

    public SpotifyRepository(){
        //To avoid hitting apis multiple times, initialize all the hashmaps here with some dummy data
        artistAlbumMap = new HashMap<>();
        albumSongMap = new HashMap<>();
        playlistSongMap = new HashMap<>();
        playlistListenerMap = new HashMap<>();
        creatorPlaylistMap = new HashMap<>();
        userPlaylistMap = new HashMap<>();
        songLikeMap = new HashMap<>();

        users = new ArrayList<>();
        songs = new ArrayList<>();
        playlists = new ArrayList<>();
        albums = new ArrayList<>();
        artists = new ArrayList<>();
    }

    public User createUser(String name, String mobile) {
        User user=new User(name,mobile);
        users.add(user);
        return user;
    }

    public Artist createArtist(String name) {
        Artist artist=new Artist(name);
        artists.add(artist);
        return artist;
    }

  public Album createAlbum(String title, String artistName) {
    Artist artist = artists.stream().filter(a -> a.getName().equals(artistName)).findFirst().orElse(null);
    if (artist == null) {
        artist = createArtist(artistName);
    }
    Album album = new Album(title);
    artistAlbumMap.putIfAbsent(artist, new ArrayList<>());
    artistAlbumMap.get(artist).add(album);
    albums.add(album);
    return album;
}


    public Song createSong(String title, String albumName, int length) throws Exception{
         Album album = albums.stream().filter(a -> a.getTitle().equals(albumName)).findFirst().orElse(null);
    if (album == null) {
        throw new Exception("Album does not exist");
    }
    Song song = new Song(title, length);
    albumSongMap.putIfAbsent(album, new ArrayList<>());
    albumSongMap.get(album).add(song);
    songs.add(song);
    return song;
    }

    public Playlist createPlaylistOnLength(String mobile, String title, int length) throws Exception {
             User user = users.stream().filter(u -> u.getMobile().equals(mobile)).findFirst().orElse(null);
    if (user == null) {
        throw new Exception("User does not exist");
    }

    Playlist playlist = new Playlist(title);
    List<Song> songsByLength = songs.stream().filter(s -> s.getLength() == length).collect(Collectors.toList());
    playlistSongMap.put(playlist, songsByLength);
    playlistListenerMap.put(playlist, new ArrayList<>(List.of(user)));
    creatorPlaylistMap.put(user, playlist);
    userPlaylistMap.putIfAbsent(user, new ArrayList<>());
    userPlaylistMap.get(user).add(playlist);
    playlists.add(playlist);
    return playlist;
    }

    public Playlist createPlaylistOnName(String mobile, String title, List<String> songTitles) throws Exception {
    User user = users.stream().filter(u -> u.getMobile().equals(mobile)).findFirst().orElse(null);
    if (user == null) {
        throw new Exception("User does not exist");
    }

    Playlist playlist = new Playlist(title);
    List<Song> selectedSongs = songTitles.stream().map(songTitle -> songs.stream()
            .filter(s -> s.getTitle().equals(songTitle)).findFirst().orElse(null)).filter(Objects::nonNull)
            .collect(Collectors.toList());
    playlistSongMap.put(playlist, selectedSongs);
    playlistListenerMap.put(playlist, new ArrayList<>(List.of(user)));
    creatorPlaylistMap.put(user, playlist);
    userPlaylistMap.putIfAbsent(user, new ArrayList<>());
    userPlaylistMap.get(user).add(playlist);
    playlists.add(playlist);
    return playlist;
    }

    public Playlist findPlaylist(String mobile, String playlistTitle) throws Exception {
        User user = users.stream().filter(u -> u.getMobile().equals(mobile)).findFirst().orElse(null);
    if (user == null) {
        throw new Exception("User does not exist");
    }

    Playlist playlist = playlists.stream().filter(p -> p.getTitle().equals(playlistTitle)).findFirst().orElse(null);
    if (playlist == null) {
        throw new Exception("Playlist does not exist");
    }

    if (!playlistListenerMap.get(playlist).contains(user)) {
        playlistListenerMap.get(playlist).add(user);
    }

    return playlist;
    }

    public int likeSong(String mobile, String songTitle) throws Exception {
     User user = users.stream().filter(u -> u.getMobile().equals(mobile)).findFirst().orElse(null);
    if (user == null) {
        throw new Exception("User does not exist");
    }
    Song song = songs.stream().filter(s -> s.getTitle().equals(songTitle)).findFirst().orElse(null);
    if (song == null) {
        throw new Exception("Song does not exist");
    }
    songLikeMap.putIfAbsent(song, new ArrayList<>());
    if (!songLikeMap.get(song).contains(user)) {
        songLikeMap.get(song).add(user);
    }
    Artist artist = artists.stream().filter(a -> a.getName().equals(song.getArtist())).findFirst().orElse(null);
    if (artist != null) {
        artist.setLikes(artist.getLikes() + 1);
    }
    return 1;
}

   
    public String mostPopularArtist() {
       return artists.stream()
                .max(Comparator.comparingInt(a -> a.getLikes())) // Find the artist with the highest like count
                .map(Artist::getName)
                .orElse("No popular artist");
    }

 public String mostPopularSong() {
    return songs.stream()
                .max(Comparator.comparingInt(s -> songLikeMap.get(s).size()))  // Find the song with most likes
                .map(Song::getTitle)
                .orElse("No popular song");  
}


}
