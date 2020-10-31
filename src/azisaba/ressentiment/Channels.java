package azisaba.ressentiment;

public class Channels {

    public static final String INIT;
    public static final String REGISTER;
    public static final String CONTROL;

    static {
        String name = "Ressentiment";
        String separator = ":";
        String prefix = name + separator;

        INIT = prefix + "init";
        REGISTER = prefix + "register";
        CONTROL = prefix + "control";
    }

}
