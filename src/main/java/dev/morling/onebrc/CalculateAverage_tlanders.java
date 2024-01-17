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

public class CalculateAverage_tlanders {

    public record FileEntry(String name, Double temp) { }
    public record CityTemperatureData(Double min, Double mean, Double max) {}
    private static final String FILE = "./measurements.txt";

    public static void main(String[] args) throws IOException {
        Files.readAllLines(Path.of(FILE))
                .stream()
                .map(line -> line.split(";"))
                .map(splitLines -> new FileEntry(splitLines[0], Double.parseDouble(splitLines[1])))
                .forEach(System.out::println);

        System.out.println("tlanders done");
    }
}
