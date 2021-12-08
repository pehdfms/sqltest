package Models;

public class Session {
    private String startDate;
    private Movie playing;
    private Long id;

    public Session() {
    }

    public Session(String startDate, Movie playing, Long id) {
        this.startDate = startDate;
        this.playing = playing;
        this.id = id;
    }

    @Override
    public String toString() {
        return "Session{" +
                "startDate='" + startDate + '\'' +
                ", playing=" + playing +
                ", id=" + id +
                '}';
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public Movie getPlaying() {
        return playing;
    }

    public void setPlaying(Movie playing) {
        this.playing = playing;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
