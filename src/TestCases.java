import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class TestCases {

	WebDriver driver = new ChromeDriver();
	Random rand = new Random();

	@BeforeTest
	public void MySetup() {
		driver.manage().window().maximize();
		int randomInt = rand.nextInt(2);
		String[] myURLS = { "https://global.almosafer.com/ar", "https://global.almosafer.com/en" };

		driver.get(myURLS[randomInt]);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
		if (driver.findElement(By.xpath("/html/body/div[3]/div/div[1]/div/div/div")).isDisplayed()) {
			driver.findElement(By.xpath("/html/body/div[3]/div/div[1]/div/div/div/button[1]")).click();
		}
	}

	@Test(groups = "mid", enabled = true)
	public void CheckLanguage() {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		if (driver.getCurrentUrl().contains("ar")) {
			String ActualLanguage = driver.findElement(By.tagName("html")).getAttribute("lang");
			Assert.assertEquals(ActualLanguage, "ar", "this test checks the Language of the website is Arabic");
		} else {
			String ActualLanguage = driver.findElement(By.tagName("html")).getAttribute("lang");
			Assert.assertEquals(ActualLanguage, "en", "this test checks the Language of the website is English");
		}

	}

	@Test(groups = "high")
	public void DateTest() {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));

		WebElement departureDateElement = driver.findElement(By.xpath(
				"//*[@id=\"uncontrolled-tab-example-tabpane-flights\"]/div/div[2]/div[1]/div/div[3]/div/div/div/div[1]/span[2]"));
		String departureDateText = departureDateElement.getText();

		WebElement returnDateElement = driver.findElement(By.xpath(
				"//*[@id=\"uncontrolled-tab-example-tabpane-flights\"]/div/div[2]/div[1]/div/div[3]/div/div/div/div[2]/span[2]"));
		String returnDateText = returnDateElement.getText();

		LocalDate currentDate = LocalDate.now();
		LocalDate expectedDepartureDate = currentDate.plusDays(1);
		LocalDate expectedReturnDate = currentDate.plusDays(2);

		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd");
		String expectedDepartureDateString = expectedDepartureDate.format(dateFormatter);
		String expectedReturnDateString = expectedReturnDate.format(dateFormatter);

		Assert.assertEquals(departureDateText, expectedDepartureDateString);
		Assert.assertEquals(returnDateText, expectedReturnDateString);

	}

	@Test(groups = "low", enabled = true)
	public void checkCurrency() {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		WebElement currencyElement = driver
				.findElement(By.xpath("//*[@id=\"__next\"]/header/div/div[1]/div[2]/div/div[1]/div/button"));
		String ActualCurrency = currencyElement.getText();
		Assert.assertEquals(ActualCurrency, "SAR", "this test checks the default currency is SAR");
	}

	@Test(groups = "mid", enabled = true)
	public void checkNumber() {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		WebElement ContactNumberElement = driver
				.findElement(By.xpath("//*[@id=\"__next\"]/header/div/div[1]/div[2]/div/a[2]/strong"));
		String ActualNumber = ContactNumberElement.getText();
		Assert.assertEquals(ActualNumber, "+966554400000",
				"this test checks whether the contact number displayed is correct");

	}

	@Test(groups = "high", enabled =true)
	public void ChooseCountry() {
		// this test shows the flow of the website choosing certain countries and
		// checking for hotels as per user's journey

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));

		String[] englishInput = { "Dubai", "Jeddah", "Riyadh" };
		int randomEngInput = rand.nextInt(3);
		String[] arabicInput = { "دبي", "جدة" };
		int randomArabicInput = rand.nextInt(2);

		if (driver.getCurrentUrl().contains("ar")) {

			WebElement HotelTab = driver.findElement(By.xpath("//*[@id=\"uncontrolled-tab-example-tab-hotels\"]"));
			HotelTab.click();
			WebElement SearchField = driver.findElement(By.xpath(
					"//*[@id=\"uncontrolled-tab-example-tabpane-hotels\"]/div/div/div/div[1]/div/div/div/div/input"));
			SearchField.sendKeys(arabicInput[randomArabicInput]);

		} else {
			WebElement HotelTab = driver.findElement(By.xpath("//*[@id=\"uncontrolled-tab-example-tab-hotels\"]"));
			HotelTab.click();
			WebElement SearchField = driver.findElement(By.xpath(
					"//*[@id=\"uncontrolled-tab-example-tabpane-hotels\"]/div/div/div/div[1]/div/div/div/div/input"));

			SearchField.sendKeys(englishInput[randomEngInput]);

		}
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		WebElement selectvistor = driver.findElement(
				By.xpath("//*[@id=\"uncontrolled-tab-example-tabpane-hotels\"]/div/div/div/div[3]/div/select"));
		Select mySelector = new Select(selectvistor);
		int myrandomindex = rand.nextInt(2);
		mySelector.selectByIndex(myrandomindex);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.findElement(By.xpath("//*[@id=\"uncontrolled-tab-example-tabpane-hotels\"]/div/div/div/div[4]/button"))
				.click();

	}

	@Test
	public void CheckSortingTestLowToHigh() {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		if (driver.getCurrentUrl().contains("ar")) {
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
			WebElement lowestPriceButton = driver
					.findElement(By.xpath("//*[@id=\"__next\"]/div[2]/div[1]/div[2]/section[1]/div/button[2]"));
			lowestPriceButton.click();
			Assert.assertEquals(lowestPriceButton.isEnabled(), true,
					"this test checks whether the sortiing button is enabled and wroks");

		} else {
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
			WebElement lowestPriceButton = driver
					.findElement(By.xpath("//*[@id=\"__next\"]/div[2]/div[1]/div[2]/section[1]/div/button[2]"));
			lowestPriceButton.click();
			Assert.assertEquals(lowestPriceButton.isEnabled(), true,
					"this test checks whether the sortiing button is enabled and wroks");

		}

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
		WebElement container = driver.findElement(By.xpath("//*[@id=\"__next\"]/div[2]/div[1]/div[2]"));
		List<WebElement> Prices = container.findElements(By.className("Price__Value"));

		for (int i = 0; i < Prices.size(); i++) {
			String price1 = Prices.get(i).getText().replaceAll("[^\\d]", "");
			String price2 = Prices.get(i + 1).getText().replaceAll("[^\\d]", "");
			int FirstPrice = Integer.parseInt(price1);
			int LastPrice = Integer.parseInt(price2);

			System.out.println(FirstPrice + "  ?  " + LastPrice);
			Assert.assertEquals(FirstPrice <= LastPrice, true,
					"this test checks whether the hotels are sorted in an ascending order");
		}

	}

	@AfterTest
	public void MyAfterTest() {

	}

}
