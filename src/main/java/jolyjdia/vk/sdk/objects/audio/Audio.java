package jolyjdia.vk.sdk.objects.audio;

import org.jetbrains.annotations.NonNls;

import java.net.URL;

public class Audio {
    private String artist;
    private Integer id;
    private String title;
    private URL url;
    private Integer duration;
    private Integer date;
    private Integer album_id;
    private Integer genre_id;
    private String performer;

    public String getArtist() {
        return artist;
    }
    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public URL getUrl() {
        return url;
    }

    public Integer getDuration() {
        return duration;
    }

    public Integer getDate() {
        return date;
    }
    public Integer getAlbumId() {
        return album_id;
    }

    public Integer getGenreId() {
        return genre_id;
    }

    public String getPerformer() {
        return performer;
    }

    @Override
    public @NonNls String toString() {
        return "Audio{" +
                "artist='" + artist + '\'' +
                ", id=" + id +
                ", title='" + title + '\'' +
                ", url=" + url +
                ", duration=" + duration +
                ", date=" + date +
                ", album_id=" + album_id +
                ", genre_id=" + genre_id +
                ", performer='" + performer + '\'' +
                '}';
    }
}
