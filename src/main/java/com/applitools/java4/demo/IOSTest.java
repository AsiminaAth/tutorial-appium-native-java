package com.applitools.java4.demo;

import com.applitools.eyes.StdoutLogHandler;
import com.applitools.eyes.appium.Eyes;
import com.applitools.eyes.selenium.fluent.Target;
import io.appium.java_client.MobileBy;
import io.appium.java_client.TouchAction;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class IOSTest {

    public static void main(String[] args) throws MalformedURLException {
        DesiredCapabilities dc = new DesiredCapabilities();

        dc.setCapability(MobileCapabilityType.PLATFORM_VERSION, "13.0");
        dc.setCapability(MobileCapabilityType.PLATFORM_NAME, "iOS");
        dc.setCapability(MobileCapabilityType.AUTOMATION_NAME, "XCUITest");
        dc.setCapability(MobileCapabilityType.DEVICE_NAME, "iPhone 11");
        dc.setCapability(MobileCapabilityType.APP, "https://applitools.bintray.com/Examples/eyes-ios-hello-world.zip");

        IOSDriver<WebElement> driver = new IOSDriver<>(new URL("http://127.0.0.1:4723/wd/hub"), dc);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        Eyes eyes = new Eyes();
        eyes.setApiKey("YOUR_API_KEY");

        eyes.setLogHandler(new StdoutLogHandler(true));
        eyes.setMatchTimeout(1000);

        try {
            eyes.open(driver, "IOSHelloWorldApp", "Demo test");

            eyes.check(Target.window().withName("Open app"));

            driver.findElement(By.xpath("//XCUIElementTypeButton[@name=\"Click me!\"]")).click();

            eyes.check(Target.window().withName("On ClickMe pressed"));

            eyes.check(Target.region(MobileBy.className("XCUIElementTypeImage")).withName("Image region"));

            driver.findElement(By.xpath("//XCUIElementTypeButton[@name=\"Click me!\"]")).click();

            driver.findElementByAccessibilityId("SimulateDiffsCheckbox").click();

            eyes.check(Target.window().withName("Simulate additional difference"));

            driver.findElement(By.xpath("//XCUIElementTypeButton[@name=\"Click me!\"]")).click();

            eyes.check(Target.window().withName("On ClickMe pressed (after difference)"));

            eyes.check(Target.region(MobileBy.className("XCUIElementTypeImage")).withName("Image region (after difference)"));

            eyes.close();
        } finally {
            eyes.abortIfNotClosed();
            driver.quit();
        }
    }
}
