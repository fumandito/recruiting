package it.f2informatica.test.services.domain.user;

import it.f2informatica.core.gateway.UserRepositoryGateway;
import it.f2informatica.core.model.UpdatePasswordModel;
import it.f2informatica.core.services.PasswordUpdaterService;
import it.f2informatica.core.services.PasswordUpdaterServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static it.f2informatica.test.services.builder.UpdatePasswordRequestBuilder.updatePasswordRequest;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PasswordUpdaterServiceTest {

  @Mock
  private UserRepositoryGateway userRepositoryGateway;

  @InjectMocks
  private PasswordUpdaterService passwordUpdaterService = new PasswordUpdaterServiceImpl();

  @Test
  public void updatePasswordTest() {
    UpdatePasswordModel updatePasswordModel = updatePasswordRequest().build();
    userRepositoryGateway.updatePassword(updatePasswordModel);
    passwordUpdaterService.updatePassword(updatePasswordModel);
  }

}
