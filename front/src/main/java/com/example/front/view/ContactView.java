package com.example.front.view;

import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "contact", layout = MainLayout.class)
@PageTitle("Контакты")
public class ContactView extends VerticalLayout {

    public ContactView() {
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        add(new H3("Наши контакты"));
        Image inst = new Image("https://cdn-icons.flaticon.com/png/512/3955/premium/3955024.png?token=exp=1648457761~hmac=6ce91bf01d72836bb6f96f0308587778", "inst");
        Image vk = new Image("https://cdn-icons.flaticon.com/png/512/3670/premium/3670055.png?token=exp=1648457610~hmac=0edec0ed6c71853f8e2384ad28266f67", "vk");
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
