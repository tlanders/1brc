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
import java.util.stream.Collectors;

public class CalculateAverage_tlanders {

    public record CityTemperatureData(double min, double max, double total, int count) {
    @Override
    public String toString() {
        return min + "/" + (Math.round(total / count * 10) / 10.0) + "/" + max;
    }
}

    private static final String FILE = "./measurements.txt";

    public static void main(String[] args) throws IOException {
        String result = Files.readAllLines(Path.of(FILE))
                // .parallelStream()
                .stream()
                .map(line -> line.split(";"))
                .reduce(
                        new TreeMap<String, CityTemperatureData>(),
                        (Map<String, CityTemperatureData> map, String[] citySplitData) -> {
                            String city = citySplitData[0];
                            double temp = Math.round(Double.parseDouble(citySplitData[1]) * 10) / 10.0;
                            CityTemperatureData cityData = map.get(city);
                            if (cityData == null) {
                                var rTemp = Math.round(temp * 10) / 10.0;
                                cityData = new CityTemperatureData(rTemp, rTemp, rTemp, 1);
                            }
                            else {
                                var min = Math.min(cityData.min(), temp);
                                var max = Math.max(cityData.max(), temp);
                                cityData = new CityTemperatureData(min, max, cityData.total() + temp, cityData.count() + 1);
                            }
                            map.put(city, cityData);
                            return map;
                        },
                        (map1, map2) -> {
                            map1.putAll(map2);
                            return map1;
                        })
                .entrySet()
                .stream()
                .map(entry -> entry.getKey() + "=" + entry.getValue())
                .collect(Collectors.joining(", "));

        System.out.println("{" + result + "}");
    }
}
