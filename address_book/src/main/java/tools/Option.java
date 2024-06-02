package tools;

public class Option {
    private final Runnable callback;
    private final String label;

    public Option(String label, Runnable function){
        this.callback = function;
        this.label = label;
    }

    public void execute(){
        callback.run();
    }

    @Override
    public String toString(){
        return label;
    }
}
