package address;

/**
 * Clase principal que inicia la aplicación de libro de direcciones.
*/
public class AddressBookApplication {
    
    /**
     * Método principal que inicia la aplicación.
     * @param args Los argumentos de la línea de comandos (no se utilizan actualmente)
    */
    /* 
     * Constructor privado para evitar la creación de instancias de esta clase. 
    */
    private AddressBookApplication() {
        // Este constructor está vacío para evitar que se cree una instancia, no se hace uso.
    }
    /**
     * Método principal que inicia la aplicación de libro de direcciones.
     *
     * @param args Los argumentos de la línea de comandos (no se utilizan en este caso).
    */
    public static void main(String[] args) {
     
        Menu menu = new Menu();
        menu.displayMenu();

    }
}
