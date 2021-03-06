package com.qa.hubspot.base;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import com.qa.hubspot.pages.ContactsPage;
import com.qa.hubspot.pages.HomePage;
import com.qa.hubspot.pages.LoginPage;

public class BaseTest {
	WebDriver driver;
	protected Properties prop;
	protected BasePage basePage;
	protected LoginPage loginpage;
	protected HomePage homepage;
	protected ContactsPage contactsPage;
	
	@Parameters({"browser"})
	@BeforeTest
	public void SetUp(String browserName)
	{
		basePage = new BasePage();
		prop =basePage.init_prop();
		prop.setProperty("browser",browserName);
		driver = basePage.init_driver(prop);
		loginpage = new LoginPage(driver);
		
	}
	
	
	@AfterTest
	public void tearDown()
	{
		driver.quit();
	}
	

}
