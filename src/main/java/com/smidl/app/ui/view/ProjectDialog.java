package com.smidl.app.ui.view;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.smidl.app.backend.model.Project;
import com.smidl.app.backend.model.User;
import com.smidl.app.backend.model.UserProject;
import com.smidl.app.backend.service.ProjectService;
import com.smidl.app.backend.service.UserService;
import com.smidl.app.security.SecurityUtils;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;

import org.springframework.beans.factory.annotation.Autowired;

public class ProjectDialog extends Dialog {

    private final ProjectView view;

    private TextField nameField = new TextField("Project Name");
    private TextArea descriptionField = new TextArea("Description");
    private Button saveBtn = new Button("Create", new Icon(VaadinIcon.CHECK));
    private Button cancelBtn = new Button("Cancel", new Icon(VaadinIcon.CLOSE));

    private Binder<Project> binder = new Binder<>(Project.class);

    @Autowired
    public ProjectDialog(ProjectView view) {
        this.view = view;

        add(createTitle());
        add(createFormLayout());
        add(createButtonLayout());

        cancelBtn.addClickListener(e -> close());
        saveBtn.addClickListener(e -> save());

    }

    private Component createFormLayout() {

        nameField.setRequiredIndicatorVisible(true);
        descriptionField.setRequiredIndicatorVisible(true);
        binder.forField(nameField).asRequired().bind(Project::getName, Project::setName);
        binder.forField(descriptionField).asRequired().bind(Project::getDescription, Project::setDescription);

        FormLayout formLayout = new FormLayout();
        formLayout.add(nameField, descriptionField);
        return formLayout;
    }

    private Component createButtonLayout() {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.addClassName("button-layout");
        saveBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(saveBtn);
        buttonLayout.add(cancelBtn);
        return buttonLayout;
    }

    private Component createTitle() {
        return new H3("Project information");
    }

    private void save() {
        User user = view.userService.findByEmail(SecurityUtils.getUsername());
        Project p = new Project();
        System.out.println(user.getEmail());
        if (binder.writeBeanIfValid(p)) {
            p.setManager(user);
            p.setCreatedAt(new Date());
            p = view.projectService.save(p);
            UserProject us = new UserProject();
            us.setProject(p);
            us.setUser(user);
            view.userProjectService.save(us);
            view.reload();
            close();
        }
    }

}
