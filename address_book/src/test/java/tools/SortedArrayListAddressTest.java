package tools;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

import address.data.AddressEntry;

public class SortedArrayListAddressTest {

    ArrayList<AddressEntry> addressList = new ArrayList<>();
    AddressEntry firstEntry = new AddressEntry("Adrián", "Herrera","Antigua", "Coatzacoalcos", "Veracruz","96535", "ejemplo@gmail.com","9211111111"); 
    AddressEntry secondEntry = new AddressEntry("Pedro", "Herrera","Antigua", "Coatzacoalcos", "Veracruz","96535", "ejemplo@gmail.com","9211111111");
    @Test
    public void testAddOrderEmptyList() {
        
        SortedArrayListAddress.addOrder(addressList, firstEntry);
        assertEquals(1, addressList.size());
        assertEquals(firstEntry, addressList.get(0));
    }

    @Test
    public void testAddOrderSingleEntry() {
        addressList.add(firstEntry);
        SortedArrayListAddress.addOrder(addressList, secondEntry);
        assertEquals(2, addressList.size());
        assertEquals(firstEntry, addressList.get(0)); // Adrián va antes que Pedro, esto lexicograficamente
        assertEquals(secondEntry, addressList.get(1));
    }


    @Test
    public void testAddOrderDuplicateEntry() {
        addressList.add(firstEntry);
        secondEntry = firstEntry;
        SortedArrayListAddress.addOrder(addressList, secondEntry);
        assertEquals(2, addressList.size());
        assertEquals(firstEntry, addressList.get(0));
        assertEquals(secondEntry, addressList.get(1));
    }
}
