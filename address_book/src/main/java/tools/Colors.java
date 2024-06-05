/**
 * Este paquete contiene clases que proporcionan herramientas y utilidades para diversas funcionalidades dentro de la aplicación.
 */
package tools;

/**
 * Clase que proporciona códigos de colores ANSI para dar formato al texto en la consola.
 */
public class Colors {

    /* 
     * Constructor privado para evitar la creación de instancias de esta clase. 
    */
    private Colors() {
        // Este constructor está vacío para evitar que se cree una instancia, no se hace uso.
    }
    
    /**
     * Código ANSI para texto en negrita.
    */
    public static final String ANSI_BOLD = "\u001B[1m";

    /**
     * Código ANSI para restablecer el formato del texto.
    */
    public static final String ANSI_RESET = "\u001B[0m";

    /**
     * Código ANSI para texto amarillo.
    */
    public static final String ANSI_YELLOW = "\u001B[38;2;245;213;99m";

    /**
     * Código ANSI para texto verde.
    */
    public static final String ANSI_GREEN = "\u001B[38;2;111;178;9m";

    /**
     * Código ANSI para texto azul.
    */
    public static final String ANSI_BLUE = "\u001B[38;2;32;155;255m";

    /**
     * Código ANSI para texto morado.
    */
    public static final String ANSI_PURPLE = "\u001B[38;2;122;83;179m";

    /**
     * Código ANSI para texto blanco.
    */
    public static final String ANSI_WHITE = "\u001B[38;2;255;255;255m";

    /**
     * Código ANSI para texto rojo.
    */
    public static final String ANSI_RED = "\u001B[38;2;255;0;0m";

    /**
     * Código ANSI para fondo rojo.
    */
    public static final String BG_RED = "\u001B[48;2;255;0;0m"; 

    /**
     * Código ANSI para texto blanco en fondo rojo (utilizado para errores).
    */
    public static final String CRITICAL_ERROR = BG_RED +  ANSI_BOLD + ANSI_WHITE ;

}
