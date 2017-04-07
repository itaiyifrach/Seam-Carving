/**
 * Created by oshriamir on 4/7/17.
 */

public class EnergyMatrix extends Matrix {

    EnergyData[][] energyData;

    public EnergyMatrix(double[][] _data) {
    }


    public EnergyData[][] getData() {
        return energyData;
    }

    public void setData(EnergyData[][] data) {
        this.energyData = data;
    }

    private void setEnergyValues(double[][] _data){
        for(int rowIndex = 0; rowIndex< _data.length; rowIndex++){
            for(int colIndex = 0; colIndex<_data[0].length; colIndex++){
                this.energyData[rowIndex][colIndex].setEnergyValue(_data[rowIndex][colIndex]);
            }
        }
    }
}
