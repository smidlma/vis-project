package com.smidl.app.ui.view;

import java.util.HashSet;
import java.util.Set;

import com.smidl.app.backend.model.Project;
import com.smidl.app.backend.model.User;
import com.smidl.app.backend.model.UserProject;
import com.smidl.app.backend.service.ProjectService;
import com.smidl.app.backend.service.UserProjectService;
import com.smidl.app.backend.service.UserService;
import com.smidl.app.security.SecurityUtils;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.listbox.ListBox;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "project", layout = MainView.class)
@PageTitle("Project")
@CssImport("./styles/views/about/about-view.css")
public class ProjectView extends Div {

        protected final ProjectService projectService;
        protected final UserService userService;
        protected final UserProjectService userProjectService;

        private ListBox<Project> listBox = new ListBox<>();

        @Autowired
        public ProjectView(ProjectService projectService, UserService userService,
                        UserProjectService userProjectService) {
                this.projectService = projectService;
                this.userService = userService;
                this.userProjectService = userProjectService;

                setId("about-view");

                Button createProjectBtn = new Button("Create project", new Icon(VaadinIcon.PLUS),
                                e -> new ProjectDialog(this).open());

                listBox.setRenderer(getProjectRenderer());
                add(createProjectBtn);
                add(listBox);

                reload();
        }

        public void reload() {
                User user = userService.findByEmail(SecurityUtils.getUsername());
                Set<Project> projects = new HashSet<>();
                for (UserProject p : user.getProjects()) {
                        projects.add(p.getProject());
                }
                listBox.setItems(projects);
        }

        private ComponentRenderer<Component, Project> getProjectRenderer() {
                return new ComponentRenderer<Component, Project>(project -> {
                        WrapperCard card = createBadge(project.getName(), new H2(), "primary-text",
                                        project.getDescription(), "badge");
                        card.addClickListener(e -> {
                                getUI().ifPresent(ui -> ui.navigate("project/" + project.getId()));
                        });
                        return card;
                });
        }

        private WrapperCard createBadge(String title, H2 h2, String h2ClassName, String description,
                        String badgeTheme) {
                Span titleSpan = new Span(title);
                titleSpan.getElement().setAttribute("theme", badgeTheme);

                h2.addClassName(h2ClassName);

                Span descriptionSpan = new Span(description);
                descriptionSpan.addClassName("secondary-text");

                return new WrapperCard("wrapper", new Component[] { titleSpan, h2, descriptionSpan }, "card",
                                "space-m");
        }
}
