package com.smidl.app.ui.view;

import java.util.NoSuchElementException;
import java.util.Set;

import com.smidl.app.backend.model.Project;
import com.smidl.app.backend.model.Task;
import com.smidl.app.backend.model.TaskState;
import com.smidl.app.backend.model.User;
import com.smidl.app.backend.service.ProjectService;
import com.smidl.app.backend.service.TaskService;
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
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "project", layout = MainView.class)
@PageTitle("Project")
@CssImport("./styles/views/about/about-view.css")
public class ProjectDetail extends Div implements HasUrlParameter<Long> {

    protected final ProjectService projectService;
    protected final TaskService taskService;
    protected final UserService userService;
    protected User user;
    protected Project project;

    private ListBox<Task> newTasks = new ListBox<>();
    private ListBox<Task> runningTasks = new ListBox<>();

    private Button createTaskBtn = new Button("Create task", new Icon(VaadinIcon.PLUS));
    private Button addTeamMemberBtn = new Button("Add team member", new Icon(VaadinIcon.USERS));

    @Autowired
    public ProjectDetail(ProjectService projectService, TaskService taskService, UserService userService) {
        this.projectService = projectService;
        this.taskService = taskService;
        this.userService = userService;
        this.user = userService.findByEmail(SecurityUtils.getUsername());

        setId("about-view");

        add(createTaskBtn, addTeamMemberBtn);

        addTeamMemberBtn.addClickListener(e -> {
            new TeamMemberDialog(this).open();
        });

        add(new HorizontalLayout(new VerticalLayout(new H3("New"), newTasks),
                new VerticalLayout(new H3("Running"), runningTasks)));
    }

    private void init() {
        newTasks.setRenderer(getTaskRenderer());
        runningTasks.setRenderer(getTaskRenderer());
    }

    protected void reload() {
        Set<Task> tasks = projectService.findById(project.getId()).getTasks();
        newTasks.setItems(tasks.stream().filter(t -> t.getState().equals(TaskState.NEW)));
        runningTasks.setItems(tasks.stream().filter(t -> t.getState().equals(TaskState.RUNNING)));
    }

    private ComponentRenderer<Component, Task> getTaskRenderer() {
        return new ComponentRenderer<Component, Task>(task -> {
            WrapperCard card = createBadge(
                    task.getName() + " " + task.getPriority() + "\n" + task.getSolver().getLastName(), new H2(),
                    "primary-text", task.getDeadlineDate() + "\n" + task.getDescription(), "badge");
            card.addClickListener(e -> {
                new TaskDialog(taskService, project, user, task.getId(), this).open();
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

    private void loadProjectDetail(Long id) {
        try {

            project = projectService.findById(id);
            createTaskBtn.addClickListener(e -> {
                new TaskDialog(taskService, project, user, null, this).open();
            });
        } catch (NoSuchElementException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setParameter(BeforeEvent event, Long parameter) {
        loadProjectDetail(parameter);
        init();
        reload();
    }

}
