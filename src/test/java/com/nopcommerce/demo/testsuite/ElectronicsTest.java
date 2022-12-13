package com.nopcommerce.demo.testsuite;

import com.nopcommerce.demo.pages.*;
import com.nopcommerce.demo.testbase.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ElectronicsTest extends BaseTest {

    String baseUrl = "https://demo.nopcommerce.com/";
    HomePage homePage;
    CellPhonesPage cellPhonesPage;
    NokiaLumiaPage nokiaLumiaPage;
    ShoppingCartPage shoppingCartPage;
    CheckOutAsGuest checkOutAsGuestPage;
    RegisterPage registerPage;
    ShippingMethodCheckoutPage shippingMethodCheckOutPage;
    BillingCheckOutPage billingCheckOutPage;
    PaymentMethodCheckOut paymentMethodCheckOutPage;
    PaymentCheckOut paymentCheckOutPage;

    ConfirmOrderPage confirmOrderPage;
    CompletedCheckoutPage completedCheckOutPage;

    @BeforeMethod(alwaysRun = true)
    public void inIt(){
        homePage = new HomePage();
        cellPhonesPage = new CellPhonesPage();
        nokiaLumiaPage = new NokiaLumiaPage();
        shoppingCartPage = new ShoppingCartPage();
        checkOutAsGuestPage = new CheckOutAsGuest();
        registerPage = new RegisterPage();
        shippingMethodCheckOutPage = new ShippingMethodCheckoutPage();
        billingCheckOutPage = new BillingCheckOutPage();
        paymentMethodCheckOutPage = new PaymentMethodCheckOut();
        paymentCheckOutPage = new PaymentCheckOut();
        confirmOrderPage = new ConfirmOrderPage();
        completedCheckOutPage = new CompletedCheckoutPage();
    }

    @Test(groups = {"sanity","regression"})
    public void verifyUserShouldNavigateToCellPhonesPageSuccessfully() {
        homePage.mouseHoverOnElectronics();
        homePage.mouseHoverOnCellPhoneAndClick();
        Assert.assertEquals(cellPhonesPage.verifyCellPhoneText(), "Cell phones", "Invalid Data");
    }

    @Test(groups = {"smoke","regression"})
    public void verifyThatTheProductAddedSuccessfullyAndPlaceOrderSuccessfully() throws InterruptedException {
        homePage.mouseHoverOnElectronics();
        homePage.mouseHoverOnCellPhoneAndClick();
        Assert.assertEquals(cellPhonesPage.verifyCellPhoneText(), "Cell phones", "Invalid Data");
        cellPhonesPage.clickOnList();
        Thread.sleep(2000);
        cellPhonesPage.clickOnNokia();

        Assert.assertEquals(nokiaLumiaPage.verifyNokiaLumiaText(), "Nokia Lumia 1020", "Invalid Text");
        Assert.assertEquals(nokiaLumiaPage.verifyPrice(), "$349.00", "Invalid Price");

        nokiaLumiaPage.updateNokiaQuantity("2");
        nokiaLumiaPage.nokiaAddToCartButton();
        Assert.assertEquals(nokiaLumiaPage.verifyProductAddedToTheCartMessage(), "The product has been added to your shopping cart", "Invalid Text");
        nokiaLumiaPage.clickOnCrossButton();
        Thread.sleep(2000);
        nokiaLumiaPage.clickOnShoppingCart();

        Assert.assertEquals(shoppingCartPage.verifyTheShoppingCartText(),"Shopping cart","Invalid Text");
        Assert.assertEquals(shoppingCartPage.verifyProductQuantity(), "2", "Quantity is not matching" );
        Assert.assertEquals(shoppingCartPage.verifyTotalPrice(),"$698.00","Priced is not matched");

        shoppingCartPage.clickOnTermsOfService();
        shoppingCartPage.clickOnCheckOutButton();

        Assert.assertEquals(checkOutAsGuestPage.VerifytheWelComeText(),"Welcome, Please Sign In!","Invalid data");
        checkOutAsGuestPage.clickOnARegisterTab();
        Assert.assertEquals(registerPage.verifyRegisterText(),"Register","Invalid Text");

        registerPage.enterFirstName("Shane");
        registerPage.enterLastName("James");
        registerPage.email("shane10@example.com");
        registerPage.enterPassword("shane12345");
        registerPage.enterConfirmPassword("shane12345");
        registerPage.clickOnRegisterButton();

        Assert.assertEquals(registerPage.verifyRegistration(),"Your registration completed","Invalid Text");
        registerPage.clickOnContinue();
        shoppingCartPage.clickOnCheckOutButton();

        billingCheckOutPage.selectCountryFromDropDown("233");
        billingCheckOutPage.selectStateFromDropDown("0");
        billingCheckOutPage.enterBillingCity("Rugby");
        billingCheckOutPage.enterBillingAddress("Proctor Close");
        billingCheckOutPage.enterBillingZipcode("CV21GG");
        billingCheckOutPage.enterBillingPhoneNumber("9858552255");
        registerPage.clickOnContinueTab();
        shippingMethodCheckOutPage.clickOn2ndDayAir();
        shippingMethodCheckOutPage.clickOnContinue();
        paymentMethodCheckOutPage.clickOnCreditCard();
        paymentMethodCheckOutPage.clickOnContinue();

        paymentCheckOutPage.selectCardTypeFromDropDown("visa");
        paymentCheckOutPage.enterCardHolderName("Shane James");
        paymentCheckOutPage.enterCardNumber("4222222222222");
        paymentCheckOutPage.selectExpiryMonthFromDropDown("10");
        paymentCheckOutPage.selectExpiryYearFromDropDown("2024");
        paymentCheckOutPage.enterCardCode("123");
        paymentCheckOutPage.clickOnContinueButton();

        Assert.assertEquals(confirmOrderPage.VerifyCreditCardPaymentMethod(),"Credit Card","Invalid Text");
        Assert.assertEquals(confirmOrderPage.VerifyShippingMethod2ndDayAir(),"2nd Day Air","Invalid Text");
        Assert.assertEquals(confirmOrderPage.VerifytheNokiaTotalPrice(),"$698.00","Invalid Text");
        confirmOrderPage.clickOnConFirm();

        Assert.assertEquals(completedCheckOutPage.verifyThankYouText(),"Thank you","Invalid Text");
        Assert.assertEquals(completedCheckOutPage.verifySucessOrderText(),"Your order has been successfully processed!","Text isnot Display");
        completedCheckOutPage.clickOnContinue();

        Assert.assertEquals(homePage.verifyWelComeText(),"Welcome to our store","Invalid Text");
        homePage.clickOnLogOut();

        String currentUrl = driver.getCurrentUrl();
        Assert.assertEquals(baseUrl,currentUrl,"Invalid URL");
    }
}
