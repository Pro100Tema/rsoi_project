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
        add(new H3("Объединяем машины и людей"));
        add(new H3("Наши контакты"));
        Image inst = new Image("https://cdn-icons.flaticon.com/png/512/3955/premium/3955024.png?token=exp=1654635229~hmac=a4fc7400077f6bcfdaf07add75e56b1d", "inst");
        Image vk = new Image("https://cdn-icons.flaticon.com/png/512/3670/premium/3670055.png?token=exp=1654635175~hmac=0b243f4a100af517a79e31fe05ce9baa", "vk");
        Image phone = new Image("https://cdn-icons-png.flaticon.com/512/724/724664.png", "phone");
        inst.setHeight("80px");
        inst.setHeight("80px");
        vk.setHeight("80px");
        vk.setHeight("80px");
        phone.setHeight("80px");
        phone.setWidth("80px");
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.add(phone);
        H4 hphone = new H4("+7(985)279-42-80");
        horizontalLayout.add(phone, hphone);
        H4 hvk = new H4("https://vk.com/bmstu_ea");
        horizontalLayout.add(vk,hvk);
        H4 hinst = new H4("@bmstu_ea");
        horizontalLayout.add(inst,hinst);
        add(horizontalLayout);
    }
}
