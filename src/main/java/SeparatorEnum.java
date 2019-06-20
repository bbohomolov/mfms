enum SeparatorEnum {

    TAB("\t"),
    NEW_ROW("\n");

    private final String name;

    private SeparatorEnum(String s) {
        name = s;
    }

    public String getValue() {
        return name;
    }
}
