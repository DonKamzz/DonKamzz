package za.ac.campus;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit tests to prove CampusInventory works as intended.
 */
public class CampusInventoryTest {

    @Test
    public void testAddAndFind() {
        CampusInventory inv = new CampusInventory(5);
        Room r = new Room("R1", 20, true);
        Lab l = new Lab("L1", 30, false, 15);
        assertTrue(inv.add(r));
        assertTrue(inv.add(l));
        assertEquals(2, inv.size());
        assertNotNull(inv.findById("R1"));
        assertNotNull(inv.findById("L1"));
    }

    @Test
    public void testUpdateCapacity() {
        CampusInventory inv = new CampusInventory(5);
        inv.add(new Room("R2", 10, false));
        assertTrue(inv.updateCapacity("R2", 25));
        assertEquals(25, inv.findById("R2").getCapacity());
    }

    @Test
    public void testDelete() {
        CampusInventory inv = new CampusInventory(5);
        inv.add(new Room("R3", 15, true));
        assertTrue(inv.delete("R3"));
        assertNull(inv.findById("R3"));
    }

    @Test
    public void testReportTotals() {
        CampusInventory inv = new CampusInventory(5);
        inv.add(new Room("R4", 20, true));
        inv.add(new Lab("L2", 25, false, 10));
        String rep = inv.report();
        assertTrue(rep.contains("Rooms=1"));
        assertTrue(rep.contains("Labs=1"));
        assertTrue(rep.contains("Total Seats=45"));
        assertTrue(rep.contains("Total PCs=10"));
    }
}
