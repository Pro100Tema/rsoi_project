package com.example.front.view;

import com.example.front.dto.AuthResponce;
import com.example.front.service.VaadinService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.button.Button;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Route(value = "login", layout = MainLayout.class)
@PageTitle("Login")
public class LoginView extends VerticalLayout {

    @Autowired
    private VaadinService vaadinService;

    public LoginView() {
        setId("login-view");
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        var username = new TextField("Логин");
        username.setMaxLength(80);
        var password = new PasswordField("Пароль");
        password.setMaxLength(80);
        add(
                new H2("Добро пожаловать!"),
                username,
                password,
                new Button("Войти", event -> {
                    if (username.getValue().isEmpty()) {
                        Notification.show("Введите логин");
                    } else if (password.getValue().isEmpty()) {
                        Notification.show("Введите пароль");
                    } else {
                        ResponseEntity<AuthResponce> response = vaadinService.authenticate(username.getValue(), password.getValue());
                        if (response.getStatusCode().equals(HttpStatus.OK)) {
                            UI.getCurrent().navigate(LoginView.class);
                            UI.getCurrent().getPage().reload();
                            UI.getCurrent().navigate(MainView.class);
//                            UI.getCurrent().getPage().reload();
                        } else if (response.getStatusCode().equals(HttpStatus.NO_CONTENT)) {
                            Notification.show("Неверный логин или пароль!");
                        } else {
                            Notification.show("Сервис недоступен, попробуйте позже");
                        }
                    }
                }),
                new RouterLink("Регистрация", RegistrationView.class)
        );
    }
}
