/**
 * * Este paquete contiene clases relacionadas con las ejecución y la gestión de libretas de direcciones, este es con lo que interactúa el usuario directamente.
 */
package address;
import java.util.Scanner;

import address.data.AddressBook;
import address.data.AddressEntry;
import tools.Colors;
import tools.Option;
import tools.OptionsList;

/**
 * Clase que representa el menú de la aplicación de gestión de direcciones, esto es con lo que interactúa el usuario.
*/
public class Menu {
    
    /** Instancia del libro de direcciones. */
    public AddressBook addressBook;
  
    /** Lista de opciones disponibles en el menú. */
    final OptionsList optionsList;
  
    /** Escáner para leer la entrada del usuario. */
    public Scanner input ;
  
    /**
     * Constructor de la clase Menu.
     * Ejecuta el método de inicialización las opciones disponibles para el menú
     * Solicita una instancia única para el uso del libro de direcciones.
     * Solicita una instancia de Scanner, considerando el estándar UTF-8
    */
    public Menu(){
        optionsList =  createOptionsList() ;
        addressBook = AddressBook.getInstance();
        input = new Scanner(System.in, "UTF-8") ;
    }

    /**
     * Crea la lista de opciones del menú.
     * La lista consiste en múltiples opciones las cuales contiene un texto y la función de ejecución.
     * @return La lista de opciones del menú.
    */
    private OptionsList createOptionsList(){
        return new OptionsList(
            new Option("Cargar de archivo", this::loadFile),
            new Option("Agregar", this::addAddress),
            new Option("Eliminar", this::deleteAddress),
            new Option("Buscar", this::searchAddress),
            new Option("Mostrar", this::showAllAddress),
            new Option("Salir", this::exitMenu)
        );
    }
           
    /**
     * Muestra el menú con el carácter necesario para ingresar una opción, con la espera de la entrada del usuario.
    */
    void displayMenu()
    {
        while(true)
        {
            optionsList.displayOptions();
            System.out.print(Colors.ANSI_YELLOW + "Ingrese una opción: " + Colors.ANSI_RESET);
            char optionEntered = input.next().charAt(0);
            input.nextLine();
            if(isValidOption(optionEntered))
                optionsList.executeOption(optionEntered - OptionsList.OPTION_INITIAL);
            else
                System.out.println(Colors.CRITICAL_ERROR + "[Entrada inválida] ¡Ingrese una opción disponible!" + Colors.ANSI_RESET);
        }
    }
    /**
     * Verifica si la opción ingresada por el usuario es válida.
     * Es válida si se encuentra entre el dominio de las opciones disponibles.
     * @param option La opción ingresada por el usuario.
     * @return true si la opción es válida, false en caso contrario.
    */
    boolean isValidOption(char option) {
        return option >= OptionsList.OPTION_INITIAL && option < OptionsList.OPTION_INITIAL + optionsList.size();
    }
  
    /**
     * Método para solicitar la cargar direcciones desde un archivo.
    */
    void loadFile(){
        System.out.println(Colors.ANSI_YELLOW + "Ingresa en la ventana desplegada y selecciona el archivo." + Colors.ANSI_RESET);
        addressBook.addAddressFromFile() ;
    }
    /**
     * Método para solicitar la adición de una nueva dirección.
     * Este método permite la lectura de AddressEntry y guardar dicho registro.
    */
    void addAddress(){
        System.out.println(Colors.ANSI_YELLOW + "Ingresa los datos del contacto." + Colors.ANSI_RESET);
        AddressEntry addressEntry = readAddressEntry() ;
        addressBook.addAddress(addressEntry);
    }
    /**
     * Método para eliminar una dirección.
     * Solicita el apellido de búsqueda para la eliminación.
    */
    void deleteAddress(){
        System.out.println(Colors.ANSI_YELLOW + "Ingrese el apellido del contacto que desea " + Colors.CRITICAL_ERROR+ "eliminar." + Colors.ANSI_RESET);
        String deletedString = input.nextLine() ; 
        addressBook.deleteAddress(deletedString);
    }

    /**
     * Método para la búsqueda de una dirección.
     * Solicita el apellido de búsqueda para la búsqueda.
    */
    void searchAddress(){
        System.out.println(Colors.ANSI_YELLOW + "Ingrese el apellido del contacto que desea" + Colors.ANSI_BLUE + " buscar." + Colors.ANSI_RESET);
        String searchString = input.nextLine() ; 
        addressBook.searchAddress(searchString);
    }

    /**
     * Método que solicita  mostrar todas las direcciones.
     */
    void showAllAddress(){
        System.out.println(Colors.ANSI_YELLOW + "Direcciones guardadas:" + Colors.ANSI_RESET);
        addressBook.showAllAddress() ;
    }
    /**
     * Método para salir del menú, permite la finalización del programa.
    */
    void exitMenu(){
        System.exit(0);
    }
    
    /**
     * Lee la entrada del usuario esperando cada parametro de {@link AddressEntry} y crea una nueva entrada de dirección.
     * @return La nueva dirección creada mediante la entrada del usuario.
    */
    public AddressEntry readAddressEntry(){
        input = new Scanner(System.in, "UTF-8") ;
        System.out.print(Colors.ANSI_BLUE + "Nombre: " + Colors.ANSI_RESET) ; String firstName = input.nextLine() ;
        System.out.print(Colors.ANSI_BLUE + "Apellidos: " + Colors.ANSI_RESET) ; String lastName = input.nextLine() ;
        System.out.print(Colors.ANSI_BLUE + "Calle: " + Colors.ANSI_RESET) ; String street = input.nextLine() ;
        System.out.print(Colors.ANSI_BLUE + "Ciudad: " + Colors.ANSI_RESET) ; String city = input.nextLine();
        System.out.print(Colors.ANSI_BLUE + "Estado: " + Colors.ANSI_RESET) ; String state = input.nextLine() ;
        System.out.print(Colors.ANSI_BLUE + "Código postal: " + Colors.ANSI_RESET) ; String postalCode = input.nextLine();
        System.out.print(Colors.ANSI_BLUE + "Correo electrónico: " + Colors.ANSI_RESET) ; String email = input.nextLine() ;
        System.out.print(Colors.ANSI_BLUE + "Número telefónico: " + Colors.ANSI_RESET) ; String phoneNumber = input.nextLine() ;

        AddressEntry newAddressEntry = new AddressEntry(firstName, lastName, street, city, state, postalCode, email, phoneNumber) ;
        return newAddressEntry;
    }

  
}
