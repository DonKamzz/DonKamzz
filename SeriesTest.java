package za.ac.series;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Series application.
 * Each test follows the rubric: search, update, delete, and age validation.
 */
public class SeriesTest {

    // Helper method to pre-load sample data for testing
    private Series seeded() {
        Series s = new Series();
        s.addSeries(new SeriesModel("S1", "Space Show", "13", "10"));
        s.addSeries(new SeriesModel("S2", "City Tales", "16", "8"));
        return s;
    }

    @Test
    public void TestSearchSeries() {
        // Should find series with ID "S1"
        Series s = seeded();
        SeriesModel m = s.findById("S1");
        assertNotNull(m);  // Must not be null
        assertEquals("Space Show", m.SeriesName); // Correct series name
    }

    @Test
    public void TestSearchSeries_SeriesNotFound() {
        // Should return null for an invalid ID
        Series s = seeded();
        assertNull(s.findById("S404"));
    }

    @Test
    public void TestUpdateSeries() {
        // Update series and check if changes saved
        Series s = seeded();
        boolean ok = s.updateById("S2", "City Tales 2", "18", "12");
        assertTrue(ok); // update must succeed
        SeriesModel m = s.findById("S2");
        assertEquals("City Tales 2", m.SeriesName);
        assertEquals("18", m.SeriesAge);
        assertEquals("12", m.SeriesNumberOfEpisodes);
    }

    @Test
    public void TestDeleteSeries() {
        // Delete existing series and confirm removal
        Series s = seeded();
        boolean removed = s.deleteById("S1");
        assertTrue(removed);
        assertNull(s.findById("S1")); // Should no longer exist
    }

    @Test
    public void TestDeleteSeries_SeriesNotFound() {
        // Deleting non-existent ID should return false
        Series s = seeded();
        boolean removed = s.deleteById("NOPE");
        assertFalse(removed);
    }

    @Test
    public void TestSeriesAgeRestriction_AgeValid() {
        // Check valid age boundaries
        Series s = new Series();
        assertTrue(s.isValidAge("2"));  // minimum
        assertTrue(s.isValidAge("18")); // maximum
        assertTrue(s.isValidAge("13")); // within range
    }

    @Test
    public void TestSeriesAgeRestriction_SeriesAgeInValid() {
        // Check invalid ages and bad input
        Series s = new Series();
        assertFalse(s.isValidAge("1"));   // too low
        assertFalse(s.isValidAge("19"));  // too high
        assertFalse(s.isValidAge("abc")); // not a number
        assertFalse(s.isValidAge(""));    // empty
        assertFalse(s.isValidAge(null));  // null
    }
}
