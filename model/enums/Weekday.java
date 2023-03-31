package mealplanner.model.enums;

public enum Weekday {
    MONDAY("Monday"),
    TUESDAY("Tuesday"),
    WEDNESDAY("Wednesday"),
    THURSDAY("Thursday"),
    FRIDAY("Friday"),
    SATURDAY("Saturday"),
    SUNDAY("Sunday");

    private final String value;

    Weekday(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }


    public static Weekday getWeekdayByValue(String value) {
        for (Weekday weekday: values()) {
            if (weekday.value.equals(value)) {
                return weekday;
            }
        }
        return null;
    }
}
