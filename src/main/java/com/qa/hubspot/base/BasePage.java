package com.qa.hubspot.base;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.qa.hubspot.utli.OptionsManager;

import io.github.bonigarcia.wdm.WebDriverManager;
public class BasePage {
	
	public WebDriver driver;
	public Properties prop;
	public OptionsManager optionsManager;
	public static String highlight;
	public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<WebDriver>();
	/**
	 * This method is used to launch the browser and initialize the WebDriver
	 * @param browserName
	 * @return this method will return WebDriver
	 */
	
	public WebDriver init_driver(Properties prop)
	{
		String browserName = prop.getProperty("browser");
		
		System.out.println("Browser name is: "+ browserName);
		
		highlight = prop.getProperty("highlight");
		
		optionsManager = new OptionsManager(prop);
		
		if(browserName.equals("chrome"))
		{
			WebDriverManager.chromedriver().setup();
			
			if(Boolean.parseBoolean(prop.getProperty("remote")))// remote execution
			{
				init_remoteDriver(browserName);
			}
			else//normal execution
			{
				tlDriver.set(new ChromeDriver(optionsManager.getChromeOptions()));
				
			}
			//driver =new ChromeDriver(optionsManager.getChromeOptions());
		}
		//else if(browserName.equals("firefox"))
				//{
					//WebDriverManager.firefoxdriver().setup();
					//tlDriver.set(new FirefoxDriver(optionsManager.getFirefoxOptions()));
					//driver =new FirefoxDriver(optionsManager.getFirefoxOptions());
				//}
		else
		{
			System.out.println("Please pass correct browser:"+ browserName);
		}
		
		getDriver().manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		getDriver().manage().deleteAllCookies();
		getDriver().manage().window().maximize();
		getDriver().get(prop.getProperty("url"));
		return getDriver();
}

	/**
	 * This method will define the desired Capabilities and will initialize the
	 * driver with capabilities.This method will send the request to the GRID - HUB.
	 * @param browserName
	 */
	public void init_remoteDriver(String browserName){
		if(browserName.equals("chrome")){
			DesiredCapabilities cap = DesiredCapabilities.chrome();
			cap.setCapability(ChromeOptions.CAPABILITY, optionsManager.getChromeOptions());
			cap.setCapability("version", "85.0");
			cap.setCapability("enableVNC", true);
			try {
				tlDriver.set(new RemoteWebDriver(new URL(prop.getProperty("huburl")),cap));
			} catch (MalformedURLException e) {
				
				e.printStackTrace();
			}
		}
		else if(browserName.equals("firefox")){
			DesiredCapabilities cap = DesiredCapabilities.firefox();
			cap.setCapability(FirefoxOptions.FIREFOX_OPTIONS, optionsManager.getFirefoxOptions());
			try {
				tlDriver.set(new RemoteWebDriver(new URL(prop.getProperty("huburl")),cap));
			} catch (MalformedURLException e) {
				
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * getDriver using ThreadLocal
	 * @return
	 */
	public static synchronized WebDriver getDriver(){
		return tlDriver.get();
	}
	
	/**
	 * This method is used to initialize Properties from config file.
	 * @return Properties prop object
	 */
	
	
	public  Properties init_prop()
	{
		
		
		prop = new Properties();
		FileInputStream ip;
		try {
			ip = new FileInputStream("./src/main/java/com/qa/hubspot/config/config.properties");
			prop.load(ip);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return prop;
	}
	/**
	 * take screenshot
	 */
	public String getScreenshot() {
		File src = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
		String path = System.getProperty("user.dir") + "/screenshots/" + System.currentTimeMillis() + ".png";
		File destination = new File(path);
		try {
			FileUtils.copyFile(src, destination);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return path;
	}
}














