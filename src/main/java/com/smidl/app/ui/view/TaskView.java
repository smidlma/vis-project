package com.smidl.app.ui.view;

import java.util.HashSet;
import java.util.Set;

import com.smidl.app.backend.model.Task;
import com.smidl.app.backend.service.TaskService;
import com.smidl.app.backend.service.UserRepository;
import com.smidl.app.backend.service.UserService;
import com.smidl.app.security.SecurityUtils;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.listbox.ListBox;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "task", layout = MainView.class)
@PageTitle("Task")
@CssImport("./styles/views/about/about-view.css")
public class TaskView extends Div {
    private final UserService userService;
    private final TaskService taskService;

    ListBox<Task> tasklist = new ListBox<>();

    @Autowired
    public TaskView(UserService userService, TaskService taskService) {
        this.userService = userService;
        this.taskService = taskService;
        setId("about-view");
        tasklist.setRenderer(getTaskRenderer());
        add(new H3("My task"));
        add(tasklist);

        reload();
    }

    protected void reload() {
        Set<Task> tasks = new HashSet<>();
        tasks = userService.findByEmail(SecurityUtils.getUsername()).getSolveTasks();
        tasklist.setItems(tasks);

    }

    private ComponentRenderer<Component, Task> getTaskRenderer() {
        return new ComponentRenderer<Component, Task>(task -> {
            WrapperCard card = createBadge(
                    task.getName() + " " + task.getPriority() + "\n" + task.getSolver().getLastName(), new H2(),
                    "primary-text", task.getDeadlineDate() + "\n" + task.getDescription(), "badge");
            card.addClickListener(e -> {
                new TaskDialog(taskService, task.getProject(), userService.findByEmail(SecurityUtils.getUsername()),
                        task.getId(), this).open();
            });
            return card;
        });
    }

    private WrapperCard createBadge(String title, H2 h2, String h2ClassName, String description, String badgeTheme) {
        Span titleSpan = new Span(title);
        titleSpan.getElement().setAttribute("theme", badgeTheme);

        h2.addClassName(h2ClassName);

        Span descriptionSpan = new Span(description);
        descriptionSpan.addClassName("secondary-text");

        return new WrapperCard("wrapper", new Component[] { titleSpan, h2, descriptionSpan }, "card", "space-m");
    }

}
