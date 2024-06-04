package address.data;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.Test;

public class AddressEntryTest {
    AddressEntry firstEntry = new AddressEntry("Primera entrada", "Primera entrada única para pruebas", "Antigua", "Coatzacoalcos", "Veracruz", "96536", "primero@gmail.com", "9211111111" );
    AddressEntry secondfirstEntry = new AddressEntry("Segunda entrada", "Segunda entrada única para pruebas", "Antigua", "Coatzacoalcos", "Veracruz", "96536", "segundo@gmail.com", "9212222222" );

    @Test
    public void testCompareToWithDifferentEntries() { // checar
        
        assertNotEquals(0, firstEntry.compareTo(secondfirstEntry));
        assertEquals(0, firstEntry.compareTo(firstEntry));
    }

    @Test
    public void testGetAndSetFirstName() {
        
        firstEntry.setFirstName("Samuel");

        assertEquals("Samuel", firstEntry.getFirstName());
    }

    @Test
    public void testGetAndSetLastName() {
        firstEntry.setLastName("Aguilera");
        assertEquals("Aguilera", firstEntry.getLastName());
    }

    @Test
    public void testGetAndSetStreet() {
        firstEntry.setStreet("Revolución");

        assertEquals("Revolución", firstEntry.getStreet());
    }

    @Test
    public void testGetAndSetCity() {
        firstEntry.setCity("Choapas");

        assertEquals("Choapas", firstEntry.getCity());
    }

    @Test
    public void testGetAndSetState() {
        firstEntry.setState("Chiapas");

        assertEquals("Chiapas", firstEntry.getState());
    }

    @Test
    public void testGetAndSetPostalCode() {
        firstEntry.setPostalCode("90000");

        assertEquals("90000", firstEntry.getPostalCode());
    }

    @Test
    public void testGetAndSetEmail() {
        firstEntry.setEmail("email@ejemplo.com");

        assertEquals("email@ejemplo.com", firstEntry.getEmail());
    }

    @Test
    public void testGetAndSetPhoneNumber() {
        firstEntry.setPhoneNumber("1234567890");

        assertEquals("1234567890", firstEntry.getPhoneNumber());
    }
}
