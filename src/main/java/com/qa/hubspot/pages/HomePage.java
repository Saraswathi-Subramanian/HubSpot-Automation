package com.qa.hubspot.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.hubspot.base.BasePage;
import com.qa.hubspot.utli.Constants;
import com.qa.hubspot.utli.ElementUtil;

public class HomePage extends BasePage
{
	WebDriver driver;
	ElementUtil elementUtil;
	
	private By header = By.cssSelector("h1.private-header__heading");
	private By accountName = By.className("user-info-name");
	private By settingsIcon = By.id("navSetting");
	private By parentContactsMenu = By.id("nav-primary-contacts-branch");
	private By subContactMenu = By.id("nav-secondary-contacts");
	
	
	public HomePage(WebDriver driver)
	{
		this.driver=driver;
		elementUtil = new ElementUtil(driver);
		
	}
	public String getHomePageTittle()
	{
		return elementUtil.waitForPageTittlePresent(Constants.HOME_PAGE_TITTLE, 10);
	}
	public String getHomePageHeaderValue()
	{
		if(elementUtil.doIsDisplayed(header))
		{
			return elementUtil.doGetText(header);
		}
		return null;
	}
	public String getuseraccountname()
	{
		if(elementUtil.doIsDisplayed(accountName))
		{
			return elementUtil.doGetText(accountName);
		}
		return null;
	}
	
	public boolean isExistsettingsicon()
	{
		return elementUtil.doIsDisplayed(settingsIcon);
	}
	public ContactsPage gotoContactsPage()
	{
		clickonContacts();//encapsulation -- create one private method and call it in another method
		return new ContactsPage(driver);
		
	}
	private void clickonContacts(){
		elementUtil.waitForElementToBeLocated(parentContactsMenu, 10);
		elementUtil.doclick(parentContactsMenu);
		elementUtil.ClickWhenReady(subContactMenu, 10);
		
	}
	
	
}
