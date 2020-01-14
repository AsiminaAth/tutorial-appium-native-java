package com.applitools.java4.demo;

import com.applitools.eyes.StdoutLogHandler;
import com.applitools.eyes.appium.Eyes;
import com.applitools.eyes.selenium.fluent.Target;
import io.appium.java_client.MobileBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class AndroidTest {

    public static void main(String[] args) throws MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", "Google Nexus 6");
        capabilities.setCapability("platformVersion", "7.1.1");

        capabilities.setCapability("app", "https://applitools.bintray.com/Examples/eyes-android-hello-world.apk");
        capabilities.setCapability("automationName", "UiAutomator2");
        capabilities.setCapability("newCommandTimeout", 300);

        AndroidDriver driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);

        driver.manage().timeouts().implicitlyWait(10_000, TimeUnit.MILLISECONDS);

        Eyes eyes = new Eyes();
        eyes.setApiKey("YOUR_API_KEY");

        eyes.setLogHandler(new StdoutLogHandler(true));
        eyes.setMatchTimeout(1000);

        try {
            eyes.open(driver, "AndroidHelloWorldApp", "Demo test");

            eyes.check(Target.window().withName("Open app"));

            driver.findElementById("click_me_btn").click();

            eyes.check(Target.window().withName("On ClickMe pressed"));

            eyes.check(Target.region(MobileBy.id("image")).withName("Image region"));

            driver.findElementById("simulate_diffs_check_box").click();

            eyes.check(Target.window().withName("Simulate additional difference"));

            driver.findElementById("click_me_btn").click();

            eyes.check(Target.window().withName("On ClickMe pressed (after difference)"));

            eyes.check(Target.region(MobileBy.id("image")).withName("Image region (after difference)"));

            eyes.close();
        } finally {
            driver.quit();
            eyes.abortIfNotClosed();
        }
    }
}
