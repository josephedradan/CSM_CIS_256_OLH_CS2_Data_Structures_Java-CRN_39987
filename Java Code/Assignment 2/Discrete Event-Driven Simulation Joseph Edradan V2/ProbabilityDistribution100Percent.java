import java.util.ArrayList;
import java.util.Arrays;

public class ProbabilityDistribution100Percent {

    // The Int array you give in the constructor
    private int[] probabilityDistribution100Array;

    // Probability Distribution Index Array
    private ProbabilityDistributionIndex[] probabilityDistributionIndexArray;

    public ProbabilityDistribution100Percent(int [] probabilityDistribution100Array){

        // Create Probability Distribution Array
        this.probabilityDistribution100Array = probabilityDistribution100Array;

        generateProbabilityDistribution100ArrayList();
    }

    private void generateProbabilityDistribution100ArrayList() {

        // Probability Distribution Index Array
        probabilityDistributionIndexArray = new ProbabilityDistributionIndex[probabilityDistribution100Array.length];

        for(int i = 0; i< probabilityDistribution100Array.length; i++){
            int indexValue =  probabilityDistribution100Array[i];

            if(indexValue< 0){
                indexValue = 0;
            } else if (indexValue > 100){
                indexValue = 100;
            }

            int zeroIndexProbability = 100 - indexValue;

            ProbabilityDistributionIndex temp = new ProbabilityDistributionIndex(new int[]{zeroIndexProbability,indexValue});
            probabilityDistributionIndexArray[i] = temp;
        }
    }

    public ProbabilityDistributionIndex[] getProbabilityDistributionIndexArray() {
        return probabilityDistributionIndexArray;
    }

    public boolean getRandomBooleanFromIndex(int index){
        if(index> probabilityDistributionIndexArray.length){
            throw new IndexOutOfBoundsException("Index given is out of range");
        }

        // This will get index from 0 to 1
        int randomValue = probabilityDistributionIndexArray[index].getRandomNumber();

        if(randomValue == 1){
            return true;
        }

        return false;
    }

    public int getSizeProbabilityDistributionIndexArray(){
        return probabilityDistributionIndexArray.length;
    }

    @Override
    public String toString(){
        return Arrays.toString(probabilityDistributionIndexArray);
    }
}
