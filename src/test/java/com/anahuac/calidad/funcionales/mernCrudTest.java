package com.anahuac.calidad.funcionales;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

public class mernCrudTest {
    private WebDriver driver;
    private String baseUrl;
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();
    JavascriptExecutor js;

    @Before
    public void setUp() throws Exception {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        js = (JavascriptExecutor) driver;
    }

    @Test
    public void addTest() throws Exception {
        // en vez de crear o deletear un nuevo mail, lo que hago es crear un numero random, esto hace que no sea tan
        // inconveniente la creacion de esto.
        int numRandom = new Random().nextInt();

        driver.get("https://mern-crud.herokuapp.com/");
        driver.findElement(By.xpath("//div[@id='root']/div/div[2]/button")).click();
        driver.findElement(By.name("name")).click();
        driver.findElement(By.name("name")).clear();
        driver.findElement(By.name("name")).sendKeys("otto montoya");
        driver.findElement(By.name("email")).clear();

        // Aqui agrego el numRandom
        driver.findElement(By.name("email")).sendKeys("ottomontoya" + String.valueOf(numRandom) +"@gmail.com");

        driver.findElement(By.name("age")).clear();
        driver.findElement(By.name("age")).sendKeys("21");
        driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Gender'])[2]/following::div[1]")).click();
        driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Male'])[1]/following::div[2]")).click();
        driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Woah!'])[1]/following::button[1]")).click();

        wait(3);

        String tag = driver.findElement(By.xpath("/html/body/div[2]/div/div[2]/form/div[4]/div/div")).getText();
        assertThat("Nice one!", is(tag));

        //driver.findElement(By.xpath("//i")).click();
    }

    @Test
    public void readTest() throws Exception{
        driver.get("https://mern-crud.herokuapp.com/");
        String name = driver.findElement(By.xpath("/html/body/div[1]/div/div[2]/table/tbody/tr[1]/td[1]")).getText();

        wait(2);

        assertThat("Otto Montoya", is(name));
    }

    @Test
    public void updateTest() throws Exception{
        int numRandom = new Random().nextInt();

        driver.get("https://mern-crud.herokuapp.com/");
        driver.findElement(By.xpath("/html/body/div[1]/div/div[2]/table/tbody/tr[1]/td[5]/button[1]")).click();
        wait(3);
        driver.findElement(By.name("name")).click();
        driver.findElement(By.name("name")).clear();
        driver.findElement(By.name("name")).sendKeys("otto montoya");
        driver.findElement(By.name("email")).clear();
        driver.findElement(By.name("email")).sendKeys("renecalderon" + String.valueOf(numRandom) +"@hotmail.com");
        driver.findElement(By.name("age")).clear();
        driver.findElement(By.name("age")).sendKeys("15");
        driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Gender'])[2]/following::div[1]")).click();
        driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Male'])[1]/following::div[2]")).click();
        driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Woah!'])[1]/following::button[1]")).click();
        wait(3);
        String tag = driver.findElement(By.xpath("/html/body/div[2]/div/div[2]/form/div[4]/div/div")).getText();
        assertThat("Nice!", is(tag));
    }



    @Test
    public void deleteTest() throws Exception{
        driver.get("https://mern-crud.herokuapp.com/");

        String email = driver.findElement(By.xpath("/html/body/div[1]/div/div[2]/table/tbody/tr[1]/td[2]")).getText();
        wait(3);
        driver.findElement(By.xpath("/html/body/div[1]/div/div[2]/table/tbody/tr[1]/td[5]/button[2]")).click();
        wait(3);
        driver.findElement(By.xpath("//button[@class='ui red button']")).click();
        wait(3);

        String emailAfter = driver.findElement(By.xpath("/html/body/div[1]/div/div[2]/table/tbody/tr[1]/td[2]")).getText();
        assertThat(emailAfter, is(not(email)));
    }

    public void wait(int sec) {
        try {
            Thread.currentThread().sleep(sec * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
    }

    private boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    private boolean isAlertPresent() {
        try {
            driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }

    private String closeAlertAndGetItsText() {
        try {
            Alert alert = driver.switchTo().alert();
            String alertText = alert.getText();
            if (acceptNextAlert) {
                alert.accept();
            } else {
                alert.dismiss();
            }
            return alertText;
        } finally {
            acceptNextAlert = true;
        }
    }
}
