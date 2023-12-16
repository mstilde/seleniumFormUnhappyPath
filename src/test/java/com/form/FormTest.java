package com.form;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class FormTest {
    
    private WebDriver driver;
    private DriverFactory setUp;
    private Main chrome;

    @BeforeEach
    public void setUpChrome() {
        setUp = new DriverFactory(driver);
        driver = setUp.initiateDriver();
        chrome = new Main(driver);
        chrome.implicitlyWait(10);
    }

    @AfterEach
    public void closeChrome() {
        driver.quit();
    }

    // SF-29 - TC-1 - Verify that you can not submit data with invalid formats - Empty Name
    @Test
    public void unhappyPath_EmptyName() {

        chrome.navigateToForm();
        String titleForm = driver.getTitle();

        chrome.sendStrings(By.id("email"), "example@example.com");
        chrome.sendStrings(By.id("number"), "18");

        chrome.click(By.id("submit"));

        String newPageTitle = driver.getTitle();

        assertEquals(titleForm, newPageTitle);
    }

    // SF-29 - TC-2 - Verify that you can not submit data with invalid formats - Invalid Email Format
    @Test
    public void happyPath_InvalidEmailFormat() {

        chrome.navigateToForm();
        String titleForm = driver.getTitle();

        chrome.sendStrings(By.id("name"), "example");
        chrome.sendStrings(By.id("email"), "@example.com");
        chrome.sendStrings(By.id("number"), "18");

        chrome.click(By.id("submit"));

        String newPageTitle = driver.getTitle();

        assertEquals(titleForm, newPageTitle);
    }
    
    // SF-29 - TC-3 - Verify that you can not submit data with invalid formats - Invalid Age Boundaries
    @ParameterizedTest
    @CsvFileSource(resources = "resources/csv/ageBoundaries.csv", numLinesToSkip = 1)
    void happyPath_ageBoundaries(Integer number) {

        chrome.navigateToForm();
        String titleForm = driver.getTitle();

        chrome.sendStrings(By.id("name"), "example");
        chrome.sendStrings(By.id("email"), "example@example.com");
        chrome.sendStrings(By.id("number"), number.toString());

        chrome.click(By.id("submit"));

        String newPageTitle = driver.getTitle();

        assertEquals(titleForm, newPageTitle);
    }

}
