package it.f2informatica.webapp.controller;

import it.f2informatica.services.user.PasswordUpdaterService;
import it.f2informatica.services.user.UserService;
import it.f2informatica.services.model.RoleModel;
import it.f2informatica.services.model.UserModel;
import it.f2informatica.services.requests.UpdatePasswordRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private PasswordUpdaterService passwordUpdaterService;

	@Deprecated
	@ModelAttribute("roles")
	public Iterable<RoleModel> loadRoles() {
		return userService.loadRoles();
	}

	@Deprecated
	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String createNewUser(ModelMap model) {
		model.addAttribute("userModel", userService.buildEmptyUserModel());
		return "user/createNewUser";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveUser(@ModelAttribute("userModel") UserModel userModel) {
		userService.saveUser(userModel);
		return "redirect:/users";
	}

	@RequestMapping(value = "/edit/{userId}", method = RequestMethod.GET)
	public String editUser(@PathVariable String userId, ModelMap model) {
		model.addAttribute("userModel", userService.findUserById(userId));
		return "user/userEdit";
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String updateUser(@ModelAttribute("userModel") UserModel userModel) {
		userService.updateUser(userModel);
		return "redirect:/users";
	}

	@RequestMapping(value = "/delete/{userId}", method = RequestMethod.GET)
	public String deleteUser(@PathVariable String userId) {
		userService.deleteUser(userId);
		return "redirect:/users";
	}

	@RequestMapping(value = "/changePassword/{userId}", method = RequestMethod.GET)
	public String changePasswordForm(@PathVariable String userId, ModelMap model) {
		model.addAttribute("changePasswordModel", passwordUpdaterService.prepareUpdatePasswordRequest(userId));
		return "user/changePasswordForm";
	}

	@RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
	public String updatePassword(@ModelAttribute("changePasswordModel") UpdatePasswordRequest request) {
		passwordUpdaterService.updatePassword(request);
		return "redirect:/users";
	}

	@RequestMapping(value = "/verifyCurrentPassword", method = RequestMethod.POST)
	@ResponseBody
	public String isCurrentPasswordValid(@RequestParam("userId") String userId, @RequestParam("currentPwd") String currentPwd) {
		return String.valueOf(passwordUpdaterService.isCurrentPasswordValid(userId, currentPwd));
	}

}
