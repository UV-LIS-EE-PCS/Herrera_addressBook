package address;

import address.data.AddressBook;
import address.data.AddressEntry;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class MenuTest {

    /*
        - Algunas pruebas fueron omitidas o no fueron completadas por la complejidad que resulta hacerlas, en caso de hacerlas considero que serían pruebas a ciegas, ya que no
        entendería a completitud la complejidad detrás de este. 
        - Algunas pruebas estarán marcadas con comentarios, ya que se quedaron en solo intentos, pero por desconocimiento no encuentro la manera correcta de hacerlas.
        - Algunas pruebas fueron omitidas por el determinismo de estas sin importar la entrada.
        - Considerar que solo son llamadas, nos interesa que llamaron a los métodos, esos métodos llamados ya fueron testeados en otras secciones.
    */
    private Menu menu;
    private AddressBook addressBookMock;
    public static final char OPTION_INITIAL = 'a' ;
    
    @BeforeEach
    void setUp() {
        addressBookMock = Mockito.mock(AddressBook.class);
        menu = new Menu();
        menu.addressBook = addressBookMock;
    }

    @Test
    void testIsValidOptionValid() {
        for (int indexOfOptions = OPTION_INITIAL ; indexOfOptions < OPTION_INITIAL + menu.optionsList.size() ; indexOfOptions++) {
            assertTrue(menu.isValidOption((char) indexOfOptions));
        }
        
    }
    @Test
    void testIsValidOptionInvalid() {
        for (int indexOfOptions = OPTION_INITIAL + menu.optionsList.size() ; indexOfOptions < OPTION_INITIAL + menu.optionsList.size() * 2; indexOfOptions++) {
        assertFalse(menu.isValidOption((char) indexOfOptions));
        }
    }

  

    @Test
    void testReadAddressEntry() {
        String input = "Manuel\nAguilar\nAntigua\nCoatzacoalcos\nVeracruz\n96536\ncorreo@ejemplo.com\n9211111111\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        AddressEntry addressEntry = menu.readAddressEntry();

        assertTrue(addressEntry.getFirstName().equals("Manuel"));
        assertTrue(addressEntry.getLastName().equals("Aguilar"));
        assertTrue(addressEntry.getStreet().equals("Antigua"));
        assertTrue(addressEntry.getCity().equals("Coatzacoalcos"));
        assertTrue(addressEntry.getState().equals("Veracruz"));
        assertTrue(addressEntry.getPostalCode().equals("96536"));
        assertTrue(addressEntry.getEmail().equals("correo@ejemplo.com"));
        assertTrue(addressEntry.getPhoneNumber().equals("9211111111"));
    }
    @Test
     void testAddAddress() {
        String input = "Manuel\nAguilar\nAntigua\nCoatzacoalcos\nVeracruz\n96536\ncorreo@ejemplo.com\n9211111111\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        menu.addAddress();
        verify(addressBookMock, times(1)).addAddress(any(AddressEntry.class));
        
    }

    @Test
    void testShowAllAddress() {
        menu.showAllAddress();
        verify(addressBookMock, times(1)).showAllAddress();
    }
    
    //@Test Se queda en espera. Consideré que se queda en espera de otro texto adicional, sin embargo no funcionó.
    void testDeleteAddress() {
        String input = "Herrera\nY 0";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        menu.deleteAddress();
        
        
    }
    //@Test Se queda en espera. Consideré que se queda en espera de otro texto adicional, sin embargo no funcionó.
    void testSearchAddress() {
        String input = "Herrera\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
    
        menu.searchAddress();
        verify(addressBookMock, times(1)).searchAddress("Herrera");
    }


}
