package com.smidl.app.ui.view;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.smidl.app.backend.service.UserService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.Configuration;
import com.vaadin.flow.component.charts.model.Crosshair;
import com.vaadin.flow.component.charts.model.ListSeries;
import com.vaadin.flow.component.charts.model.XAxis;
import com.vaadin.flow.component.charts.model.YAxis;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "dashboard", layout = MainView.class)
@PageTitle("Dashboard")
@CssImport(value = "./styles/views/helloworld/hello-world-view.css", include = "lumo-badge")
@JsModule("@vaadin/vaadin-lumo-styles/badge.js")
@RouteAlias(value = "", layout = MainView.class)
public class DashboardView extends Div implements AfterNavigationObserver {

        private final UserService userService;
        private H2 taskCountH2 = new H2();
        private H2 projectCountH2 = new H2();

        @Autowired
        public DashboardView(UserService userService) {
                this.userService = userService;
                setId("dashboard-view");

                add(createDashLayout());

        }

        private Component createDashLayout() {
                HorizontalLayout layout = new HorizontalLayout();
                layout.add(createBadge("My tasks", taskCountH2, "primary-text", "Current assigned tasks", "badge"),
                                createBadge("Projects", projectCountH2, "success-text", "Created projects",
                                                "badge success"));
                layout.addClassName("dashboard");
                return layout;

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

        @Override
        public void afterNavigation(AfterNavigationEvent event) {
                taskCountH2.setText(userService.taskCount().toString());
                projectCountH2.setText(userService.projectCount().toString());
        }
}
