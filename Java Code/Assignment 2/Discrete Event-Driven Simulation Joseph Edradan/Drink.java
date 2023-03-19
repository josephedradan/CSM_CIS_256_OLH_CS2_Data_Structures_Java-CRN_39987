public class Drink {
    private String drinkName;
    private int drinkCost;
    private int drinkDuration;

    // It's a number where the probability is not based within the range from 1 to 100 but from 1 to probabilityDistributionMenuDrinkRangeEnd
    private int probabilityDistributionDrinkRangeRelative;

    // Given by menu
    private int probabilityDistributionMenuDrinkRangeStart;
    private int probabilityDistributionMenuDrinkRangeEnd;

    public Drink(String drinkName, int drinkCost, int drinkDuration, int probabilityDistributionDrinkRangeRelative) {
        this.drinkName = drinkName;
        this.drinkCost = drinkCost;
        this.drinkDuration = drinkDuration;

        this.probabilityDistributionDrinkRangeRelative = probabilityDistributionDrinkRangeRelative;
    }

    public String getDrinkName() {
        return drinkName;
    }

    public void setDrinkName(String drinkName) {
        this.drinkName = drinkName;
    }

    public int getDrinkCost() {
        return drinkCost;
    }

    public void setDrinkCost(int drinkCost) {
        this.drinkCost = drinkCost;
    }

    public int getDrinkDuration() {
        return drinkDuration;
    }

    public void setDrinkDuration(int drinkDuration) {
        this.drinkDuration = drinkDuration;
    }

    public int getProbabilityDistributionDrinkRangeRelative() {
        return probabilityDistributionDrinkRangeRelative;
    }

    public void setProbabilityDistributionDrinkRangeRelative(int probabilityDistributionDrinkRangeRelative) {
        this.probabilityDistributionDrinkRangeRelative = probabilityDistributionDrinkRangeRelative;
    }

    public int getProbabilityDistributionMenuDrinkRangeStart() {
        return probabilityDistributionMenuDrinkRangeStart;
    }

    public void setProbabilityDistributionMenuDrinkRangeStart(int probabilityDistributionMenuDrinkRangeStart) {
        this.probabilityDistributionMenuDrinkRangeStart = probabilityDistributionMenuDrinkRangeStart;
    }

    public int getProbabilityDistributionMenuDrinkRangeEnd() {
        return probabilityDistributionMenuDrinkRangeEnd;
    }

    public void setProbabilityDistributionMenuDrinkRangeEnd(int probabilityDistributionMenuDrinkRangeEnd) {
        this.probabilityDistributionMenuDrinkRangeEnd = probabilityDistributionMenuDrinkRangeEnd;
    }

    @Override
    public String toString() {
        return String.format("$%s %s lasting %s minutes", drinkCost, drinkName, drinkDuration);
    }
}
