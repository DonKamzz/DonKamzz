package za.ac.series;

/**
 * Data model class for TV Series.
 * According to the rubric, fields are kept public and named
 * exactly as required: SeriesId, SeriesName, SeriesAge, SeriesNumberOfEpisodes.
 */
public class SeriesModelSectionA {
    public String SeriesId;
    public String SeriesName;
    public String SeriesAge;
    public String SeriesNumberOfEpisodes;

    // Default constructor
    public SeriesModelSectionA() {}

    // Overloaded constructor
    public SeriesModelSectionA(String id, String name, String age, String episodes) {
        this.SeriesId = id;
        this.SeriesName = name;
        this.SeriesAge = age;
        this.SeriesNumberOfEpisodes = episodes;
    }
}
