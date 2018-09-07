/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newton.resultApi;
/*
import com.google.common.io.Files;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;


public class SeleniumClient {

    static WebDriver driver;
    static String cbse18 = "http://cbseresults.nic.in/class12zpq/class12th18.htm";

    private static void tor() {
        File torProfileDir = new File(
                "E:\\CBSE\\tor\\To\\Browser\\TorBrowser\\Data\\Browser\\profile.default");
        FirefoxBinary binary = new FirefoxBinary(new File(
                "E:\\CBSE\\tor\\To\\Browser\\firefox.exe"));
        FirefoxProfile torProfile = new FirefoxProfile(torProfileDir);
        torProfile.setPreference("webdriver.load.strategy", "unstable");

        try {
            binary.startProfile(torProfile, torProfileDir, "");
        } catch (IOException e) {
            e.printStackTrace();
        }
        FirefoxProfile profile = new FirefoxProfile();
        profile.setPreference("network.proxy.type", 1);
        profile.setPreference("network.proxy.socks", "127.0.0.1");
        profile.setPreference("network.proxy.socks_port", 9150);
        FirefoxOptions option = new FirefoxOptions();
        option.setProfile(profile);
        FirefoxDriver dri = new FirefoxDriver(option);
        driver.get("http://www.google.com");
//Set  timeout  for 5 seconds so that the page may load properly within that time
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        System.out.println(driver.getPageSource());
    }

    private static String seleniumCBSE(String roll, String Schode, String centercode) {
        //  = "6622710", Schode = "09909", centercode = "6213";

        driver.get(cbse18);

        WebElement rgno = driver.findElement(By.name("regno"));
        rgno.sendKeys(roll);
        WebElement sch = driver.findElement(By.name("sch"));
        sch.sendKeys(Schode);
        WebElement cntr = driver.findElement(By.name("cno"));
        cntr.sendKeys(centercode);
        WebElement btn = driver.findElement(By.name("B2"));
        btn.click();
        String ps = driver.getPageSource();
        //  System.out.println("Done " + roll);
        return ps;
    }

    public static void main(String[] args) {
        tor();

    }

    public static void mainh(String[] args) throws IOException {
        System.setProperty("webdriver.chrome.driver", "E:\\CBSE\\chromedriver.exe");

        final ChromeOptions chromeOptions = new ChromeOptions();
        //   chromeOptions.addArguments("--headless");

        driver = new ChromeDriver(chromeOptions);
        String schoolcode = "56008", centr = "6221";

        for (int i = 6626350; i < 6626400; i++) {

            String src = seleniumCBSE(i + "", schoolcode, centr);
            if (src.contains("Result Not Found")) {
                continue;
            }

            File out = new File("E:\\CBSE\\DAVPandevshar\\" + i + ".html");
            out.createNewFile();
            Files.write(src.getBytes(), out);

        }
    }

}
*/