/*
This class is to make the type of dinker, for example a person who is more likely to drink 5 beers
than anything else would be [0,0,0,0,0,1] or like [0,0,0,0,0,421123] or realistically [0,10,40,60,80,200]

Index number is the amount of beers to buy
Value at index is probability of buying that amount

*** IMPORTANT NOTE ***
    This does not handle arrays with > 1 indexes and with something that is 100 Percent chosen or with a 0 Percent index value
 */
public class ProbabilityDistribution {

    // Array of probabilities
    private int[] probabilityArray;

    // Max Total value of range from 0 to total sum of every index in array
    private int maxTotalValueInProbability;

    public ProbabilityDistribution(int[] drinkingProbabilityArray){

        // Array of probabilities
        this.probabilityArray = drinkingProbabilityArray;

        // The number is always 0
        this.maxTotalValueInProbability = 0;

        // Sets the max Drinking probability range
        this.setMaxTotalValueInProbability();

    }
    private int getRandBetween(int low, int high) {
        return low + (int) ((high - low + 1) * Math.random());
    }

    private void setMaxTotalValueInProbability(){
        for(int i = 0; i< probabilityArray.length; i++){
            maxTotalValueInProbability += probabilityArray[i];
        }
    }
    public int getRandomNumber(){
        // Get random number (getRandBetween is inclusive
        int randomValue = getRandBetween(1, maxTotalValueInProbability);

        int beginning = 0;
        for(int i = 0; i< probabilityArray.length; i++ ){
            int end = beginning + probabilityArray[i];

            // Handles 0 probability
            if(beginning == end){
//                return null;
                throw new IllegalArgumentException("Cannot handle Zero Probability");
            }

            if(beginning < randomValue && randomValue <= end){
                return i;
            }
            beginning += probabilityArray[i];

        }

        // Crash if error
        throw new UnknownError("Could not find value between in getRandomNumber() method");
    }

    public int[] getProbabilityArray() {
        return probabilityArray;
    }

    public void setProbabilityArray(int[] probabilityArray) {
        this.probabilityArray = probabilityArray;
        this.setMaxTotalValueInProbability();
    }
    public int getProbabilityArraySize(){
        return probabilityArray.length;
    }

    // Test
//    public static void main(String[] args) {
//        // Drinking probabilities
//        // Index is amount of drinks
//        // Value is amount the probability
//
//        ProbabilityDistribution test = new ProbabilityDistribution(new int[]{1,50,20,20});
////        ProbabilityDistribution test = new ProbabilityDistribution(new int[]{100, 0, 0, 100});
//
//        for(int i = 0; i<100; i++){
//            System.out.println(test.getRandomNumber());
//        }
//    }
}


