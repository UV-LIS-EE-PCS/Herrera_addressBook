package address.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import org.junit.*;




public class AddressBookTest {

    
    private AddressBook addressBook ;
    AddressEntry firstEntry = new AddressEntry("Primera entrada", "Primera entrada única para pruebas", "Antigua", "Coatzacoalcos", "Veracruz", "96536", "primero@gmail.com", "9211111111" );
    AddressEntry secondEntry = new AddressEntry("Segunda entrada", "Segunda entrada única para pruebas", "Antigua", "Coatzacoalcos", "Veracruz", "96536", "segundo@gmail.com", "9212222222" );
    @Before
    public void  initialize(){
        addressBook = AddressBook.getInstance() ;
    }
   @Test
    public void testAddAddressFromFileWithValidFile() throws Exception {

        int sizeOfAddressEntryList = addressBook.AddressEntryList.size() ;
        addressBook.addAddressFromFile(); /* Seleccionar algún archivo */
        assertNotEquals(sizeOfAddressEntryList, addressBook.AddressEntryList.size());
        sizeOfAddressEntryList = addressBook.AddressEntryList.size() ;
        addressBook.addAddressFromFile(); /* Seleccionar el mismo archivo */
        assertEquals(sizeOfAddressEntryList, addressBook.AddressEntryList.size());
    }
    @Test
    public void testSingletonInstance(){
        AddressBook firstInstance = AddressBook.getInstance();
        AddressBook secondInstance = AddressBook.getInstance();

        assertEquals(firstInstance,secondInstance) ;
    }
    @Test
    public void testAddAddress(){
        int numberOfAddress = addressBook.AddressEntryList.size();
        addressBook.addAddress(firstEntry);
        ArrayList<Integer> firstResult = addressBook.searchAddress(firstEntry.getLastName());
        assertEquals(1, firstResult.size());
        assertEquals(addressBook.AddressEntryList.get(firstResult.get(0)).toString(), firstEntry.toString());

        addressBook.addAddress(secondEntry);
        ArrayList<Integer> secondResult = addressBook.searchAddress(secondEntry.getLastName());
        assertEquals(1, secondResult.size());
        assertEquals(addressBook.AddressEntryList.get(secondResult.get(0)).toString(), secondEntry.toString());

        assertEquals(numberOfAddress + 2, addressBook.AddressEntryList.size()); 
        // No considero viable una variable, justificando un "número mágico en este Test"
    }

    @Test
    public void testSearchAddress(){

        addressBook.addAddress(firstEntry);
        addressBook.addAddress(secondEntry);

        ArrayList<Integer> firstResult = addressBook.searchAddress(firstEntry.getLastName());
        assertFalse(firstResult.isEmpty());
        assertEquals(firstEntry.toString(), addressBook.AddressEntryList.get(firstResult.get(0)).toString());

        ArrayList<Integer> secondResult = addressBook.searchAddress(secondEntry.getLastName());
        assertFalse(secondResult.isEmpty());
        assertEquals(secondEntry.toString(), addressBook.AddressEntryList.get(secondResult.get(0)).toString());
    }

    @Test // Separación del test en dos posibilidades -> Elegir sí.
    public void testYesDeleteAddress() {

        addressBook.addAddress(firstEntry);

        String simulatedInput = "Y 0\n";
        InputStream originalIn = System.in;
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        ArrayList<Integer> resultsBeforeDeletion = addressBook.searchAddress(firstEntry.getLastName());
        assertFalse(resultsBeforeDeletion.isEmpty());

        addressBook.deleteAddress(firstEntry.getLastName());

        ArrayList<Integer> resultsAfterDeletion = addressBook.searchAddress(firstEntry.getLastName());
        assertTrue(resultsAfterDeletion.isEmpty());
        System.setIn(originalIn);
    }

    @Test // Separación del test en dos posibilidades -> elegir no.
    public void testNoDeleteAddress() {

        addressBook.addAddress(secondEntry);

        String simulatedInput = "N\n";
        InputStream originalIn = System.in;
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
        ArrayList<Integer> resultsBeforeDeletion = addressBook.searchAddress(secondEntry.getLastName());
        assertFalse(resultsBeforeDeletion.isEmpty());

        addressBook.deleteAddress(secondEntry.getLastName());
        
        ArrayList<Integer> resultsAfterDeletion = addressBook.searchAddress(secondEntry.getLastName());
        assertFalse(resultsAfterDeletion.isEmpty());
        System.setIn(originalIn);
    }

    @Test
    public void testShowAllAddress(){
        addressBook.addAddress(firstEntry);
        addressBook.addAddress(secondEntry);
        /* 
            Comentario: Esto lo manejo con los pocos conocimientos que tengo. manejar pruebas y salidas estándar me resulta  complejo y nuevo,
            se comprende la idea básica del código.
        */ 
        ByteArrayOutputStream outContent = new ByteArrayOutputStream(); 
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        addressBook.showAllAddress();

        System.setOut(originalOut);
        
        String expectedOutput =
                    "0\t" + 
                    firstEntry.getFirstName() + ' ' + firstEntry.getLastName() + "\n\t" +
                    firstEntry.getStreet() + ' ' + firstEntry.getCity() + ", " + firstEntry.getState() + ' ' + firstEntry.getPostalCode() + "\n\t" +
                    firstEntry.getEmail() + "\t" +
                    firstEntry.getPhoneNumber() + '\n' + (char)13 + '\n' + // <- El de showAllAdress retorna la secuencia de escape de retorno de carro, investigando concluí que esto se debe a Windows, por lo que estas pruebas son dependientes del S. No considero que sea una práctica recomendada.

                    "1\t" +
                    secondEntry.getFirstName() + ' ' + secondEntry.getLastName() + "\n\t" +
                    secondEntry.getStreet() + ' ' + secondEntry.getCity() + ", " + secondEntry.getState() + ' ' + secondEntry.getPostalCode() + "\n\t" +
                    secondEntry.getEmail() + "\t" +
                    secondEntry.getPhoneNumber() + '\n' + (char)13 +'\n' // <- El showAllAdress retorna la secuencia de escape de retorno de carro, investigando concluí que esto se debe a Windows, por lo que estas pruebas son dependientes del SO. No considero que sea una práctica recomendada.
        ;
        String output = outContent.toString();
        String cleanOutput = removeAnsiCodes(output);
    
        assertEquals(expectedOutput, cleanOutput);
    }

    @Test
    public void testIsValidAddressEntry(){
        AddressEntry notValidEntry = new AddressEntry("", "Perez", "Antigua", "", "Veracruz", "12345", "email@gmail.com", "9211111111");
        assertTrue(AddressBook.isValidAddressEntry(firstEntry));
        assertFalse(AddressBook.isValidAddressEntry(notValidEntry));
    }
    private String removeAnsiCodes(String input) { // 
        // Patrón para eliminar para regex.
        String ansiPattern = "\\u001B\\[[;\\d]*m";
        return input.replaceAll(ansiPattern, "");
    }
   
   
}
