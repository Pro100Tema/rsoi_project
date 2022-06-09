package com.example.front.view;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.router.RouterLink;

public class MainLayout extends AppLayout {
    public MainLayout() {
        createHeader();
    }
    private void createHeader() {
        Button mainButton = new Button("Главное меню");
        mainButton.addThemeVariants(ButtonVariant.LUMO_CONTRAST);

        Icon carIcon = new Icon(VaadinIcon.NURSE);
        Button carButton = new Button("Арендовать машину", carIcon);
        carButton.addThemeVariants(ButtonVariant.LUMO_CONTRAST);

        Icon phoneIcon = new Icon("lumo", "phone");
        Button contactButton = new Button("О нас", phoneIcon);
        contactButton.addThemeVariants(ButtonVariant.LUMO_CONTRAST);

        Icon authIcon = new Icon(VaadinIcon.NURSE);
        Button authButton = new Button("Войти", authIcon);
        authButton.addThemeVariants(ButtonVariant.LUMO_CONTRAST);

        Icon userIcon = new Icon(VaadinIcon.USER);
        Button userButton = new Button("Пользователь", userIcon);
        userButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        RouterLink mainRouterLink = new RouterLink("", MainView.class);
        RouterLink carRouterLink = new RouterLink("", CarView.class);
        //RouterLink contactRouterLink = new RouterLink("", ContactView.class);
        RouterLink authRouterLink = new RouterLink("", LoginView.class);
        RouterLink userRouterLink = new RouterLink("", UserView.class);

        //contactRouterLink.getElement().appendChild(contactButton.getElement());
        mainRouterLink.getElement().appendChild(mainButton.getElement());
        authRouterLink.getElement().appendChild(authButton.getElement());
        carRouterLink.getElement().appendChild(carButton.getElement());
        userRouterLink.getElement().appendChild(userButton.getElement());

        addToNavbar(mainRouterLink);
        addToNavbar(carRouterLink);
        //addToNavbar(contactRouterLink);
        addToNavbar(authRouterLink);
        addToNavbar(userRouterLink);
    }
}