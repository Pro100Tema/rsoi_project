package com.example.front.view;

import com.example.front.dto.AuthResponce;
import com.example.front.service.VaadinService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Route(value = "register")
@PageTitle("Register")
public class RegistrationView extends VerticalLayout {

    @Autowired
    private VaadinService vaadinService;

    public RegistrationView() {
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        TextField name = new TextField("Имя");
        name.setMaxLength(80);
        TextField surname = new TextField("Фамилия");
        surname.setMaxLength(80);
        TextField username = new TextField("Логин");
        username.setMaxLength(80);
        PasswordField password1 = new PasswordField("Пароль");
        password1.setMaxLength(80);
        //PasswordField password2 = new PasswordField("Подтверждение пароля");
        //password1.setMaxLength(80);
        add(
                new H2("Регистрация"),
                name,
                surname,
                username,
                password1,
                //password2,
                new Button("Зарегистрироваться", event -> register(
                        name.getValue(),
                        surname.getValue(),
                        username.getValue(),
                        password1.getValue()
                       // password2.getValue()
                )));
    }

    private void register(String name, String surname, String username, String password1) {
        if (name.trim().isEmpty()) {
            Notification.show("Введите имя");
        } else if (surname.trim().isEmpty()) {
            Notification.show("Введите фамилию");
        } else if (username.trim().isEmpty()) {
            Notification.show("Введите логин");
        } else if (password1.isEmpty()) {
            Notification.show("Введите пароль");
        } else if (password1.length() < 5) {
            Notification.show("Слишком слабый пароль");
        //} else if (!password1.equals(password2)) {
       //     Notification.show("Пароли не совпадают");
        } else {
            try {
                    ResponseEntity<AuthResponce> response = vaadinService.registration(username, name, surname, password1);
                    if (response.getStatusCode().equals(HttpStatus.CREATED)) {
//                        UI.getCurrent().navigate("");
                        UI.getCurrent().getPage().reload();
                        UI.getCurrent().navigate(MainView.class);
                    } else if (response.getStatusCode().equals(HttpStatus.SERVICE_UNAVAILABLE)) {
                        Notification.show("Сервис недоступен, попробуйте позже");
                    }
            } catch (Exception e) {
                Notification.show("Непредвиденная ошибка, попробуйте позже");
            }
        }
    }
}
