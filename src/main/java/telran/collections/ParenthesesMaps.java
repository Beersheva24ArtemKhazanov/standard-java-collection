package telran.collections;

import java.util.Map;

public record ParenthesesMaps(Map<Character, Character> openCloseMap, Map<Character, Character> closeOpenMap) {
    //closeOpenMap - key is open parentheses, value is closing parentheses
    //closeOpenMap - key is closing parentheses, value is open parentheses
}
