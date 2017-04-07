import java.util.ArrayList;

/**
 * Created by oshriamir on 4/7/17.
 */
public class Algorithms {

    /**
     * this algorithm calculate by dynamic programing approach, finding the path of minimum energy cost
     * from one end of the image to another.
     *
     * @param  - 2D dimensional array consists energy function values for each pixel.
     *
     * @return EnergyData[][] object consists for each pixel the original pixel's energy function values &  accumulated energy value.
     */

    /*
    public static EnergyData[][] SEAM_CARVING(EnergyData[][] energyDataPixelValues){
        int N = energyDataPixelValues.length;       // #rows
        int M = energyDataPixelValues[0].length;    //#cols

        for(int colIndex = 0; colIndex<M; colIndex++){
            energyDataPixelValues[0][colIndex].setCumulatedEnergyValueUntilMe(energyDataPixelValues[0][colIndex].getEnergyValue());
        }

        for(int rowIndex = 1; rowIndex<N; rowIndex++){
            for(int colIndex = 0; colIndex<M; colIndex++){
                if(colIndex == 0){
                    energyDataPixelValues[rowIndex][colIndex].setCumulatedEnergyValueUntilMe(
                            Math.min(energyDataPixelValues[rowIndex-1][colIndex].getCumulatedEnergyValueUntilMe(),
                                    energyDataPixelValues[rowIndex - 1][colIndex + 1].getCumulatedEnergyValueUntilMe()));
                }else if(colIndex == M-1){
                    energyDataPixelValues[rowIndex][colIndex].setCumulatedEnergyValueUntilMe(
                            Math.min(energyDataPixelValues[rowIndex-1][colIndex-1].getCumulatedEnergyValueUntilMe(),
                                    energyDataPixelValues[rowIndex - 1][colIndex].getCumulatedEnergyValueUntilMe()));

                }else{
                    energyDataPixelValues[rowIndex][colIndex].setCumulatedEnergyValueUntilMe(MIN_THREE_VAR(
                            energyDataPixelValues[rowIndex-1][colIndex-1].getCumulatedEnergyValueUntilMe(),
                            energyDataPixelValues[rowIndex-1][colIndex].getCumulatedEnergyValueUntilMe(),
                            energyDataPixelValues[rowIndex-1][colIndex+1].getCumulatedEnergyValueUntilMe()
                            ));
                }
            }
        }
        return null; // ------------------- change this return statement ------------- //
    }
    */

    public static TwoDimensionalArrayList<EnergyData> SEAM_CARVING (TwoDimensionalArrayList<EnergyData> twoDimensionalArrayList){
        int N = twoDimensionalArrayList.size();               // #rows
        int M = twoDimensionalArrayList.get(0).size();        //#cols

        for(int colIndex = 0; colIndex<M; colIndex++){
            twoDimensionalArrayList.get(0).get(colIndex).setCumulatedEnergyValueUntilMe(twoDimensionalArrayList.get(0).get(colIndex).getEnergyValue());
        }

        for(int rowIndex = 1; rowIndex<N; rowIndex++){
            for(int colIndex = 0; colIndex<M; colIndex++){
                if(colIndex == 0){
                    twoDimensionalArrayList.get(rowIndex).get(colIndex).setCumulatedEnergyValueUntilMe(
                            Math.min(twoDimensionalArrayList.get(rowIndex-1).get(colIndex).getCumulatedEnergyValueUntilMe(),
                                    twoDimensionalArrayList.get(rowIndex-1).get(colIndex+1).getCumulatedEnergyValueUntilMe()));
                }else if(colIndex == M-1){
                    twoDimensionalArrayList.get(rowIndex).get(colIndex).setCumulatedEnergyValueUntilMe(
                            Math.min(twoDimensionalArrayList.get(rowIndex-1).get(colIndex-1).getCumulatedEnergyValueUntilMe(),
                                    twoDimensionalArrayList.get(rowIndex-1).get(colIndex).getCumulatedEnergyValueUntilMe()));

                }else{
                    twoDimensionalArrayList.get(rowIndex).get(colIndex).setCumulatedEnergyValueUntilMe(MIN_THREE_VAR(
                            twoDimensionalArrayList.get(rowIndex-1).get(colIndex-1).getCumulatedEnergyValueUntilMe(),
                            twoDimensionalArrayList.get(rowIndex-1).get(colIndex).getCumulatedEnergyValueUntilMe(),
                            twoDimensionalArrayList.get(rowIndex-1).get(colIndex+1).getCumulatedEnergyValueUntilMe()
                    ));
                }
            }
        }
        return REMOVAL_SEAM(twoDimensionalArrayList);
    }


    // array = new ArrayList<ArrayList<String>>();

    /**
     * this Algorithm assume that this 2Dimensional ArrayList represent a 2Dimensional array,
     * consists pixel's accumulated energy value.
     *
     * this Algorithm do backTracking in order to finding the path of minimum energy cost
     * from one end of the image to another.
     *
     * @return - 2Dimensional ArrayList without the minimal energy cost path.
     */
    private static TwoDimensionalArrayList<EnergyData> REMOVAL_SEAM (TwoDimensionalArrayList<EnergyData> twoDimensionalArrayList){
        int N = twoDimensionalArrayList.size();            // #rows (number of inner array_lists)
        int M = twoDimensionalArrayList.get(0).size();      // #cols (number of elements in each inner array_list)

        int col = N-1;

        ArrayList<EnergyData> mArrayList = twoDimensionalArrayList.get(col);
        int index_Of_Minimal_Accumulated_Energy_Value_This_Inner_ArrayList = findIndexMinimalAccumulateEnergyValue(mArrayList);
        mArrayList.remove(index_Of_Minimal_Accumulated_Energy_Value_This_Inner_ArrayList);

        return removalSeam(twoDimensionalArrayList, col, index_Of_Minimal_Accumulated_Energy_Value_This_Inner_ArrayList);
    }

    private static TwoDimensionalArrayList<EnergyData> removalSeam(TwoDimensionalArrayList<EnergyData> twoDimensionalArrayList,
                                                                   int rowNumber, int previousRowValueIndex) {
        if (rowNumber < 0) {
            return twoDimensionalArrayList;
        }
        int minValueToRemoveIndex;


        if (previousRowValueIndex == 0) {
            minValueToRemoveIndex = INDEX_OF_MIN_VALUE(twoDimensionalArrayList.get(rowNumber).get(previousRowValueIndex).getCumulatedEnergyValueUntilMe(),
                    twoDimensionalArrayList.get(rowNumber).get(previousRowValueIndex + 1).getCumulatedEnergyValueUntilMe(), 0);

        } else if (previousRowValueIndex == twoDimensionalArrayList.size() - 1) {
            minValueToRemoveIndex = INDEX_OF_MIN_VALUE(twoDimensionalArrayList.get(rowNumber).get(previousRowValueIndex).getCumulatedEnergyValueUntilMe(),
                    twoDimensionalArrayList.get(rowNumber).get(previousRowValueIndex - 1).getCumulatedEnergyValueUntilMe(), twoDimensionalArrayList.size() - 1);


        } else {
            minValueToRemoveIndex = INDEX_OF_MIN_VALUE(twoDimensionalArrayList.get(rowNumber).get(previousRowValueIndex - 1).getCumulatedEnergyValueUntilMe(),
                    twoDimensionalArrayList.get(rowNumber).get(previousRowValueIndex).getCumulatedEnergyValueUntilMe(),
                    twoDimensionalArrayList.get(rowNumber).get(previousRowValueIndex + 1).getCumulatedEnergyValueUntilMe(),
                    previousRowValueIndex);


        }
        ArrayList<EnergyData> mArrayList = twoDimensionalArrayList.get(rowNumber);
        mArrayList.remove(minValueToRemoveIndex);

        rowNumber = rowNumber-1;
        return removalSeam(twoDimensionalArrayList,rowNumber, minValueToRemoveIndex);

    }

    private static int findIndexMinimalAccumulateEnergyValue(ArrayList<EnergyData> mArrayList){
        int indexWithMinValue = 0;
        double minAccumulatedEnergyValue = mArrayList.get(0).getCumulatedEnergyValueUntilMe();
        for (int index = 1; index < mArrayList.size(); index++) {
            if(mArrayList.get(index).getCumulatedEnergyValueUntilMe() < minAccumulatedEnergyValue){
                indexWithMinValue = index;
                minAccumulatedEnergyValue = mArrayList.get(index).getCumulatedEnergyValueUntilMe();
            }
        }
        return indexWithMinValue;

    }
    private static double MIN_THREE_VAR(double x, double y, double z){
        return Math.min(x, Math.min(y,z));
    }

    /**
     * x represent - the value of previousRowValueIndex - 1
     * y represent - the value of previousRowValueIndex
     * z represent - the value of previousRowValueIndex + 1
     * @param x
     * @param y
     * @param z
     * @param previousRowValueIndex
     * @return
     */
    private static int INDEX_OF_MIN_VALUE(double x, double y, double z, int previousRowValueIndex){
        double result = MIN_THREE_VAR( x,  y,  z);

        if(result == x){
            return previousRowValueIndex-1;
        }else if(result == y){
            return previousRowValueIndex;
        }else{
            return previousRowValueIndex+1;
        }
    }

    private static int INDEX_OF_MIN_VALUE(double x, double y, int previousRowValueIndex){
        double result = Math.min(x, y);

        if(result == x){
            return previousRowValueIndex-1;
        }else{
            return previousRowValueIndex;
        }
    }
}

