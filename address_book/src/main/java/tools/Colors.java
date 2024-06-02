package tools;

public class Colors {

    public static final String ANSI_BOLD = "\u001B[1m";


    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_YELLOW = "\u001B[38;2;245;213;99m";
    public static final String ANSI_GREEN = "\u001B[38;2;111;178;9m";
    public static final String ANSI_BLUE = "\u001B[38;2;32;155;255m";
    public static final String ANSI_PURPLE = "\u001B[38;2;122;83;179m";
    public static final String ANSI_WHITE = "\u001B[38;2;255;255;255m";

    public static final String BG_RED = "\u001B[48;2;255;0;0m"; 

    public static final String CRITICAL_ERROR = BG_RED +  ANSI_BOLD + ANSI_WHITE ;
    

}
