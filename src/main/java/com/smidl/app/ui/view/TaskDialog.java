package com.smidl.app.ui.view;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

import com.smidl.app.backend.model.Priority;
import com.smidl.app.backend.model.Project;
import com.smidl.app.backend.model.Task;
import com.smidl.app.backend.model.TaskState;
import com.smidl.app.backend.model.User;
import com.smidl.app.backend.model.UserProject;
import com.smidl.app.backend.service.TaskService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;

@CssImport("./styles/views/about/about-view.css")
public class TaskDialog extends Dialog {

    private final TaskService taskService;
    private Long taskId;
    private User user;
    private Project project;
    private Task originalTask;
    private Div view = new Div();

    TextField nameField = new TextField("Name");
    TextArea descriptionField = new TextArea("Description");
    ComboBox<Priority> priorityField = new ComboBox<>("Priority");
    NumberField hourPriceField = new NumberField("Price/Hour");
    NumberField hoursField = new NumberField("Hours");
    DatePicker createdDateField = new DatePicker("Created date");
    DatePicker startedDateField = new DatePicker("Started date");
    DatePicker finishedDateField = new DatePicker("Finished date");
    DatePicker deadlineDateField = new DatePicker("Deadline date");
    ComboBox<User> solverField = new ComboBox<>("Solver");
    ComboBox<User> creatorField = new ComboBox<>("Creator");
    ComboBox<TaskState> stateField = new ComboBox<>("State");

    Button saveBtn = new Button("Save", new Icon(VaadinIcon.CHECK));
    Button cancelBtn = new Button("Cancel", new Icon(VaadinIcon.CLOSE), e -> close());

    private Binder<Task> binder = new Binder<>(Task.class);

    public TaskDialog(TaskService taskService, Project project, User user, Long taskId, Div view) {
        this.taskService = taskService;
        this.user = user;
        this.project = project;
        this.taskId = taskId;
        this.view = view;
        setId("about-view");
        setTask(taskId);

        add(createTitle());
        add(createFormLayout());
        add(createButtonLayout());

        saveBtn.addClickListener(e -> save());
    }

    private void setTask(Long taskId) {

        binder.forField(nameField).asRequired().bind(Task::getName, Task::setName);
        binder.forField(descriptionField).asRequired().bind(Task::getDescription, Task::setDescription);
        binder.forField(priorityField).asRequired().bind(Task::getPriority, Task::setPriority);
        binder.forField(hourPriceField).asRequired().bind(Task::getPrice, Task::setPrice);
        binder.forField(createdDateField).asRequired().bind(Task::getCreateDate, Task::setCreateDate);
        binder.forField(deadlineDateField).asRequired().bind(Task::getDeadlineDate, Task::setDeadlineDate);
        binder.forField(creatorField).asRequired().bind(Task::getCreator, Task::setCreator);
        binder.forField(solverField).asRequired().bind(Task::getSolver, Task::setSolver);
        binder.forField(stateField).asRequired().bind(Task::getState, Task::setState);

        Set<User> solvers = new HashSet<>();
        for (UserProject up : project.getUsers()) {
            solvers.add(up.getUser());
        }
        solverField.setItems(solvers);
        solverField.setItemLabelGenerator(User::getEmail);
        stateField.setItems(TaskState.values());
        priorityField.setItems(Priority.values());
        creatorField.setItemLabelGenerator(User::getEmail);
        creatorField.setReadOnly(true);
        if (taskId != null) {
            try {

                binder.forField(startedDateField).bind(Task::getStartDate, Task::setStartDate);
                binder.forField(finishedDateField).bind(Task::getFinishDate, Task::setFinishDate);
                binder.forField(hoursField).bind(Task::getHours, Task::setHours);
                originalTask = taskService.findById(taskId);
                creatorField.setItems(originalTask.getCreator());
                createdDateField.setReadOnly(true);
                binder.setBean(originalTask);
            } catch (NoSuchElementException e) {
                e.printStackTrace();
            }

        } else {
            creatorField.setItems(user);
            creatorField.setValue(user);

            stateField.setValue(TaskState.NEW);
            createdDateField.setValue(LocalDate.now());
            hoursField.setReadOnly(true);

            startedDateField.setReadOnly(true);
            finishedDateField.setReadOnly(true);
            stateField.setReadOnly(true);
        }

    }

    private Component createFormLayout() {
        FormLayout formLayout = new FormLayout();
        formLayout.setMaxWidth("1000px");
        formLayout.add(nameField, stateField, priorityField, creatorField, solverField, createdDateField,
                startedDateField, finishedDateField, deadlineDateField, hourPriceField, hoursField, descriptionField);
        formLayout.setColspan(nameField, 2);
        formLayout.setColspan(descriptionField, 2);
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
        return new H3("Task information");
    }

    private void save() {
        if (taskId != null) {
            if (binder.writeBeanIfValid(originalTask)) {
                taskService.save(originalTask);
                Notification.show("Task updated");
            }
        } else {
            Task t = new Task();
            if (binder.writeBeanIfValid(t)) {
                t.setProject(project);
                taskService.save(t);
                Notification.show("Task created");
            }
        }
        if (view instanceof ProjectDetail) {
            ((ProjectDetail) view).reload();
        } else {
            ((TaskView) view).reload();
        }
        close();
    }
}
