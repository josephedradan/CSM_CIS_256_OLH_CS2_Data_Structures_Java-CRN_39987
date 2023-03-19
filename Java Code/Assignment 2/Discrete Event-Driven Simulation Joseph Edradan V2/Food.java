public abstract class Food {
    protected String foodName;
    protected int foodCost;
    protected int foodDuration;

    // It's a number where the probability is not based within the range from 1 to 100 but from 1 to probabilityDistributionMenuDrinkRangeEnd
    protected int probabilityDistributionDrinkRangeRelative;

    // Set by menu
    protected int probabilityDistributionMenuDrinkRangeStart;
    protected int probabilityDistributionMenuDrinkRangeEnd;

    public Food(String foodName, int foodCost, int foodDuration, int probabilityDistributionDrinkRangeRelative) {
        this.foodName = foodName;
        this.foodCost = foodCost;
        this.foodDuration = foodDuration;
        this.probabilityDistributionDrinkRangeRelative = probabilityDistributionDrinkRangeRelative;
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

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public int getFoodCost() {
        return foodCost;
    }

    public void setFoodCost(int foodCost) {
        this.foodCost = foodCost;
    }

    public int getFoodDuration() {
        return foodDuration;
    }

    public void setFoodDuration(int foodDuration) {
        this.foodDuration = foodDuration;
    }

    @Override
    public String toString() {
        return String.format("$%s %s lasting %s minutes", foodCost, foodName, foodDuration);
    }
}
