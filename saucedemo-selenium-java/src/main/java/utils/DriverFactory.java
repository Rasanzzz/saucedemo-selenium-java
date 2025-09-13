package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DriverFactory {
    private static Properties properties = new Properties();

    static {
        try (FileInputStream fis = new FileInputStream ("config/config.properties")){
            properties.load(fis);
        } catch (IOException e) {
            throw new RuntimeException("couldn't load config.properties");
        }
    }
    public static WebDriver initializeDriver() {
        String browser = properties.getProperty("browser");
        boolean headless = Boolean.parseBoolean(properties.getProperty("headless"));

        WebDriver driver;
        if (browser.equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            if (headless) options.addArguments("--headless=new");
            options.addArguments("--window-size=1920,1080");
            driver = new ChromeDriver(options);
        } else {
            throw new IllegalArgumentException("Unsupported browser: " + browser);
        }
        return driver;
    }

    public static String getBaseUrl() {
        return properties.getProperty("baseUrl");
    }

}
