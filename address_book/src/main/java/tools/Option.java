package tools;

public class Option {
    public final Runnable callback;
    private final String label;

    public Option(String label, Runnable function) {
        this.callback = function;
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}
