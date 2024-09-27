package telran.collections;

import java.util.*;
import java.util.stream.*;
import java.util.function.*;
import java.util.Map.*;

public class MapTasks {
    public static void displayOccurrences(String[] strings) {
        // input {"lpm", "ab", "a", "c", "cb", "cb", "c", "lpm", "lpm"}
        // output:
        // lpm -> 3
        // c -> 2
        // cb -> 2
        // a -> 1
        // ab -> 1
        HashMap<String, Long> occurrencesMap = getMapOccurrences(strings);
        TreeMap<Long, TreeSet<String>> sortedOccurrencesMap = getSortedOccurrencesMap(occurrencesMap);
        System.out.println(occurrencesMap);
        System.out.println(sortedOccurrencesMap);
        displaySortedOccurrencesMap(sortedOccurrencesMap);
    }

    public static void displayOccurrencesStream(String[] strings) {
        Arrays.stream(strings).collect(Collectors.groupingBy(Function.identity(), Collectors.counting())).entrySet().stream().sorted((e1,e2) -> {
            int res = Long.compare(e2.getValue(), e1.getValue());
            return res == 0 ? e1.getKey().compareTo(e2.getKey()) : res;
        }).forEach(e -> System.out.printf("%s -> %d\n", e.getKey(), e.getValue()));
    }

    private static void displaySortedOccurrencesMap(TreeMap<Long, TreeSet<String>> sortedOccurrencesMap) {
        sortedOccurrencesMap.forEach((occ, treeSet) -> treeSet.forEach(s -> System.out.printf("%s -> %d \n", s, occ)));
    }

    private static TreeMap<Long, TreeSet<String>> getSortedOccurrencesMap(HashMap<String, Long> occurrencesMap) {
        TreeMap<Long, TreeSet<String>> res = new TreeMap<>(Comparator.reverseOrder());
        occurrencesMap.entrySet().forEach(e -> res.computeIfAbsent(e.getValue(), k -> new TreeSet<>()).add(e.getKey()));
        return res;
    }

    private static HashMap<String, Long> getMapOccurrences(String[] strings) {
        HashMap<String, Long> result = new HashMap<>();
        Arrays.stream(strings).forEach(s -> result.merge(s, 1l, Long::sum));
        return result;
    }

    public static Map<Integer, Integer[]> getGroupingByNumberOfDigits(int[][] array) {
        Map<Integer, List<Integer>> map = StreamOfNumber(array)
            .collect(Collectors.groupingBy(n ->Integer.toString(n).length()));
        return map.entrySet().stream().collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue().toArray(Integer[]::new)));
    }

    private static Stream<Integer> StreamOfNumber(int[][] array) {
        return Arrays.stream(array).flatMapToInt(Arrays::stream).boxed();
    }

    public static Map<Integer, Long> getDistributionByNumberOfDigits(int[][] array) {
        return StreamOfNumber(array).collect(Collectors.groupingBy(n -> Integer.toString(n).length(), Collectors.counting()));
    }

    public static void displayDigitsDistribution() {
        new Random().ints(1_000_000, 0, Integer.MAX_VALUE)
        .flatMap(n -> Integer.toString(n).chars()).boxed()
        .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
        .entrySet().stream().sorted(Entry.comparingByValue(Comparator.reverseOrder()))
        .forEach(e -> System.out.printf("%d -> %d\n", e.getKey(), e.getValue()));

    }

    public static ParenthesesMaps getParenthesesMaps(Character[][] openCloseParentheses) {
        Map<Character, Character> openCloseMap = getMap(openCloseParentheses, ar -> ar[0], ar -> ar[1]);
        Map<Character, Character> closeOpenMap = getMap(openCloseParentheses, ar -> ar[1], ar -> ar[0]);
        return new ParenthesesMaps(openCloseMap, closeOpenMap);
    }

    private static Map<Character, Character> getMap(Character[][] openCloseParentheses, Function<Character[],Character> keyFn, Function<Character[],Character> valueFn) {
        return Arrays.stream(openCloseParentheses).collect(Collectors.toMap(keyFn, valueFn));
    }

}
