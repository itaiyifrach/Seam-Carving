/**
 * Created by oshriamir on 4/7/17.
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class TwoDimensionalArrayList<T> extends ArrayList<ArrayList<T>>{

    public TwoDimensionalArrayList<EnergyData> twoDimensionalArrayList;

    EnergyData[][] accumulatedenergyDataPixelValues;
    public int N;

    public TwoDimensionalArrayList(EnergyData[][] _accumulatedenergyDataPixelValues){

        this.accumulatedenergyDataPixelValues = _accumulatedenergyDataPixelValues;
        this.N = accumulatedenergyDataPixelValues.length;
        setmArrayList();

    }

    public void setmArrayList() {
        for (int rowIndex = 0; rowIndex < N; rowIndex++) {
            EnergyData[] arr = accumulatedenergyDataPixelValues[0];
            ArrayList<EnergyData> arraylist = new ArrayList<>(Arrays.asList(arr));
            twoDimensionalArrayList.add(arraylist);
        }
    }



}
