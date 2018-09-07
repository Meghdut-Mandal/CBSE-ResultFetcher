/*
 * Copyright 2018 Meghdut  Mandal.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package newton.test;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 *
 * @author Meghdut Mandal
 */
public class StreamProgress {

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        int size = 250;
        Stream<Integer> stream = readData(size);

        Consumer<Long> progressConsumer = progress -> {
            // "Filter" the output here: Report only every 10th element
            if (progress % 10 == 0) {
                double relative = (double) progress / (size - 1);
                double percent = relative * 100;
                System.out.printf(
                        "Progress %8d, relative %2.5f, percent %3.2f\n",
                        progress, relative, percent);
            }
        };

        Integer result = stream
                .map(element -> process(element))
                .map(progressMapper(progressConsumer))
                .reduce(0, (a, b) -> a + b);

        System.out.println("result " + result);
    }

    private static <T> Function<T, T> progressMapper(
            Consumer<? super Long> progressConsumer) {
        AtomicLong counter = new AtomicLong(0);
        return t -> {
            long n = counter.getAndIncrement();
            progressConsumer.accept(n);
            return t;
        };

    }

    private static Integer process(Integer element) {
        return element * 2;
    }

    private static Stream<Integer> readData(int size) {
        Iterator<Integer> iterator = new Iterator<Integer>() {
            int n = 0;

            @Override
            public Integer next() {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return n++;
            }

            @Override
            public boolean hasNext() {
                return n < size;
            }
        };
        return StreamSupport.stream(
                Spliterators.spliteratorUnknownSize(
                        iterator, Spliterator.ORDERED), false);
    }
}
