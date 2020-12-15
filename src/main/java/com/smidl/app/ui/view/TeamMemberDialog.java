package com.smidl.app.ui.view;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.flowingcode.vaadin.addons.twincolgrid.TwinColGrid;
import com.smidl.app.backend.model.User;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class TeamMemberDialog extends Dialog {
    private final ProjectDetail view;

    final TwinColGrid<User> twinColGrid = new TwinColGrid<User>();

    Button saveBtn = new Button("Save", new Icon(VaadinIcon.CHECK), e -> save());
    Button cancelBtn = new Button("Cancel", new Icon(VaadinIcon.CLOSE), e -> close());

    public TeamMemberDialog(ProjectDetail view) {
        this.view = view;

        add(createTitle());
        createGrid();
        add(createButtonLayout());

    }

    private void createGrid() {

        // Drag and drop
        twinColGrid.withLeftColumnCaption("Available users").withRightColumnCaption("Team members").withoutAddAllButton()
                .addFilterableColumn(User::getEmail, "Email", "Email filter", false).withSizeFull()
                .withDragAndDropSupport().selectRowOnClick();

        // twinColGrid.setValue(selectedBooks);

        // final Label countLabel = new Label("Selected items in left grid: 0");

        Set<User> teamMembers = new HashSet<>();
        view.project.getUsers().forEach(up -> {
            teamMembers.add(up.getUser());
            System.out.println(up.getUser().getEmail());
        });
        List<User> allUsers = view.userService.findAll();
        allUsers.removeAll(teamMembers);
        twinColGrid.setItems(allUsers);
        twinColGrid.setValue(teamMembers);

        twinColGrid.setMaxHeight("60vh");
        add(twinColGrid);

        setSizeFull();
        setMaxWidth("1000px");
        setMaxHeight("80vh");
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
        return new H3("Choose team members");
    }

    private void save() {
        view.projectService.addTeamMember(twinColGrid.getValue(), view.project);
        close();
    }

}
