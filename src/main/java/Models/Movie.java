package Models;

public class Movie {
    private String name;
    private String category;
    private String duration;
    private Long id;

    public Movie() {
    }

    public Movie(String name, String category, String duration, Long id) {
        this.name = name;
        this.category = category;
        this.duration = duration;
        this.id = id;
    }

    @Override
    public String toString() {
        return "ID " + id +
                " - " + name +
                " (" + category + "): " +
                duration;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
