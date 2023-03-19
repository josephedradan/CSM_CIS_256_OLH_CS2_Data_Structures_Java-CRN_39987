import java.util.ArrayList;

public class Menu {

    // Drinks here
    private ArrayList<Drink> menu = new ArrayList<Drink>();

    // Range to loop from (User should never touch this)
    // Start should always be 0
    private int menuRangeStart; // should be final
    private int menuRangeEnd;

    public Menu() {
        this.menuRangeStart = 0;
        this.menuRangeEnd = 0;
    }

    public void addDrink(Drink drink) {

        // Give drink its start range
        drink.setProbabilityDistributionMenuDrinkRangeStart(menuRangeEnd);

        // Set the end of range of menu
        menuRangeEnd += drink.getProbabilityDistributionDrinkRangeRelative();

        // Give drink its end range
        drink.setProbabilityDistributionMenuDrinkRangeEnd(menuRangeEnd);

        menu.add(drink);
    }

    public void removeDrink(Drink drink) {

        // Set the end of range of menu
        menuRangeEnd -= drink.getProbabilityDistributionDrinkRangeRelative();

        // Remove drink from menu
        menu.remove(drink);
    }

    public ArrayList<Drink> getMenu() {
        return menu;
    }

    public int getMenuRangeEnd() {
        return menuRangeEnd;
    }

    public int getMenuRangeStart() {
        return menuRangeStart;
    }

    // Test
//    public static void main(String[] args) {
//        Menu testMenu = new Menu();
//        testMenu.addDrink(new Drink("Generic Beer", 50, 10, 50));
//        testMenu.addDrink(new Drink("Brandy", 50, 10, 50));
//        testMenu.addDrink(new Drink("Rum", 50, 10, 50));
//        testMenu.addDrink(new Drink("Gin", 50, 10, 50));
//
//        System.out.println(testMenu.getMenu());
//        System.out.println(testMenu.getMenuRangeStart());
//        System.out.println(testMenu.getMenuRangeEnd());
//
//    }
}