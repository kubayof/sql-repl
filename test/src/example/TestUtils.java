package example;

import java.io.File;

public class TestUtils {
    @SuppressWarnings("all")
    public static void deleteTestDatabase() {
        File[] files = new File("./").listFiles();
        for (File file : files) {
            if (file.getName().matches("test_db.*")) {
                file.delete();
            }
        }
    }
}
