package tools;

import java.util.ArrayList;
import java.util.Collections;

import address.data.AddressEntry;

/**
 * Clase que permite el uso de métodos estáticos para el mantenimiento de ArrayList ordenados.
*/
public class SortedArrayListAddress {

    /**
     * Constructor privado para evitar la creación de instancias de esta clase. 
    */
    private SortedArrayListAddress() {
        // Este constructor está vacío para evitar que se cree una instancia, no se hace uso.
    }
    /**
     * Agrega una nueva entrada de dirección a la lista de direcciones de manera ordenada lexicograficamente según el indice dado.
     * @param addressListEntry La lista de direcciones donde se agregará la nueva entrada.
     * @param newAddressEntry La nueva entrada de dirección que se agregará.
    */
    public static void addOrder(ArrayList<AddressEntry> addressListEntry, AddressEntry newAddressEntry)
    {
        int index = Collections.binarySearch(addressListEntry, newAddressEntry) ;
        if(index < 0) index = -index - 1;
        addressListEntry.add(index, newAddressEntry);
    }
    
}
