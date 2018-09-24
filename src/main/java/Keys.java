public enum Keys {
    RAYLEIGH("R"),
    GAMMA("Г"),
    WEIBULL("W"),
    TRUNCATED_NORMAL("TN"),
    EXPONENTIAL("Exp"),
    NORMAL("N"),
    PT("p(t)"),
    FT("f(t)"),
    M("m"),
    U("U"),
    SIGMA("sigma");
    private final String value;

    Keys(String value) { this.value = value; }
    public String getValue() {
        return this.value;
    }
}
