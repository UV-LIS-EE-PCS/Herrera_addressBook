/**
 * Este paquete contiene clases relacionadas con las estructura de datos de direcciones y la gestión de entradas de libretas de direcciones.
 */
package address.data;


/**
 * Clase que representa una entrada en un libro de direcciones.
 * Implementa la interfaz Comparable para poder ordenar las entradas de manera lexicográfica.
*/
public class AddressEntry implements Comparable<AddressEntry>{
    
    /** El nombre de la persona. */
    private String firstName;
    /** El apellido de la persona. */
    private String lastName;
    /** La calle de la dirección. */
    private String street;
     /** La ciudad de la dirección. */
    private String city;
     /** El estado de la dirección. */
    private String state;
    /** El código postal de la dirección. */
    private String postalCode;
    /** El correo electrónico de la persona. */
    private String email;
    /** El número de teléfono de la persona. */
    private String phoneNumber;
    
    /**
     * Constructor vacío por defecto de AddressEntry, pero público.
     */
    public AddressEntry(){
    }
    
    /**
     * Constructor de AddressEntry que inicializa todos los campos.
     * @param firstName El nombre de la persona.
     * @param lastName El apellido de la persona.
     * @param street La calle de la dirección.
     * @param city La ciudad de la dirección.
     * @param state El estado de la dirección.
     * @param postalCode El código postal de la dirección.
     * @param email El correo electrónico de la persona.
     * @param phoneNumber El número de teléfono de la persona.
     */
    public AddressEntry(String firstName, String lastName, String street, String city, String state, String postalCode, String email, String phoneNumber){
        this.firstName = firstName;
        this.lastName = lastName;
        this.street = street;
        this.city = city;
        this.state = state;
        this.postalCode = postalCode;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    /**
     * Obtiene el nombre de la persona.
     * @return El nombre de la persona.
    */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Establece el nombre de la persona.
     * @param firstName El nombre de la persona.
    */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Obtiene el apellido de la persona.
     * @return El apellido de la persona.
    */
    public String getLastName() {
        return lastName;
    }

    /**
     * Establece el apellido de la persona.
     * @param lastName El apellido de la persona.
    */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Obtiene la calle de la dirección.
     * @return La calle de la dirección.
    */
    public String getStreet() {
        return street;
    }

    /**
     * Establece la calle de la dirección.
     * @param street La calle de la dirección.
    */
    public void setStreet(String street) {
        this.street = street;
    }

    /**
     * Obtiene la ciudad de la dirección.
     * @return La ciudad de la dirección.
    */
    public String getCity() {
        return city;
    }

    /**
     * Establece la ciudad de la dirección.
     * @param city La ciudad de la dirección.
    */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Obtiene el estado de la dirección.
     * @return El estado de la dirección.
    */
    public String getState() {
        return state;
    }

    /**
     * Establece el estado de la dirección.
     * @param state El estado de la dirección.
    */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * Obtiene el código postal de la dirección.
     * @return El código postal de la dirección.
    */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * Establece el código postal de la dirección.
     * @param postalCode El código postal de la dirección.
    */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * Obtiene el correo electrónico de la persona.
     * @return El correo electrónico de la persona.
    */
    public String getEmail() {
        return email;
    }

    /**
     * Establece el correo electrónico de la persona.
     * @param email El correo electrónico de la persona.
    */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Obtiene el número de teléfono de la persona.
     * @return El número de teléfono de la persona.
    */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Establece el número de teléfono de la persona.
     * @param phoneNumber El número de teléfono de la persona.
    */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Convierte la entrada de dirección a una cadena de texto.
     * @return Una representación de cadena de la entrada de dirección.
    */
    @Override
    public String toString()
    {
        return this.firstName + ' ' + this.lastName + ' ' + this.street + ' ' + this.city + ' ' + this.state + ' ' + this.postalCode 
        + ' ' + this.email  + ' ' + this.phoneNumber  ;
    }

    /**
     * Compara esta entrada de dirección con otra para determinar su orden según el contenido de {@link AddressEntry}.
     * @param other La otra entrada de dirección con la que comparar.
     * @return Un valor negativo si esta entrada lexicograficamente es menor, cero si son iguales, o un valor positivo si es mayor.
    */
   @Override
   public int compareTo(AddressEntry other){

        return this.toString().compareToIgnoreCase(other.toString());
   }
}
