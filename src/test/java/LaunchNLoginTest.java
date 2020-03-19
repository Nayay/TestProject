import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.annotations.Test;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

import static org.testng.Assert.*;


public class LaunchNLoginTest {
    public WebDriver driver;
    @BeforeClass
    public void setup() {
        System.setProperty("webdriver.chrome.driver","/Users/nayaysharma/Selenium/chromedriver");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }



    @Test
    @Parameters({"username","password","url"})
    public void loginCCD(@Optional ("https://ccd.cestarcollege.com/student/loginindex") String url, @Optional("747871") String username, @Optional ("bW9kZWxzdHVkeQ==") String password) throws InterruptedException {
        driver.get(url);
        Thread.sleep(2000);
        org.testng.Assert.assertEquals(driver.getTitle(),"Login");
        WebElement Username = driver.findElement(By.xpath("//*[@id=\"userName\"]"));
        Username.sendKeys(username);
        WebElement Password = driver.findElement(By.xpath("//*[@id=\"password\"]"));
        Password.sendKeys(new String(Base64.getDecoder().decode(password)));
        WebElement LogIn = driver.findElement(By.xpath("//*[@id=\"tynamoEnter\"]"));
        LogIn.click();
        Thread.sleep(2000);
        boolean studentInformationTitle = driver.findElement(By.xpath("//h2[contains(text(),'Student Information')]")).isDisplayed();
        Assert.assertTrue(studentInformationTitle);
    }

    @Test
    public void logoutCCD() throws InterruptedException {
        WebElement Calender = driver.findElement(By.xpath("//*[@id=\"page_rootMenu-1TitleLink\"]"));
        Calender.click();
        Thread.sleep(2000);
        WebElement LogOut = driver.findElement(By.xpath("//*[@id=\"page_rootMenu-4TitleLink\"]"));
        LogOut.click();
        Thread.sleep(2000);
    }

    //This method is to capture the screenshot and return the path of the screenshot.
    public static String getScreenShot(WebDriver driver, String screenshotName) throws IOException {
        String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        // after execution, you could see a folder "FailedTestsScreenshots" under src folder
        String destination = System.getProperty("user.dir") + "/Screenshots/" + screenshotName + dateName + ".png";
        File finalDestination = new File(destination);
        FileUtils.copyFile(source, finalDestination);
        return destination;
    }

    @AfterClass
    public void end() {
        driver.quit();
    }


}