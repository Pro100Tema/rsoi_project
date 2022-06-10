package com.example.front.view;

import com.example.front.dto.*;
import com.example.front.service.VaadinService;
import com.example.front.view.LoginView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.dom.ThemeList;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinRequest;
import com.vaadin.flow.theme.lumo.Lumo;
import com.vaadin.flow.component.notification.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.annotation.PostConstruct;
import javax.servlet.http.Cookie;
import java.util.*;
import java.util.stream.Collectors;

@Route(value = "user", layout = MainLayout.class)
@PageTitle("Пользователь")
public class UserView extends VerticalLayout {

    @Autowired
    private VaadinService vaadinService;

    public UserView() {

    }

    @PostConstruct
    public void init() {
        setSizeFull();
        ResponseEntity<UserResponse> response = vaadinService.getUser();
        //System.out.println(response);
        UserResponse userResponse = new UserResponse();
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        VerticalLayout profile = new VerticalLayout();
        VerticalLayout userHistory = new VerticalLayout();
        profile.setAlignItems(Alignment.CENTER);
        //System.out.println(response);
        if (response.getStatusCode().equals(HttpStatus.OK)) {
            userResponse = response.getBody();
            //System.out.println(userResponse.getName().toString());
            //Avatar avatar = new Avatar(userResponse.getName() + " " + userResponse.getSurname());
            //avatar.setWidth("200px");
            //avatar.setHeight("200px");
            H1 title = new H1("Профиль любимого пользователя");
            Image userImage = new Image("https://cdn-icons-png.flaticon.com/512/3022/3022316.png", "user");
            userImage.setHeight("250px");
            userImage.setHeight("250px");
            H4 name = new H4("Имя" + " " + userResponse.getName());
            //name.getStyle().set("margin-top", "0px");
            H4 surname = new H4("Фамилия" + " " + userResponse.getSurname());
            //name.getStyle().set("margin-top", "0px");
            H4 log = new H4("Логин" + " " + userResponse.getLogin());
            //log.getStyle().set("margin-top", "0px");
            //profile.getStyle().set("margin-left", "50px");
            H4 role = new H4("Роль:" + " " + userResponse.getRole());
            //role.getStyle().set("margin-top", "0px");
            //profile.getStyle().set("margin-left", "50px");
            Button exitButton = new Button("Выход", e -> {
                UI.getCurrent().getPage().executeJs("document.cookie = 'jwt=;expires=Thu, 01 Jan 1970 00:00:00 GMT'");
                UI.getCurrent().getPage().reload();
                UI.getCurrent().navigate(LoginView.class);
            });

            profile.add(title, userImage, log, name, surname, role);
            profile.getStyle().set("margin-right", "-50%");

            if (userResponse.getRole().equals("ADMIN")) {
                Dialog dialog = new Dialog();
                VerticalLayout dialogLayout = createDialogLayout(dialog);
                dialog.add(dialogLayout);
                Button addCar = new Button("Добавить автомобиль", e -> dialog.open());
                dialog.add(dialogLayout);
                profile.add(addCar);
                profile.add(exitButton);

                ResponseEntity<List<UserStat>> userStat = vaadinService.getUserStat();
                if (userStat.getStatusCode().equals(HttpStatus.OK) && userStat.getBody().size() > 0) {

                    H3 userStatTitle = new H3("Зарегистрированные пользователи портала");
                    userStatTitle.getStyle().set("margin-bottom", "-8px").set("margin-top", "5px");
                    userHistory.getStyle().set("margin-top", "380px");
                    userHistory.getStyle().set("margin-left", "550px");
                    userHistory.add(userStatTitle);
                    ResponseEntity<List<UserStat>> userStatResponse = vaadinService.getUserStat();
                    if (userStatResponse.getStatusCode().equals(HttpStatus.OK) && userStatResponse.getBody().size() > 0) {
                        List<UserStat> userStatDtoList = userStatResponse.getBody().stream()
                                .sorted(Comparator.comparing(UserStat::getRegisterDate)).collect(Collectors.toList());
                        Collections.reverse(userStatDtoList);
                        Grid<UserStat> gridUser = new Grid<>(UserStat.class, false);
                        gridUser.addColumn(UserStat::getName).setHeader("Имя")
                                .setFooter(String.format("Всего %s", userStatDtoList.size())).setAutoWidth(true);
                        gridUser.addColumn(UserStat::getUsername).setHeader("Логин").setAutoWidth(true);
                        gridUser.addColumn(UserStat::getStringRegisterDate).setHeader("Дата").setWidth("7em").setTextAlign(ColumnTextAlign.END);
                        gridUser.setItems(userStatDtoList);
                        gridUser.setMinWidth("860px");
                        gridUser.setMaxHeight("240px");
                        //gridUser.addThemeVariants(GridVariant.LUMO_NO_ROW_BORDERS);
                        //gridUser.getStyle().set("margin-left", "500px");
                        //gridUser.getStyle().set("margin-top", "0px");
                        userHistory.add(gridUser);
                    }
                } else {
                    Span err = new Span("Статистика в данный момент недоступна");
                    err.getStyle().set("width", "400px");
                    userHistory.add(err);
                }
                horizontalLayout.add(profile,userHistory);
            } else if (userResponse.getRole().equals("USER")){
                exitButton.getStyle().set("margin-top", "750px");
                exitButton.getStyle().set("margin-right", "100px");
                horizontalLayout.add(profile);
                horizontalLayout.add(exitButton);
            }
            add(horizontalLayout);
        } else {
            Notification.show("Что-то пошло не так");
        }
    }

    public VerticalLayout createDialogLayout(Dialog dialog) {
        H3 headline = new H3("Добавление автомобиля");
        headline.getStyle().set("margin", "var(--lumo-space-m) 0 0 0")
                .set("font-size", "1.5em").set("font-weight", "bold");

        TextField model = new TextField("Модель");
        model.setClearButtonVisible(true);
        model.setMaxLength(80);

        TextArea type = new TextArea("Тип");
        type.setMaxLength(600);
        type.setClearButtonVisible(true);
        type.setValueChangeMode(ValueChangeMode.EAGER);
        /*type.addValueChangeListener(e -> {
            e.getSource().setHelperText(e.getValue().length() + "/" + 600);
        });*/

        TextField brand = new TextField();
        brand.setLabel("Бренд");
        brand.setMaxLength(50);
        brand.setClearButtonVisible(true);
        //brand.setPrefixComponent(VaadinIcon.MAP_MARKER.create());

        TextField power = new TextField();
        power.setLabel("Мощность");
        power.setMaxLength(50);
        power.setClearButtonVisible(true);
        //power.setPrefixComponent(VaadinIcon.MAP_MARKER.create());

        TextField price = new TextField();
        price.setLabel("Стоимость");
        price.setMaxLength(50);
        price.setClearButtonVisible(true);
        //price.setPrefixComponent(VaadinIcon.MAP_MARKER.create());

        TextField register_number = new TextField("Регистрационный номер");
        register_number.setClearButtonVisible(true);
        register_number.setMaxLength(100);


        VerticalLayout fieldLayout = new VerticalLayout(model, type, type, brand, power, price, register_number);
        fieldLayout.setSpacing(false);
        fieldLayout.setPadding(false);
        fieldLayout.setAlignItems(FlexComponent.Alignment.STRETCH);

        Button cancelButton = new Button("Отмена", e -> dialog.close());
        Button addButton = new Button("Добавить", buttonClickEvent -> {
            if (model.getValue().trim().isEmpty()) {
                Notification.show("Введите название модели");
            } else if (type.getValue() == null || type.getValue().trim().isEmpty()) {
                Notification.show("Выберите тип автомобиля");
            } else if (power.getValue().trim().isEmpty()){
                Notification.show("Введите мощность автомобиля");
            } else if (price.getValue().trim().isEmpty()){
                Notification.show("Введите стоимость автомобиля");
            } else if (brand.getValue().trim().isEmpty()) {
                Notification.show("Введите название брэнда");
            } else {

                GetCarFullInfo CarInfoResponse = new GetCarFullInfo();
                CarInfoResponse.setModel(model.getValue());
                CarInfoResponse.setType(type.getValue());
                CarInfoResponse.setBrand(brand.getValue());
                CarInfoResponse.setPower(Integer.valueOf(power.getValue()));
                CarInfoResponse.setPrice(Integer.valueOf(price.getValue()));
                CarInfoResponse.setRegistration_number(register_number.getValue());

                ResponseEntity<GetCarFullInfo> responseEntity = vaadinService.createCar(CarInfoResponse);
                if (responseEntity.getStatusCode().equals(HttpStatus.CREATED)) {
                    UI.getCurrent().navigate("car/" + responseEntity.getBody().getCar_uid());
                } else {
                    Notification.show("Что-то пошло не так");
                }
                dialog.close();
            }
        });
        HorizontalLayout buttonLayout = new HorizontalLayout(cancelButton, addButton);
        buttonLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.END);

        VerticalLayout dialogLayout = new VerticalLayout(headline, fieldLayout, buttonLayout);
        dialogLayout.setPadding(false);
        dialogLayout.setAlignItems(FlexComponent.Alignment.STRETCH);
        dialogLayout.getStyle().set("width", "500px").set("max-width", "100%");
        return dialogLayout;
    }
}