package com.example.front.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "", layout = MainLayout.class)
@PageTitle("Главная страница")
public class MainView extends VerticalLayout {
    public MainView() {
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        add(new H1("Аренда автомобилей"));
        Image mainImage = new Image("https://cdn-icons-png.flaticon.com/512/1349/1349570.png", "авто)");
        mainImage.setHeight("250px");
        mainImage.setHeight("250px");
        add(mainImage);
        add(new H4("Объединяем машины и людей"));
        Image imageArt = new Image("https://cdn-icons.flaticon.com/png/512/5442/premium/5442439.png?token=exp=1648456601~hmac=e8e88d4133813c1a323406ac458cfddf", "art");
        imageArt.setWidth("80px");
        imageArt.setHeight("80px");
        Image imageSculpture = new Image("https://cdn-icons-png.flaticon.com/512/72/72686.png", "pig");
        imageSculpture.setWidth("80px");
        imageSculpture.setHeight("80px");
        Image imageBook = new Image("https://cdn-icons.flaticon.com/png/512/5446/premium/5446308.png?token=exp=1648456689~hmac=ccb66e4e4bbd2bf1a3bd8a1b2d30b458", "авто2");
        imageBook.setWidth("80px");
        imageBook.setHeight("80px");
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        VerticalLayout verticalLayout1 = new VerticalLayout();
        H4 h41 = new H4("Быстро");
        verticalLayout1.add(imageArt, h41);

        VerticalLayout verticalLayout2 = new VerticalLayout();
        H4 h42 = new H4("Качественно");
        verticalLayout2.add(imageBook, h42);

        VerticalLayout verticalLayout3 = new VerticalLayout();
        H4 h43 = new H4("Недорого");
        verticalLayout3.add(imageSculpture, h43);
        horizontalLayout.add(verticalLayout1, verticalLayout2, verticalLayout3);
        add(horizontalLayout);
        //add(new H4("Хочешь быстро и качественно забронировать автомобиль для того, чтобы добраться до важного совещания, а может быть побыстрее доехать до любимого человека? " +
        // "Тогда ты попал на правильный сайт. У нас ты можешь ознакомиться с обширным выбором автомобилей и забронировать понравившейся в один клик!!!"));
    }
}
