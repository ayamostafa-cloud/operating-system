package os;

import java.util.HashMap;
import java.util.Map;

public class Memory {
    private final Map<String, String> memory = new HashMap<>();

    public void write(String variable, String value) {
        memory.put(variable, value);
    }

    public String read(String variable) {
        return memory.getOrDefault(variable, "0");
    }
}