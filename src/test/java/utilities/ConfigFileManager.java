package utilities;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class ConfigFileManager {
    Properties props = new Properties();

    public void loadConfig() {
        Path target = Path.of("src", "test", "resources", "config.properties").normalize();

        if (Files.exists(target)) {
            //System.out.println("File found at: " + target.toAbsolutePath());
            try (var reader = Files.newBufferedReader(target, java.nio.charset.StandardCharsets.UTF_8))
            {
                this.props.load(reader);
            } catch (IOException e) {
                System.err.println("Error reading properties: " + e.getMessage());
            }
        } else {
            System.err.println("Could not locate the properties file at: " + target.toAbsolutePath());
        }
    }

    public String getCity() {
        return this.props.getProperty("city", "chennai");
    }
}