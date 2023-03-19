import java.lang.reflect.Array;
import java.util.Arrays;

/*
This class is to make the type of dinker, for example a person who is more likely to drink 5 beers
than anything else would be [0,0,0,0,0,1] or like [0,0,0,0,0,421123] or realistically [0,10,40,60,80,200]

Index number is the amount of beers to buy
Value at index is probability of buying that amount

*** IMPORTANT NOTE ***
    This does not handle arrays with > 1 indexes and with something that is 100 Percent chosen or with a 0 Percent index value
 */
public class ProbabilityDistributionIndex {

    // Array of probabilities
    private int[] probabilityDistributionIndexArray;

    // Max Total value of range from 0 to total sum of every index in array
    private int maxTotalValueInProbability = 0;

    public ProbabilityDistributionIndex(int[] probabilityDistributionIndexArray){

        // Array of probabilities
        this.probabilityDistributionIndexArray = probabilityDistributionIndexArray;

        // Sets the max Drinking probability range
        this.setMaxTotalValueInProbability();

    }
    private int getRandBetween(int low, int high) {
        return low + (int) ((high - low + 1) * Math.random());
    }

    private void setMaxTotalValueInProbability(){
        for(int i = 0; i< probabilityDistributionIndexArray.length; i++){
            maxTotalValueInProbability += probabilityDistributionIndexArray[i];
        }
    }
    public int getRandomNumber(){
        // Get objects number (getRandBetween is inclusive
        int randomValue = getRandBetween(1, maxTotalValueInProbability);

        int beginning = 0;
        for(int i = 0; i< probabilityDistributionIndexArray.length; i++ ){
            int end = beginning + probabilityDistributionIndexArray[i];

            // Handles 0 probability
            if(beginning == end){
                continue;
            }

            if(beginning < randomValue && randomValue <= end){
                return i;
            }
            beginning += probabilityDistributionIndexArray[i];

        }

        // Crash if error
        throw new Error("Could not find value between in getRandomNumber() method");
    }

    public int[] getProbabilityDistributionIndexArray() {
        return probabilityDistributionIndexArray;
    }

    public void setProbabilityDistributionIndexArray(int[] probabilityDistributionIndexArray) {
        this.probabilityDistributionIndexArray = probabilityDistributionIndexArray;
        this.setMaxTotalValueInProbability();
    }

    public int getProbabilityArraySize(){
        return probabilityDistributionIndexArray.length;
    }

    @Override
    public String toString(){
        return Arrays.toString(probabilityDistributionIndexArray);
    }

    // Test
//    public static void main(String[] args) {
//        // Drinking probabilities
//        // Index is amount of drinks
//        // Value is amount the probability
//
//        ProbabilityDistributionIndex test = new ProbabilityDistributionIndex(new int[]{1,50,20,20});
////        ProbabilityDistributionIndex test = new ProbabilityDistributionIndex(new int[]{100, 0, 0, 100});
//
//        for(int i = 0; i<100; i++){
//            System.out.println(test.getRandomNumber());
//        }
//    }
}


