package address.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

import tools.Colors;
import tools.LoadAddressFile;
import tools.SortedArrayListAddress;
 
/**
 * La clase AddressBook representa un libro de direcciones que contiene una lista de entradas de dirección.
 * Permite agregar, eliminar, buscar y mostrar entradas de dirección, así como cargar y guardar datos desde un archivo.
*/
public class AddressBook{

    /** Lista que contiene todas las entradas de dirección en el libro de direcciones. */
    public ArrayList<AddressEntry> AddressEntryList = new ArrayList<>() ;
    /** Instancia que me permite mantener un único libro de direcciones. */
    private static AddressBook instance = null;
    
    /**
     * Constructor privado para evitar la creación de instancias directas de AddressBook.
     * Carga las direcciones desde el archivo predeterminado al inicializar el libro de direcciones.
    */
    private AddressBook(){
        LoadAddressFile.importAddressesFromDefaultFile(AddressEntryList);
    }
    
     /**
     * Obtiene la instancia única del libro de direcciones, con el fin de cumplir el patrón de diseño Singleton.
     * @return La instancia única del libro de direcciones.
    */
    public static synchronized AddressBook getInstance(){
        if(instance == null)
            instance = new AddressBook() ;
        return instance ;
    }
    
    /**
     * Agrega direcciones almecenandolo en {@link AddressEntryList} desde un archivo seleccionado por el usuario desde una ventana desplegada del explorador de archivos.
     * Muestra un mensaje de error si el archivo no es un txt file.
    */
    public void addAddressFromFile(){
        
        String  filePath = LoadAddressFile.selectFileFromDialog() ; 
        
        if(filePath != null){
            try {
                File importFile = new File(filePath) ;
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                        new FileInputStream(importFile), StandardCharsets.UTF_8))) {
                    if(filePath.endsWith(".txt")){
                        AddressEntry addressEntryFile = new AddressEntry();
                        String line ;
                        int numberOfLines = 0;
                        while((line = reader.readLine()) != null){
                            numberOfLines ++;
                            LoadAddressFile.fillAddressEntry(addressEntryFile, line);

                            if(LoadAddressFile.isAddressComplete(addressEntryFile)){         
                                if(isValidAddressEntry(addressEntryFile)){
                                    if(Collections.binarySearch(AddressEntryList, addressEntryFile) < 0){
                                        SortedArrayListAddress.addOrder(AddressEntryList, addressEntryFile); 
                                        LoadAddressFile.updateFileWithNewEntry(addressEntryFile) ;
                                    }
                                    else 
                                        System.out.println( Colors.ANSI_YELLOW + "¡Este contacto ya existe! " + Colors.ANSI_RESET +addressEntryFile.toString());
                                }
                                else 
                                    System.err.println(Colors.CRITICAL_ERROR + "[Parámetro inválido]" + Colors.ANSI_RESET + " Algún campo no cumple con el formato adecuado. " + addressEntryFile.toString());   
                                addressEntryFile = new AddressEntry();
                            }
                        }
                        if(numberOfLines % 8 != 0)
                            System.err.println(Colors.CRITICAL_ERROR + "[Entrada inmpletada]" + Colors.ANSI_RESET + " Uno o varios campos no fueron definidos " );   
                    }
                    else{
                        System.err.println(Colors.CRITICAL_ERROR + "[Parámetro inválido]" + Colors.ANSI_RESET + " No se seleccionó un documento de texto. " );   
                        return;
                    }
                    System.out.println(Colors.ANSI_GREEN + "[Archivo procesado correctamente]" + Colors.ANSI_RESET + "\n" + filePath);
                } 
            } 
            catch (Exception e) {
                System.err.println(Colors.CRITICAL_ERROR + "[Archivo no encontrado]" +  Colors.ANSI_RESET + " " + e.getMessage() );
            }
        }
        else
            System.out.println(Colors.CRITICAL_ERROR + "[Operación cancelada]" +  Colors.ANSI_RESET + " No se seleccionó un archivo.");
    }
   
    /**
     * Agrega una nueva entrada de dirección al libro de direcciones. 
     * Este método llama al método para la escritura de direcciones en el archivo de texto designado.
     * @param addressEntry La entrada de dirección a agregar a la lista {@link AddressEntryList}.
    */
    public void addAddress(AddressEntry addressEntry){   
        
        
        if(isValidAddressEntry(addressEntry) == true){
            if(Collections.binarySearch(AddressEntryList, addressEntry) < 0){
                SortedArrayListAddress.addOrder(AddressEntryList, addressEntry);
                LoadAddressFile.saveAddressToDefaultFile(addressEntry);
            }
            else 
                System.out.println( Colors.ANSI_YELLOW + "¡Este contacto ya existe! " + Colors.ANSI_RESET +addressEntry.toString());
        }       
        else
            System.out.println(Colors.CRITICAL_ERROR + "[Parámetro inválido]" + Colors.ANSI_RESET + " Algún campo no cumple con el formato adecuado.");
    }

    /**
     * Muestra en consola todas las entradas de dirección almacenadas en {@link AddressEntryList}el libro de direcciones con todos los campos.
    */
    public void showAllAddress(){
        
        if(AddressEntryList.size() > 0){
            int index = 0;
            for(AddressEntry addressEntry : AddressEntryList){
                System.out.println(
                    Colors.ANSI_BOLD + Colors.ANSI_PURPLE + index + "\t" + Colors.ANSI_RESET + Colors.ANSI_WHITE + 
                    addressEntry.getFirstName() + ' ' + addressEntry.getLastName() + "\n\t" +
                    addressEntry.getStreet() + ' ' + addressEntry.getCity() + ", " + addressEntry.getState() + ' ' + addressEntry.getPostalCode() + "\n\t" +
                    addressEntry.getEmail() + "\t" +
                    addressEntry.getPhoneNumber() + '\n' + Colors.ANSI_RESET
                );
                index ++;
            }
        }
        else 
            System.out.println(Colors.CRITICAL_ERROR + "[No se encontraron resultados]" + Colors.ANSI_RESET +  " No hay contactos que mostrar.");
    }

    /**
     * Busca las entradas de dirección guardadas en {@link AddressEntryList} que contienen una cadena de búsqueda basada en los apellidos.
     * @param searchString El apellido ocupado como cadena de búsqueda.
     * @return Una lista de índices de las entradas de dirección encontradas que cumplen el estándar de búsqueda.
    */
    public ArrayList<Integer> searchAddress(String searchString){
    
        ArrayList<Integer> addressFound = new ArrayList<>() ;
        int index = 0;
        boolean isThereOneFound = false ;
        for(AddressEntry addressEntry : AddressEntryList){
            if(addressEntry.getLastName().contains(searchString)){
                System.out.println(
                    Colors.ANSI_BOLD + Colors.ANSI_PURPLE + index + "\t" + Colors.ANSI_RESET + Colors.ANSI_WHITE + 
                    addressEntry.getFirstName() + ' ' + addressEntry.getLastName() + "\n\t" +
                    addressEntry.getStreet() + ' ' + addressEntry.getCity() + ", " + addressEntry.getState() + ' ' + addressEntry.getPostalCode() + "\n\t" +
                    addressEntry.getEmail() + "\t" +
                    addressEntry.getPhoneNumber() + '\n' + Colors.ANSI_RESET
                );
                addressFound.add((Integer)index) ;
                isThereOneFound = true;
            } 
            index ++;
        }
        if(isThereOneFound == false)
            System.out.println(Colors.CRITICAL_ERROR + "[No se encontraron resultados]" + Colors.ANSI_RESET +  " No hay contactos que mostrar.");
        return addressFound ;
    }

    /**
     * Elimina las entradas de dirección guardadas en {@link AddressEntryList} que contienen una cadena de búsqueda basada en los apellidos y que fueron seleccionadas por el usuario.
     * Permite al usuario seleccionar las entradas a eliminar, ofrece la opción de eliminar todas o cancelar la operación.
     * @param deletedString La cadena de búsqueda para identificar las entradas a eliminar.
    */
    public void deleteAddress(String deletedString){

       
        ArrayList<Integer>addressFound = searchAddress(deletedString) ;
        if(addressFound.size() >= 1){
            System.out.println("Ingresar " + Colors.ANSI_BOLD + Colors.ANSI_GREEN + 'Y' + Colors.ANSI_RESET + " eliminará todos los registros.");
            System.out.println("Ingresar " + Colors.ANSI_BOLD + Colors.ANSI_GREEN + "Y [número] [número_2]" + Colors.ANSI_RESET + " eliminará los registros seleccionados.");
            System.out.println("Ingresar " + Colors.ANSI_BOLD + Colors.ANSI_RED +  'N' + Colors.ANSI_RESET+ " no eliminará ningún contacto.");

            ArrayList<String> optionAnswered = new ArrayList<>();
            Scanner input = new Scanner(System.in, StandardCharsets.UTF_8) ;
            while (true) {
                System.out.print(Colors.ANSI_YELLOW + "Ingresa una opción válida: " + Colors.ANSI_RESET);
                Collections.addAll(optionAnswered, input.nextLine().split(" "));
                switch (optionAnswered.get(0)){
                    case "Y":
                        if(optionAnswered.size() > 1){
                            optionAnswered.remove(0);
                            Collections.sort(optionAnswered, Comparator.reverseOrder());
                            
                                try {
                                    for(String indexAddress : optionAnswered){
                                        if(Integer.parseInt(indexAddress) <= AddressEntryList.size() - 1){                       
                                        
                                            LoadAddressFile.updateFileAfterDelete(AddressEntryList.get(Integer.parseInt(indexAddress)));
                                            AddressEntryList.remove(Integer.parseInt(indexAddress));

                                        }
                                        else 
                                        System.err.println(Colors.CRITICAL_ERROR + "[Parámetro inválido]" + Colors.ANSI_RESET + " " +  indexAddress + " ¡No pertenece a ningún registro!");   
                                    }
                                    return;
                                    
                                } catch (Exception e) {
                                    System.err.println(Colors.CRITICAL_ERROR + "[Parámetro inválido]" + Colors.ANSI_RESET  + " ¡Ingresa una pción válida!");   
                                }
                            
                        }
                        else if(optionAnswered.size() == 1){                           
                            Collections.sort(addressFound, Comparator.reverseOrder());
                            try {
                                for(Integer indexAddress : addressFound){
                                    LoadAddressFile.updateFileAfterDelete(AddressEntryList.get((int)(indexAddress))); 
                                    AddressEntryList.remove((int)indexAddress);   
                                }
                                return;
                            } catch (Exception e) {
                                System.err.println(Colors.CRITICAL_ERROR + "[Parámetro inválido]" + Colors.ANSI_RESET  + " ¡Ingresa una pción válida!");   
                            }
                        }
                        break;
                    case "N":{
                        System.out.println(Colors.CRITICAL_ERROR + "[Operación cancelada]" + Colors.ANSI_RESET + " Se seleccionó cancelar la operación");
                        return;
                    }
                    default:
                        System.out.println(Colors.CRITICAL_ERROR + "[Operación inválida]" + Colors.ANSI_RESET + " Se seleccionó una opción invalida."); /*  */
                    }
                }
        }
        else
            System.out.println(Colors.CRITICAL_ERROR + "[No se encontraron resultados]" + Colors.ANSI_RESET +  " No hay contactos que mostrar.");
    }

    /**
     * Verifica si una entrada de dirección es válida.
     * Una entrada de dirección se considera válida si todos sus campos tienen al menos un carácter.
     * @param addressEntry La entrada de dirección a verificar.
     * @return true si la entrada de dirección es válida, false de lo contrario.
    */
    public static boolean isValidAddressEntry(AddressEntry addressEntry){
        if(addressEntry.getFirstName().length() < 1 || 
            addressEntry.getLastName().length() < 1 || 
            addressEntry.getStreet().length() < 1 || 
            addressEntry.getCity().length() < 1 || 
            addressEntry.getState().length() < 1 || 
            addressEntry.getPostalCode().length() < 1 ||
            addressEntry.getEmail().length() < 1 || 
            addressEntry.getPhoneNumber().length() < 1
        )
            return false;

        return true;
    }

   
}
