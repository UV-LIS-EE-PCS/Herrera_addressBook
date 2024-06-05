package tools;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.Test;

import address.data.AddressEntry;

public class LoadAddressFileTest {
    final static String PATH_ADDRESS_BOOK_TEST = "address_book\\src\\test\\java\\resources\\testAddressBook.txt";
    /*
        Algunas pruebas fueron omitidas por la complejidad de las rutas y la elección de archivos.
     */
    @Test
    public void testLoadFileFromExplorer() {
       
        String selectedFile = LoadAddressFile.loadFileFromExplorer();

        assertTrue(selectedFile.contains(PATH_ADDRESS_BOOK_TEST)); // <- Este es importante usar el de la misma ruta, el contains es para considerar lo relativo.
    }

    @Test
    public void testFillAddressEntry() {
        AddressEntry entry = new AddressEntry();
        LoadAddressFile.fillAddressEntry(entry, "Adrián");
        LoadAddressFile.fillAddressEntry(entry, "Herrera");
        LoadAddressFile.fillAddressEntry(entry, "Antigua");
        LoadAddressFile.fillAddressEntry(entry, "Coatzacoalcos");
        LoadAddressFile.fillAddressEntry(entry, "Veracruz");
        LoadAddressFile.fillAddressEntry(entry, "96536");
        LoadAddressFile.fillAddressEntry(entry, "adrian@gmail.com");
        LoadAddressFile.fillAddressEntry(entry, "9211111111");

        assertEquals("Adrián", entry.getFirstName());
        assertEquals("Herrera", entry.getLastName());
        assertEquals("Antigua", entry.getStreet());
        assertEquals("Coatzacoalcos", entry.getCity());
        assertEquals("Veracruz", entry.getState());
        assertEquals("96536", entry.getPostalCode());
        assertEquals("adrian@gmail.com", entry.getEmail());
        assertEquals("9211111111", entry.getPhoneNumber());
    }

    @Test
    public void testIsAddressComplete() {
    
        AddressEntry completeAddressEntry = new AddressEntry("Adrián", "Herrera Jeronimo", "Antigua", "Coatzacoalcos", "Veracruz", "96535", "adrian@gmail.com", "9211111111");
        assertTrue(LoadAddressFile.isAddressComplete(completeAddressEntry));

    
        AddressEntry firstIncompleteAddressEntry = new AddressEntry("", "Herrera Jeronimo", "Antigua", "Coatzacoalcos", "Veracruz", "96535", "adrian@gmail.com", "9211111111");
        assertFalse(LoadAddressFile.isAddressComplete(firstIncompleteAddressEntry));

    
        AddressEntry secondIncompleteAddressEntry = new AddressEntry("", "", "", "", "", "", "", "");
        assertFalse(LoadAddressFile.isAddressComplete(secondIncompleteAddressEntry));

    
        AddressEntry nullAddressEntry = new AddressEntry();
        assertFalse(LoadAddressFile.isAddressComplete(nullAddressEntry));
    }

  
}
