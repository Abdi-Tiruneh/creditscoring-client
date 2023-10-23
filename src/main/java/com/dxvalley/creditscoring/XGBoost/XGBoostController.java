package com.dxvalley.creditscoring.XGBoost;

import java.io.File;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.bind.annotation.*;

import ml.dmlc.xgboost4j.java.Booster;
import ml.dmlc.xgboost4j.java.DMatrix;
import ml.dmlc.xgboost4j.java.XGBoost;

@RestController
@RequestMapping("/api/v1/predict")
public class XGBoostController {
  @Autowired
  ResourceLoader resourceLoader;

  @PostMapping()
  public double predict(@RequestBody String input) throws Exception {
    // Load the XGBoost model
    ClassLoader classLoader = getClass().getClassLoader();
    File file = new File(classLoader.getResource("XGBmodel.pkl").getFile());

    Booster booster = XGBoost.loadModel(file.getAbsolutePath());

    float[][] dataFromCBS = new float[][] { { 123005, 1237804, 10000001, 11131005,
        1116224, 1000001, 1012305, 1019004,
        101, 1304005, 1304004, 101,
        874004, 874003, 101, 1809005,
        1809004, 101, 132505, 132504,
        1000001, 792004, 8877803, 900000 } };

    ArrayList<ArrayList<Float>> processedData = sparesify(dataFromCBS);

    System.out.println("+++++++++++++++++" + processedData);

    float[] data = toFloatArray(processedData.get(0));

    long[] rowHeaders = toLongArray(processedData.get(2));

    int[] colIndex = toIntArray(processedData.get(1));

    int numColumn = 24;

    DMatrix dmat = new DMatrix(rowHeaders, colIndex, data, DMatrix.SparseType.CSR, numColumn);

    System.out.println(">>>>>>>>>>>>>>>"+dmat);
    
    // Make the prediction
    float[][] prediction = booster.predict(dmat);

    System.out.println("<<<<<<<<<<<.>>>>>>>>>>" + prediction);

    // Return the prediction
    return prediction[0][0];
  }

  private static ArrayList<ArrayList<Float>> sparesify(float[][] M) {
    int m = M.length;
    int n = (M.length == 0 ? 0 : M[0].length), i, j;
    ArrayList<Float> data = new ArrayList<Float>();
    ArrayList<Float> colHeaders = new ArrayList<Float>(); // colHeaders matrix has N+1
                                                          // rows
    ArrayList<Float> rowIndex = new ArrayList<Float>();
    float NNZ = 0;

    for (i = 0; i < m; i++) {
      for (j = 0; j < n; j++) {
        if (M[i][j] != 0) {
          data.add(M[i][j]);
          rowIndex.add((float) j);

          // Count Number of Non Zero
          // Elements in row i
          NNZ++;
        }
      }
      colHeaders.add(NNZ);
    }

    ArrayList<ArrayList<Float>> res = new ArrayList<>();
    res.add(data);
    res.add(rowIndex);
    res.add(colHeaders);

    return res;
  }

  private float[] toFloatArray(ArrayList<Float> arrList) {
    final float[] arr = new float[arrList.size()];
    int index = 0;
    for (final Float value : arrList) {
      arr[index++] = value;
    }

    return arr;
  }

  private long[] toLongArray(ArrayList<Float> arrList) {
    final long[] arr = new long[arrList.size()];
    int index = 0;
    for (final Float value : arrList) {
      arr[index++] = value.longValue();
    }

    return arr;
  }

  private int[] toIntArray(ArrayList<Float> arrList) {
    final int[] arr = new int[arrList.size()];
    int index = 0;
    for (final Float value : arrList) {
      arr[index++] = value.intValue();
    }

    return arr;
  }
}
