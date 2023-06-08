package selenium;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.util.Date;

public class CRUDItem {
    ChromeDriver chrome;

    @BeforeEach
    public void openBrowser(){
        System.setProperty("webdriver.chrome.driver","src/test/resources/driver/chromedriver.exe");
        chrome = new ChromeDriver();

        // implicit wait --> tiempo de espera generico para todos los controles
        chrome.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        chrome.manage().window().maximize();
        chrome.get("http://todo.ly/");
    }

    @AfterEach
    public void closeBrowser(){
        chrome.quit();
    }

    @Test
    public void verifyCreateItem() throws InterruptedException {
        // click login button
        chrome.findElement(By.xpath("//img[@src='/Images/design/pagelogin.png']")).click();
        // type email in email txtbox
        chrome.findElement(By.id("ctl00_MainContent_LoginControl1_TextBoxEmail")).sendKeys("nikita27111@hotmail.com");
        // type pwd in password txtbox
        chrome.findElement(By.id("ctl00_MainContent_LoginControl1_TextBoxPassword")).sendKeys("estoesesparta");
        // click login button
        chrome.findElement(By.id("ctl00_MainContent_LoginControl1_ButtonLogin")).click();





        // Crear un Projecto
        String nameProject = "Nicky"+new Date().getTime();
        // click AddNewProject
        chrome.findElement(By.xpath("//td[text()='Add New Project']")).click();
        // set name project
        chrome.findElement(By.id("NewProjNameInput")).sendKeys(nameProject);
        // click Add project
        chrome.findElement(By.id("NewProjNameButton")).click();
        Thread.sleep(2000);
        // verification 1

        Assertions.assertTrue(chrome.findElements(By.xpath("(//li//td[text()='"+nameProject+"'])[last()]")).size() == 1 ,
                "ERROR, the project was not created");
        // verification 2
        String actualResult=chrome.findElement(By.id("CurrentProjectTitle")).getText();
        String expectedResult =nameProject;
        Assertions.assertEquals(expectedResult,actualResult,"ERROR, the project was not created");


        String itemName="Item"+new Date().getTime();

        // click on project name
        chrome.findElement(By.xpath("(//li//td[text()='"+nameProject+"'])[last()]")).click();
        // type name in item name txtbox
        chrome.findElement(By.id("NewItemContentInput")).sendKeys(itemName);
        chrome.findElement(By.id("NewItemAddButton")).click();

        Assertions.assertTrue(chrome.findElements(By.xpath("(//li//td//div[text()='"+itemName+"' and @class=\"ItemContentDiv\"])[last()]")).size() == 1 ,
                "ERROR, the item was not created");

        String newItemname="NewItem";

        //update item check
        chrome.findElement(By.xpath("(//div[@id=\"ItemListPlaceHolder\"]//li//td//input[@id=\"ItemCheckBox\"])[last()]")).click();
        Thread.sleep(1500);
        chrome.findElement(By.xpath("(//div[@id=\"DoneItemListPlaceholder\"]//li//td//input[@id=\"ItemCheckBox\"])[last()]")).click();
        Thread.sleep(2500);

        //update item name



        chrome.findElement(By.xpath("(//li//td//div[text()='"+itemName+"' and @class=\"ItemContentDiv\"])[last()]")).click();
        Thread.sleep(1000);
        chrome.findElement(By.xpath("(//div[@id=\"ItemListPlaceHolder\"]//img[@style=\"display: inline;\"])[last()]")).click();
        Thread.sleep(1000);
        chrome.findElement(By.xpath("//ul[contains(@style,'block')]//a[text()='Edit']")).click();
        Thread.sleep(2000);
        chrome.findElement(By.xpath("(//div[@class=\"ItemContentDiv UnderEditingItem\"]//textarea[@id=\"ItemEditTextbox\"])[last()]")).clear();
        chrome.findElement(By.xpath("(//div[@class=\"ItemContentDiv UnderEditingItem\"]//textarea[@id=\"ItemEditTextbox\"])[last()]")).sendKeys(newItemname);

        // chrome.findElement(By.xpath("(//div[@class=\"ItemContentDiv UnderEditingItem\"]//img[@id=\"ItemEditSubmit\"])[last()]")).click();
        //Tiene el bug de no guardar el update con el boton de Save. Se debe usar el ENTER en el textarea

        chrome.findElement(By.xpath("(//div[@class=\"ItemContentDiv UnderEditingItem\"]//textarea[@id=\"ItemEditTextbox\"])[last()]")).sendKeys(Keys.ENTER);
        Thread.sleep(3000);


        Assertions.assertTrue(chrome.findElements(By.xpath("(//li//td//div[text()='"+newItemname+"' and @class=\"ItemContentDiv\"])[last()]")).size() == 1 ,
                "ERROR, the item was not updated");

        //Delete item
        chrome.findElement(By.xpath("(//li//td//div[text()='"+newItemname+"' and @class=\"ItemContentDiv\"])[last()]")).click();
        Thread.sleep(1000);
        chrome.findElement(By.xpath("(//div[@id=\"ItemListPlaceHolder\"]//img[@style=\"display: inline;\"])[last()]")).click();
        Thread.sleep(1000);
        chrome.findElement(By.xpath("//ul[contains(@style,'block')]//a[text()='Delete']")).click();
        Thread.sleep(2000);

        Assertions.assertTrue(chrome.findElements(By.xpath("(//li//td//div[text()='"+newItemname+"' and @class=\"ItemContentDiv\"])[last()]")).size() == 0,
                "ERROR, the project was not deleted");
    }
}
