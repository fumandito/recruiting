package it.f2informatica.services.domain.consultant;

import it.f2informatica.services.gateway.ConsultantRepositoryGateway;
import it.f2informatica.services.model.ConsultantModel;
import it.f2informatica.services.model.ExperienceModel;
import it.f2informatica.services.model.LanguageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import static it.f2informatica.services.model.builder.ConsultantModelBuilder.consultantModel;
import static it.f2informatica.services.model.builder.ExperienceModelBuilder.experienceModel;
import static it.f2informatica.services.model.builder.LanguageModelBuilder.*;

@Service
public class ConsultantServiceImpl implements ConsultantService {
	private static final String YEAR_MONTH_MILLISECONDS_FORMAT = "yyyyMMSSS";

	@Autowired
	private ConsultantRepositoryGateway consultantRepositoryGateway;

	@Override
	public ConsultantModel buildNewConsultantModel() {
		return consultantModel()
			.withConsultantNo(generateConsultantNumber())
			.withRegistrationDate(Calendar.getInstance().getTime())
			.build();
	}

	@Override
	public ExperienceModel buildNewExperienceModel() {
		return experienceModel().build();
	}

	@Override
	public LanguageModel buildNewLanguageModel() {
		return languageModel().build();
	}

	@Override
	public Page<ConsultantModel> showAllConsultants(Pageable pageable) {
		return consultantRepositoryGateway.findAllConsultants(pageable);
	}

	@Override
	public ConsultantModel registerConsultantMasterData(ConsultantModel consultantModel) {
		return consultantRepositoryGateway.saveMasterData(consultantModel);
	}

	@Override
	public ConsultantModel findConsultantById(String consultantId) {
		return consultantRepositoryGateway.findConsultantById(consultantId);
	}

	@Override
	public String generateConsultantNumber() {
		String uuid = UUID.randomUUID().toString();
		String[] components = uuid.split("-");
		return getTimePrefixFormat() + "-" + components[components.length - 1].toUpperCase();
	}

	private String getTimePrefixFormat() {
		DateFormat dateFormat = new SimpleDateFormat(YEAR_MONTH_MILLISECONDS_FORMAT);
		return dateFormat.format(Calendar.getInstance().getTime());
	}

	@Override
	public boolean addConsultantExperience(ExperienceModel experienceModel, String consultantId) {
		return consultantRepositoryGateway.addConsultantExperience(experienceModel, consultantId);
	}

	@Override
	public List<ExperienceModel> findExperiences(String consultantId) {
		return consultantRepositoryGateway.findExperiencesByConsultantId(consultantId);
	}

	@Override
	public boolean addLanguage(LanguageModel languageModel, String consultantId) {
		return consultantRepositoryGateway.addLanguage(languageModel, consultantId);
	}

	@Override
	public boolean addSkills(String[] skills, String consultantId) {
		return consultantRepositoryGateway.addSkills(skills, consultantId);
	}

}
