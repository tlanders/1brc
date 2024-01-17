/*
 *  Copyright 2023 The original authors
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package dev.morling.onebrc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class CalculateAverage_tlanders {

    static class CityTemperatureData {
        public double min;
        public double max;
        public double total;
        public int count;

        public CityTemperatureData(double min, double max, double total, int count) {
            this.min = min;
            this.max = max;
            this.total = total;
            this.count = count;
        }

        @Override
        public String toString() {
            return min + "/" + (Math.round(total / count * 10) / 10.0) + "/" + max;
        }
    }

    private static final String FILE = "./measurements.txt";

    public static void main(String[] args) throws IOException {
        Map<String, CityTemperatureData> result = Files.readAllLines(Path.of(FILE))
                // .parallelStream()
                .stream()
                .reduce(
                        new TreeMap<>(),
                        (Map<String, CityTemperatureData> map, String line) -> {
                            String[] citySplitData = line.split(";");
                            double temp = Double.parseDouble(citySplitData[1]);
                            // double temp = Math.round(Double.parseDouble(citySplitData[1]) * 10) / 10.0;
                            CityTemperatureData cityData = map.get(citySplitData[0]);
                            if (cityData != null) {
                                cityData.min = Math.min(cityData.min, temp);
                                cityData.max = Math.max(cityData.max, temp);
                                cityData.total += temp;
                                cityData.count++;
                            }
                            else {
                                map.put(citySplitData[0], new CityTemperatureData(temp, temp, temp, 1));
                            }
                            return map;
                        },
                        (map1, map2) -> {
                            map1.putAll(map2);
                            return map1;
                        });

        System.out.println(result);
    }
}
