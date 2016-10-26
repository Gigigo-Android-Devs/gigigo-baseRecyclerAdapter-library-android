package com.gigigo.baserecycleradapter_demoapp;

import com.gigigo.baserecycleradapter_demoapp.entities.ImageCell;
import com.gigigo.baserecycleradapter_demoapp.entities.TextCell;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DataGenerator {

  private static String[] categories =
      new String[] { "animals", "architecture", "nature", "people", "tech" };

  public static List<Object> generateRandomDataList(int num) {
    ArrayList<Object> data = new ArrayList<>();
    for (int i = 0; i < num; i++) {
      if (i % 2 == 0) {
        data.add(generateRandomImageData());
      } else {
        data.add(generateRandomTextData(i));
      }
    }
    return data;
  }

  public static ImageCell generateRandomImageData() {
    return new ImageCell("http://placeimg.com/250/250/" + getCategory());
  }

  public static TextCell generateRandomTextData(int index) {
    return new TextCell("item" + index);
  }

  private static String getCategory() {
    int random = new Random().nextInt();
    int index = Math.abs(random) % (categories.length - 1);
    return categories[index];
  }
}
