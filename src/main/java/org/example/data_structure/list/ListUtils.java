package org.example.data_structure.list;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class ListUtils {

    public static <T> List<List<T>> partition(final Collection<T> sources, int partitionSize) {
        AtomicInteger counter = new AtomicInteger(0);
        Map<Integer, List<T>> groupedMap = sources.stream().collect(Collectors.groupingBy(it -> counter.getAndIncrement() / partitionSize));
        return  new ArrayList<>(groupedMap.values());
    }
}
