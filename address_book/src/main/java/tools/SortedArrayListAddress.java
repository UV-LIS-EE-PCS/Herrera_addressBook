package tools;

import java.util.ArrayList;
import java.util.Collections;

import address.data.AddressEntry;

public class SortedArrayListAddress {

    public static void addOrder(ArrayList<AddressEntry> addressListEntry, AddressEntry newAddressEntry)
    {
        int index = Collections.binarySearch(addressListEntry, newAddressEntry) ;
        if(index < 0) index = -index - 1;
        addressListEntry.add(index, newAddressEntry);
    }
    
}
