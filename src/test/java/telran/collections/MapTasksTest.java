package telran.collections;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.*;

public class MapTasksTest {
    Integer[] numbers = { 10, 5, 7, -4, 1 };
    LinkedHashMap<Integer, Integer> map;

    private void setUpMap() {
        map = new LinkedHashMap<>(10, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<Integer, Integer> eldestEntry) {
                return size() > numbers.length;
            }
        };
        Arrays.stream(numbers).forEach(n -> map.put(n, n * n));
    }

    @Test
    void displayOccurrencesTest() {
        String[] strings = { "lpm", "ab", "a", "c", "cb", "cb", "c", "lpm", "lpm" };
        MapTasks.displayOccurrences(strings);
    }

    @Test
    void displayOccurrencesStreamTest() {
        String[] strings = { "lpm", "ab", "a", "c", "cb", "cb", "c", "lpm", "lpm" };
        MapTasks.displayOccurrencesStream(strings);
    }

    @Test
    void getGroupingByNumberOfDigitsTest() {
        int[][] array = { { 100, 1, 50 }, { 20, 30 }, { 1 } };
        Map<Integer, Integer[]> actualMap = MapTasks.getGroupingByNumberOfDigits(array);
        Integer[] oneDigitsNembers = { 1, 1 };
        Integer[] twoDigitsNembers = { 50, 20, 30 };
        Integer[] threeDigitsNembers = { 100 };
        assertArrayEquals(actualMap.get(1), oneDigitsNembers);
        assertArrayEquals(actualMap.get(2), twoDigitsNembers);
        assertArrayEquals(actualMap.get(3), threeDigitsNembers);
    }

    @Test
    void getDistributionByNumberOfDigits() {
        int[][] array = { { 100, 1, 50 }, { 20, 30 }, { 1 } };
        Map<Integer, Long> actualMap = MapTasks.getDistributionByNumberOfDigits(array);
        assertEquals(2, actualMap.get(1));
        assertEquals(3, actualMap.get(2));
        assertEquals(1, actualMap.get(3));
    }

    @Test
    void displayDigitsDistributionTest() {
        MapTasks.displayDigitsDistribution();
    }

    @Test
    void getParenthesesMapsTest() {
        Character[][] openCloseParentheses = {
                { '[', ']' }, { '(', ')' }, { '{', '}' }
        };
        ParenthesesMaps maps = MapTasks.getParenthesesMaps(openCloseParentheses);
        Map<Character, Character> openCloseMap = maps.openCloseMap();
        Map<Character, Character> closeOpenMap = maps.closeOpenMap();
        assertEquals(']', openCloseMap.get('['));
        assertEquals('[', closeOpenMap.get(']'));
    }

    @Test
    void LinkedHashMapTest() {
        setUpMap();
        assertArrayEquals(numbers, map.keySet().toArray(Integer[]::new));
    }

    @Test
    void LinkedHashMapWithPutTest() {
        setUpMap();
        map.put(3, 9);
        Integer[] expected = { 5, 7, -4, 1, 3 };
        assertArrayEquals(expected, map.keySet().toArray(Integer[]::new));
    }

    @Test
    void LinkedHashMapWithPutAndGetTest() {
        setUpMap();
        map.get(10); // 10 will be moved at end
        map.put(3, 9);
        Integer[] expected = { 7, -4, 1, 10, 3 };
        assertArrayEquals(expected, map.keySet().toArray(Integer[]::new));
    }

}
