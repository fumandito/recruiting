package it.f2informatica.acceptance.usecase;

import it.f2informatica.acceptance.page.HomePage;
import it.f2informatica.acceptance.page.login.LoginPage;
import org.junit.After;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class UC_01_PerformLogin extends UseCaseTest {

	@After
	public void logout() {
		navigator.logOut();
	}

	@Test
	public void loginAsAdmin() {
		LoginPage loginPage = navigator.goToLoginPage();
		loginPage.typeUsername("admin");
		loginPage.typePassword("admin");
		HomePage homePage = loginPage.clickOnLoginSuccessButton();
		assertThat("admin").isEqualTo(homePage.getUserLoggedIn());
	}

	@Test
	public void loginExpectingFailure() {
		LoginPage loginPage = navigator.goToLoginPage();
		loginPage.typeUsername("unknown_user");
		loginPage.typePassword("unknown_password");
		LoginPage failedLoginPage = loginPage.clickOnLoginFailureButton();
		assertThat(failedLoginPage.isLoginErrorMessagePresent()).isTrue();
	}

}
