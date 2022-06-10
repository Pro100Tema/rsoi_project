package com.example.front.view;

import com.example.front.dto.*;
import com.example.front.service.VaadinService;
import com.flowingcode.vaadin.addons.simpletimer.SimpleTimer;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.virtuallist.VirtualList;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.dom.ElementFactory;
import com.vaadin.flow.router.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@Route(value = "car", layout = MainLayout.class)
@PageTitle("Автомобиль")
public class CarInfoView extends VerticalLayout implements HasUrlParameter<String> {

    @Autowired
    private VaadinService vaadinService;

    private UUID car_uid;
    private UUID user_uuid;
    private String username;
    private Boolean availability;
    private int page = 1;
    private int size = 12;
    private Boolean showAll = true;
    public CarInfoView() {

    }

    @Override
    public void setParameter(BeforeEvent event, @OptionalParameter String parameter) {
        if (parameter != null) {
            this.setCar_uid(UUID.fromString(parameter));
        }
        try {
            ResponseEntity<GetCarFullInfo> response = vaadinService.getCar(this.getCar_uid());

            //System.out.println(response.getStatusCode());
            if (response.getStatusCode().equals(HttpStatus.OK)) {

                GetCarFullInfo car = response.getBody();
      /*          GetCarFullInfo car = new GetCarFullInfo(
                        "0001111",
                        "Nissan",
                        "Skyline",
                        "777",
                        123,
                        500,
                        "racing",
                        true);*/


                //System.out.println(car.getModel());
                //ResponseEntity<CarFullInfo> response2 = vaadinService.getCars(page, size, showAll);
                //List<Car> shows = response2.getBody().getCars();
                H3 name = new H3("Брэнд " + car.getBrand());
                name.getStyle().set("padding-left", "32px").set("margin-top", "10px");

                Paragraph model = new Paragraph("Модель " + car.getModel());
                model.getStyle().set("padding-left", "32px").set("margin-top", "-10px");

                Span price = new Span("Стоимость " + car.getPrice() + "₽");
                price.getStyle().set("padding-left", "32px").set("margin-top", "-16px");

                Span power = new Span("Мощность " + car.getPower());
                power.getStyle().set("padding-left", "32px").set("margin-top", "-16px");

                Span type = new Span("Тип автомобиля " + car.getType());
                type.getStyle().set("padding-left", "32px").set("margin-top", "-16px");

                add(name, model, price, power, type);

                ResponseEntity<UserResponse> user_response = vaadinService.getUser();
                this.username = user_response.getBody().getLogin();
                this.user_uuid = user_response.getBody().getUser_uid();

                //VirtualList<Car> showList = new VirtualList<>();
                //showList.setItems(shows);
                //showList.setRenderer(showCardRenderer);
                //showList.getStyle().set("padding-left", "32px");
                //showList.getStyle().set("height", "288px");
                //if (shows.size() == 0) {
                //    showList.setVisible(false);
                //}

                Dialog changeDialog = new Dialog();
                VerticalLayout changeDialogLayout = changeDialogLayout(changeDialog, car);
                changeDialog.add(changeDialogLayout);

                Button deleteMuseumButton = new Button("Удалить", e -> {
                    GetCarFullInfo delete_car = new GetCarFullInfo();
                    delete_car.setCar_uid(car_uid.toString());
                    delete_car.setAvailability(false);
                    ResponseEntity<GetCarFullInfo> responseEntity = vaadinService.changeAvailability(delete_car);
                    if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
                        Notification.show("Автомобиль удален");
                        UI.getCurrent().navigate(CarView.class);
                    } else {
                        Notification.show("Что-то пошло не так");
                    }
                });
                deleteMuseumButton.setHeight("32px");
                deleteMuseumButton.setWidth("90px");
                Dialog dialog = new Dialog();
                VerticalLayout dialogLayout = createDialogLayout(dialog, car.getBrand(), car.getPrice(), car_uid);
                dialog.add(dialogLayout);
                Button buyButton = new Button("Забронировать", e -> {
                    GetCarFullInfo change_availability = new GetCarFullInfo();
                    change_availability.setCar_uid(car_uid.toString());
                    change_availability.setAvailability(false);
                    ResponseEntity<GetCarFullInfo> rental_response = vaadinService.changeAvailability(change_availability);
                    if (rental_response.getStatusCode().equals(HttpStatus.OK)) {
                        Notification.show("Автомобиль успешно забронирован!");
                        dialog.open();
                    } else {
                        Notification.show("Сервис бронирования недоступен");
                    }
                    //Rental rental = new Rental();
                    //rental.setCar_uid(car_uid);
                    //rental.setUsername(username);
                    //rental.setDate_to();
                    //ResponseEntity<HttpStatus> rental_response = vaadinService.createRental(rental);
                });
                buyButton.setHeight("32px");
                buyButton.setWidth("120px");


                Dialog dialog2 = new Dialog();
                VerticalLayout dialogLayout2 = createDialogLayout(dialog2, car.getCar_uid());
                dialog2.add(dialogLayout2);
                dialog2.add(dialogLayout2);

                HorizontalLayout horizontalLayout = new HorizontalLayout();
                horizontalLayout.getStyle().set("padding-left", "32px");

                //VerticalLayout contacts = new VerticalLayout(price, power);
                //contacts.setSpacing(false);
                //contacts.setPadding(false);

                //Details contactDetails = new Details("Контакты", contacts);
                //contactDetails.setOpened(false);
                //contactDetails.getStyle().set("padding-left", "32px");

                add(name, model, type, price, power);
                ResponseEntity<UserResponse> useResponse = vaadinService.getUser();
                if (useResponse.getBody().getRole().equals("USER")) {
                    horizontalLayout.add(buyButton);
                    add(horizontalLayout);
                } else if (useResponse.getBody().getRole().equals("ADMIN")) {
                    horizontalLayout.add(buyButton, deleteMuseumButton);
                    add(horizontalLayout);
                }
                //add(contactDetails);
            }
        } catch (Exception e) {
            Notification.show("Что-то пошло не так");
        }
    }

    private ComponentRenderer<Component, Car> showCardRenderer = new ComponentRenderer<>(showResponse -> {
        HorizontalLayout cardLayout = new HorizontalLayout();
        cardLayout.setMargin(true);

        VerticalLayout infoLayout = new VerticalLayout();
        infoLayout.setSpacing(false);
        infoLayout.setPadding(false);
        infoLayout.getElement().appendChild(ElementFactory.createStrong(showResponse.getBrand()));
        infoLayout.add(new Div(new Text(showResponse.getModel())));
        infoLayout.add(new Div(new Text("Цена: " + showResponse.getPrice().toString() + "₽")));
        Dialog dialog = new Dialog();
        VerticalLayout dialogLayout = createDialogLayout(dialog, showResponse.getBrand(), showResponse.getPrice(), showResponse.getCar_uid());
        dialog.add(dialogLayout);
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        Button buyButton = new Button("Забронировать", e -> {
            GetCarFullInfo change_availability = new GetCarFullInfo();
            change_availability.setCar_uid(car_uid.toString());
            change_availability.setAvailability(false);
            ResponseEntity<GetCarFullInfo> rental_response = vaadinService.changeAvailability(change_availability);
            if (rental_response.getStatusCode().equals(HttpStatus.OK)) {
                Notification.show("Автомобиль успешно забронирован!");
                dialog.open();
            } else {
                Notification.show("Сервис бронирования недоступен");
            }
            //Rental rental = new Rental();
            //rental.setCar_uid(car_uid);
            //rental.setUsername(username);
            //rental.setDate_to();
            //ResponseEntity<HttpStatus> rental_response = vaadinService.createRental(rental);
        });
        buyButton.setHeight("32px");
        buyButton.setWidth("120px");
        Button removeShowButton = new Button("Завершить", e -> {
            ResponseEntity<CarAndPaymentInfo> responseEntity = vaadinService.cancelRental(username, showResponse.getCar_uid());
            if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
                UI.getCurrent().navigate(CarView.class);
                UI.getCurrent().navigate("car/" + car_uid);
                //UI.getCurrent().navigate("car/" + "109b42f3-198d-4c89-9276-a7520a7120ab");
                Notification.show("Аренда успешно завершена");
            } else {
                Notification.show("Что-то пошло не так");
            }
        });
        removeShowButton.setHeight("32px");
        removeShowButton.setWidth("120px");
        horizontalLayout.add(buyButton);
        ResponseEntity<UserResponse> response = vaadinService.getUser();
        if (response.getBody().getRole().equals("USER")) {
            horizontalLayout.add(buyButton, removeShowButton);
        }
        infoLayout.add(horizontalLayout);
        cardLayout.add(infoLayout);
        return cardLayout;
    });

    public VerticalLayout createDialogLayout(Dialog dialog, String showName, Integer price, UUID show_uid) {
        H3 headline = new H3("Оплата аренды авто");
        headline.getStyle().set("margin", "var(--lumo-space-m) 0 0 0").set("font-size", "1.5em").set("font-weight", "bold");

        SimpleTimer timer = new SimpleTimer(new BigDecimal("30"));
        timer.start();
        timer.addTimerEndEvent(ev-> {
            GetCarFullInfo change_availability = new GetCarFullInfo();
            change_availability.setCar_uid(car_uid.toString());
            change_availability.setAvailability(true);
            ResponseEntity<GetCarFullInfo> rental_response = vaadinService.changeAvailability(change_availability);
            if (rental_response.getStatusCode().equals(HttpStatus.OK)) {
                Notification.show("Бронирование автомобиля отменено! Прошло больше 30 секунд");
                UI.getCurrent().navigate(CarView.class);
                dialog.close();
            }
        });

        AtomicInteger sum = new AtomicInteger();
        Span name = new Span(showName);
        IntegerField integerField = new IntegerField();
        integerField.setLabel("Количество дней для аренды");
        integerField.setMin(1);
        integerField.setMax(30);
        integerField.setValue(1);
        integerField.setHasControls(true);

        IntegerField sumField = new IntegerField();
        sumField.setLabel("Сумма");
        sumField.setMin(price);
        sumField.setValue(price);
        sumField.setReadOnly(true);
        sumField.setHasControls(false);

        integerField.addValueChangeListener(e -> {
            sum.set(price * integerField.getValue());
            sumField.setValue(sum.intValue());
        });

        VerticalLayout fieldLayout = new VerticalLayout(name, timer, integerField, sumField);
        fieldLayout.setSpacing(false);
        fieldLayout.setPadding(false);
        fieldLayout.setAlignItems(FlexComponent.Alignment.STRETCH);

        Button cancelButton = new Button("Отмена", e -> dialog.close());
        Button buyButton = new Button("Оплатить", buttonClickEvent -> {
            try {
                PaymentInfo paymentInfo = new PaymentInfo();
                paymentInfo.setUser_uid(user_uuid.toString());
                paymentInfo.setPrice(price);
                ResponseEntity<PaymentInfo> response = vaadinService.paymentForCar(paymentInfo);
                if (response.getStatusCode().equals(HttpStatus.OK)) {
                    RentalInfo rentalInfo = new RentalInfo();
                    rentalInfo.setCar_uid(car_uid.toString());
                    rentalInfo.setUser_uid(user_uuid.toString());
                    rentalInfo.setPayment_uid(response.getBody().getPayment_uid());
                    rentalInfo.setDateFrom(LocalDateTime.now());
                    rentalInfo.setDateTo(LocalDateTime.now().plusDays(integerField.getValue()));
                    ResponseEntity<RentalInfo> response2 = vaadinService.createRental(rentalInfo);
                    if (response2.getStatusCode().equals(HttpStatus.OK)) {
                        Notification.show("Оплата арендованного автомобиля прошла успешно!");
                        UI.getCurrent().navigate(CarView.class);
                    }
                } else {
                    Notification.show("Что-то пошло не так");
                }
            } catch (Exception e) {
                Notification.show("Что-то пошло не так");
            }
            dialog.close();
        });
        buyButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        HorizontalLayout buttonLayout = new HorizontalLayout(cancelButton,
                buyButton);
        buttonLayout
                .setJustifyContentMode(FlexComponent.JustifyContentMode.END);

        VerticalLayout dialogLayout = new VerticalLayout(headline, fieldLayout,
                buttonLayout);
        dialogLayout.setPadding(false);
        dialogLayout.setAlignItems(FlexComponent.Alignment.STRETCH);
        dialogLayout.getStyle().set("width", "500px").set("max-width", "100%");
        return dialogLayout;
    }

    public VerticalLayout createDialogLayout(Dialog dialog, String car_uid) {
        H3 headline = new H3("Добавление автомобиля");
        headline.getStyle().set("margin", "var(--lumo-space-m) 0 0 0")
                .set("font-size", "1.5em").set("font-weight", "bold");

        TextField name = new TextField("Название*");
        name.setClearButtonVisible(true);
        name.setMaxLength(80);

        TextArea desc = new TextArea("Описание");
        desc.setMaxLength(600);
        desc.setClearButtonVisible(true);
        desc.setValueChangeMode(ValueChangeMode.EAGER);
        desc.addValueChangeListener(e -> {
            e.getSource().setHelperText(e.getValue().length() + "/" + 600);
        });

        TextField price = new TextField("Цена");
        price.setClearButtonVisible(true);
        price.setMaxLength(12);
        price.setPattern("^([1-9]).*[0-9]");
        price.setSuffixComponent(new Div(new Text("₽")));
        price.setErrorMessage("Указана некорректная цена");

        DatePicker startDate = new DatePicker("Дата начала");
        DatePicker endDate = new DatePicker("Дата окончания");
        startDate.setClearButtonVisible(true);
        endDate.setClearButtonVisible(true);
        startDate.setEnabled(false);
        endDate.setEnabled(false);

        startDate.addValueChangeListener(e -> {
            endDate.setMin(e.getValue());
        });
        endDate.addValueChangeListener(e -> {
            startDate.setMax(e.getValue());
        });

        Checkbox permanentExhibition = new Checkbox();
        permanentExhibition.setLabel("Постоянная выставка");
        permanentExhibition.setValue(true);
        permanentExhibition.addValueChangeListener(e -> {
            if (e.getValue()) {
                endDate.setValue(null);
                startDate.setValue(null);
            }
            endDate.setEnabled(!e.getValue());
            startDate.setEnabled(!e.getValue());
        });
        permanentExhibition.getStyle().set("margin-top", "13px");
        VerticalLayout fieldLayout = new VerticalLayout(name, desc, price, permanentExhibition, startDate, endDate);
        fieldLayout.setSpacing(false);
        fieldLayout.setPadding(false);
        fieldLayout.setAlignItems(FlexComponent.Alignment.STRETCH);

        Button cancelButton = new Button("Отмена", e -> dialog.close());
        Button addButton = new Button("Добавить", buttonClickEvent -> {
            if (name.getValue().trim().isEmpty()) {
                Notification.show("Введите имя");
            } else if (price.getValue() == null || price.getValue().trim().isEmpty() || price.isInvalid()) {
                Notification.show("Укажите корректную цену");
            } else if (!permanentExhibition.getValue() && (startDate.getValue() == null || endDate.getValue() == null)) {
                Notification.show("Укажите корректную дату");
            } else {
                Rental showResponse = new Rental();
                showResponse.setUsername(name.getValue());
                showResponse.setDate_from(startDate);
                showResponse.setDate_to(endDate);
                showResponse.setCar_uid(UUID.fromString(car_uid));
                ResponseEntity<HttpStatus> responseEntity = vaadinService.updateCar(UUID.fromString(car_uid), availability);
                if (responseEntity.getStatusCode().equals(HttpStatus.CREATED)) {
                    UI.getCurrent().navigate(CarView.class);
                    UI.getCurrent().navigate("car/" + car_uid);
                    //UI.getCurrent().navigate("car/" + "109b42f3-198d-4c89-9276-a7520a7120ab");
                    Notification.show("Автомобиль успешно добавлен");
                    //showList.setVisible(true);
                } else {
                    Notification.show("Что-то пошло не так");
                }
                dialog.close();
            }
        });
        addButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        HorizontalLayout buttonLayout = new HorizontalLayout(cancelButton,
                addButton);
        buttonLayout
                .setJustifyContentMode(FlexComponent.JustifyContentMode.END);

        VerticalLayout dialogLayout = new VerticalLayout(headline, fieldLayout,
                buttonLayout);
        dialogLayout.setPadding(false);
        dialogLayout.setAlignItems(FlexComponent.Alignment.STRETCH);
        dialogLayout.getStyle().set("width", "500px").set("max-width", "100%");
        return dialogLayout;
    }

    public VerticalLayout changeDialogLayout(Dialog dialog, GetCarFullInfo car) {
        H3 headline = new H3("Добавление автомобиля");
        headline.getStyle().set("margin", "var(--lumo-space-m) 0 0 0")
                .set("font-size", "1.5em").set("font-weight", "bold");

        TextField name = new TextField("Название*");
        name.setClearButtonVisible(true);
        name.setMaxLength(80);
        name.setValue(car.getBrand());

        TextArea desc = new TextArea("Модель");
        desc.setMaxLength(600);
        desc.setClearButtonVisible(true);
        desc.setValue(car.getModel());
        desc.setValueChangeMode(ValueChangeMode.EAGER);
        desc.addValueChangeListener(e -> {
            e.getSource().setHelperText(e.getValue().length() + "/" + 600);
        });

        TextField city = new TextField();
        city.setLabel("Стоимость*");
        city.setMaxLength(50);
        city.setValue(String.valueOf(car.getPrice()));
        city.setClearButtonVisible(true);
        city.setPrefixComponent(VaadinIcon.MAP_MARKER.create());

        TextField street = new TextField();
        street.setLabel("Мощность*");
        street.setMaxLength(50);
        street.setValue(String.valueOf(car.getPower()));
        street.setClearButtonVisible(true);
        street.setPrefixComponent(VaadinIcon.MAP_MARKER.create());

        TextField type = new TextField();
        type.setLabel("Тип автомобиля*");
        type.setMaxLength(50);
        type.setValue(String.valueOf(car.getType()));
        type.setClearButtonVisible(true);
        type.setPrefixComponent(VaadinIcon.MAP_MARKER.create());


        VerticalLayout fieldLayout = new VerticalLayout(name, desc, type, city, street);
        fieldLayout.setSpacing(false);
        fieldLayout.setPadding(false);
        fieldLayout.setAlignItems(FlexComponent.Alignment.STRETCH);

        Button cancelButton = new Button("Отмена", e -> dialog.close());
        Button addButton = new Button("Изменить", buttonClickEvent -> {
            if (name.getValue().trim().isEmpty()) {
                Notification.show("Введите название");
            } else if (type.getValue() == null || type.getValue().trim().isEmpty()) {
                Notification.show("Выберите тип автомобиля");
            } else {

                /*GetCarFullInfo carInfoResponse = new GetCarFullInfo(
                        car.getCar_uid(),
                        name.getValue(),
                        desc.getValue(),
                        Integer.valueOf(new Random().nextInt()).toString(),
                        Integer.valueOf(city.toString()),
                        Integer.valueOf(street.getValue()),
                        type.getValue(),
                        true
                );*/

                ResponseEntity<HttpStatus> responseEntity = vaadinService.updateCar(car_uid,availability);
                if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
                    UI.getCurrent().navigate(CarView.class);
                    UI.getCurrent().navigate("car/" + car_uid);
                    //UI.getCurrent().navigate("car/" + "109b42f3-198d-4c89-9276-a7520a7120ab");
                }
                else {
                    Notification.show("Что-то пошло не так");
                }
                dialog.close();
            }
        });
        addButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        HorizontalLayout buttonLayout = new HorizontalLayout(cancelButton, addButton);
        buttonLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.END);

        VerticalLayout dialogLayout = new VerticalLayout(headline, fieldLayout,
                buttonLayout);
        dialogLayout.setPadding(false);
        dialogLayout.setAlignItems(FlexComponent.Alignment.STRETCH);
        dialogLayout.getStyle().set("width", "500px").set("max-width", "100%");
        return dialogLayout;
    }

    public UUID getCar_uid() {
        return car_uid;
    }

    public void setCar_uid(UUID car_uid) {
        this.car_uid = car_uid;
    }
}
