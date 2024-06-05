package tools;

/**
 * Clase que representa una opción en un menú, se compone de su nombre descriptivo y el método a ejecutar.
*/
public class Option {
    /*
     * Atributo que representa un método asociado a la opción.
    */
    private final Runnable callback;
    /*
     * Descripción general del método asociado.
    */
    private final String label;

    /**
     * Constructor de la clase Option.
     * @param label Etiqueta que describe la opción.
     * @param function Función que se ejecutará cuando se seleccione la opción.
    */
    public Option(String label, Runnable function){
        this.callback = function;
        this.label = label;
    }

    /**
     * Método que ejecuta la función asociada a la opción.
    */
    public void execute(){
        callback.run();
    }

    /**
     * Método que devuelve la etiqueta de la opción.
     * @return La etiqueta de la opción.
    */
    @Override
    public String toString(){
        return label;
    }
}
