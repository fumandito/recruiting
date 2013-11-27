package it.f2informatica.acceptance.usecase;

import it.f2informatica.acceptance.page.consultant.ConsultantManagementPage;
import it.f2informatica.acceptance.page.consultant.ConsultantRegistrationPage;
import it.f2informatica.acceptance.page.consultant.ProfileRegistrationPage;
import org.junit.After;
import org.junit.Test;

public class UC_03_ConsultantRegistration extends UseCaseTest {

	@After
	public void logout() {
		navigator.logOut();
	}

	@Test
	public void registerNewConsultant() {
		login();
		ConsultantManagementPage consultantManagementPage = navigator.goToConsultantManagementPage();
		ConsultantRegistrationPage registrationFormPage = consultantManagementPage.consultantRegistrationForm();
		registrationFormPage.selectRegistrationDate();
		registrationFormPage.typeFirstName("Mario");
		registrationFormPage.typeLastName("Rossi");
		registrationFormPage.selectMaleGender();
		registrationFormPage.typeEmail("mario.rossi@tiscali.it");
		registrationFormPage.typeFiscalCode("RSSMRA78H05A089N");
		registrationFormPage.selectBirthDate();
		registrationFormPage.typeBirthCity("Agrigento");
		registrationFormPage.typeBirthCountry("Italy");
		registrationFormPage.typePhoneNumber("0289223344");
		registrationFormPage.typeMobileNumber("3401246559");
		ProfileRegistrationPage profilePage = registrationFormPage.clickOnSaveAndContinueRegisteringProfile();
	}

	private void login() {
		navigator.goToLoginPage().login("admin", "admin");
	}

}