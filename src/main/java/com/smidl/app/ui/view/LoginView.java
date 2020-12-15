package com.smidl.app.ui.view;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.smidl.app.backend.model.Project;
import com.smidl.app.backend.model.User;
import com.smidl.app.backend.service.ProjectService;
import com.smidl.app.backend.service.UserRepository;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

@Route
@PageTitle("Login")
@CssImport(value = "./styles/views/helloworld/hello-world-view.css", include = "lumo-badge")
@JsModule("@vaadin/vaadin-lumo-styles/badge.js")
public class LoginView extends LoginOverlay implements AfterNavigationObserver {

        private final UserRepository userRepository;
        private final PasswordEncoder passwordEncoder;
        private final ProjectService projectService;

        @Autowired
        public LoginView(UserRepository userRepository, PasswordEncoder passwordEncoder,
                        ProjectService projectService) {
                this.userRepository = userRepository;
                this.projectService = projectService;
                this.passwordEncoder = passwordEncoder;

                LoginI18n i18n = LoginI18n.createDefault();
                i18n.setHeader(new LoginI18n.Header());
                i18n.getHeader().setTitle("WorkFlow");
                i18n.getHeader().setDescription("Welcome back!");
                i18n.setAdditionalInformation(null);
                i18n.setForm(new LoginI18n.Form());
                i18n.getForm().setSubmit("Sign in");
                i18n.getForm().setTitle("Sign in");
                i18n.getForm().setUsername("Email");
                i18n.getForm().setPassword("Password");
                setI18n(i18n);
                setForgotPasswordButtonVisible(false);
                setAction("login");

                // Button createUserBtn = new Button("Create user", e -> {
                //         generateData();
                // });
                // setTitle(createUserBtn);

                setOpened(true);

        }

        @Override
        public void afterNavigation(AfterNavigationEvent event) {
                setError(event.getLocation().getQueryParameters().getParameters().containsKey("error"));
        }

        private User createUser(String email, String firstName, String lastName, String passwordHash, String role,
                        boolean locked) {
                User user = new User();
                user.setEmail(email);
                user.setFirstName(firstName);
                user.setLastName(lastName);
                user.setPasswordHash(passwordHash);
                user.setRole(role);
                user.setLocked(locked);
                return user;
        }

        private void generateData() {
                userRepository.save(createUser("smidl@gmail.com", "Martin", "Smidl", passwordEncoder.encode("password"),
                                "user", false));
                userRepository.save(createUser("graham@gmail.com", "Leanne", "Graham",
                                passwordEncoder.encode("password"), "user", false));
                userRepository.save(createUser("light@gmail.com", "Kulas", "Light", passwordEncoder.encode("password"),
                                "user", false));
                userRepository.save(createUser("howell@gmail.com", "Ervin", "Howell",
                                passwordEncoder.encode("password"), "user", false));
                userRepository.save(createUser("lebsack@gmail.com", "Patricia", "Lebsack",
                                passwordEncoder.encode("password"), "user", false));
                userRepository.save(createUser("dietrich@gmail.com", "Chelsey ", "Dietrich",
                                passwordEncoder.encode("password"), "user", false));
                // projectService.save(createProject("Subin", "Open-source motivating data-warehouse", "2020-05-11", 1));
                // projectService.save(createProject("Tempsoft", "Digitized neutral capacity", "2020-08-04", 1));
                // projectService.save(createProject("Otcom", "Pre-emptive holistic standardization", "2020-09-27", 1));
                // projectService.save(createProject("Prodder", "Implemented tertiary artificial intelligence",
                //                 "2020-10-30", 1));

        }

        private Project createProject(String name, String desc, String date, Integer manager) {
                Project p = new Project();
                p.setName(name);
                p.setDescription(desc);
                Date d = new Date(date);
                p.setCreatedAt(d);
                return p;
        }

}
