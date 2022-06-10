package com.example.front.view;

import com.example.front.dto.*;
import com.example.front.service.VaadinService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.grid.dataview.GridListDataView;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.function.Consumer;

@Route(value = "cars", layout = MainLayout.class)
@PageTitle("Автомобили")
public class CarView extends VerticalLayout {

    @Autowired
    private VaadinService vaadinService;

    private int totalAmountOfPages;

    private int itemsPerPage = 12;

    private int currentPageNumber = 1;

    private boolean showAll = false;

    public CarView() {
    }

    @PostConstruct
    public void init() {
        Grid<Car> grid = new Grid<>();
        try {
            this.getStyle().set("padding", "0");
            ResponseEntity<CarFullInfo> cars = vaadinService.getCars(currentPageNumber, itemsPerPage, showAll);
            totalAmountOfPages = 0;
            List<Car> initialItems = cars.getBody().getCars();
            Integer totalAmountOfItems = 0;
            /*if (totalAmountOfItems % itemsPerPage == 0) {
                totalAmountOfPages = totalAmountOfItems / itemsPerPage;
            } else {
                totalAmountOfPages = totalAmountOfItems / itemsPerPage + 1;
            }*/
            GridListDataView<Car> dataView = grid.setItems(initialItems);
            CarFilter carFilter = new CarFilter(dataView);
            Grid.Column<Car>  model= grid.addColumn(Car::getModel);
            Grid.Column<Car>  type= grid.addColumn(Car::getType);
            Grid.Column<Car>  brand= grid.addColumn(Car::getBrand);
            Grid.Column<Car>  power= grid.addColumn(Car::getPower);
            Grid.Column<Car>  price= grid.addColumn(Car::getPrice);
            Grid.Column<Car>  number= grid.addColumn(Car::getRegistration_number);
            grid.setWidth("100%");
            grid.setWidthFull();
            grid.getStyle().set("margin", "0").set("margin-top", "-12px");
            grid.getHeaderRows().clear();

            model.setWidth("200px");
            type.setWidth("200px");
            brand.setWidth("200px");
            power.setWidth("200px");
            price.setWidth("200px");
            number.setWidth("200px");
            HeaderRow headerRow = grid.appendHeaderRow();

            CarFilter Filter = new CarFilter(dataView);
            headerRow.getCell(model).setComponent(
                    createFilterHeader("Модель", Filter::setModel));
            headerRow.getCell(type).setComponent(
                    createFilterHeader("Тип", Filter::setType));
            headerRow.getCell(brand).setComponent(
                    createFilterHeader("Бренд", Filter::setBrand));
            headerRow.getCell(power).setComponent(
                    createFilterHeader("Мощность", Filter::setPower));
            headerRow.getCell(price).setComponent(
                    createFilterHeader("Стоимость", Filter::setPrice));
            headerRow.getCell(number).setComponent(
                    createFilterHeader("Регистрационный номер", Filter::setNumber));
            grid.setHeightByRows(true);
            H5 currPage = new H5(String.valueOf(currentPageNumber));
            currPage.getStyle().set("margin-top", "0.8em");

            /*Button previousButton = new Button("Предыдущая страница", e -> {
                if (currentPageNumber <= 1) {
                    return;
                }
                ResponseEntity<CarFullInfo> carsNext = vaadinService.getCars(--currentPageNumber, itemsPerPage, showAll);
                List<Car> prevPageItems = carsNext.getBody().getCars();
                GridListDataView<Car> prevDataView = grid.setItems(prevPageItems);
                CarFilter prevCarFilter = new CarFilter(prevDataView);
                headerRow.getCell(model).setComponent(
                        createFilterHeader("Модель", prevCarFilter::setModel));
                headerRow.getCell(type).setComponent(
                        createFilterHeader("Тип", prevCarFilter::setType));
                headerRow.getCell(brand).setComponent(
                        createFilterHeader("Бренд", prevCarFilter::setBrand));
                headerRow.getCell(power).setComponent(
                        createFilterHeader("Мощность", prevCarFilter::setPower));
                headerRow.getCell(price).setComponent(
                        createFilterHeader("Стоимость", prevCarFilter::setPrice));
                headerRow.getCell(number).setComponent(
                        createFilterHeader("Регистрационный номер", prevCarFilter::setNumber));
                currPage.setText(String.valueOf(currentPageNumber));
            });
            Button nextButton = new Button("Следующая страница", e -> {
                if (currentPageNumber >= totalAmountOfPages) {
                    return;
                }
                ResponseEntity<CarFullInfo> carsNext = vaadinService.getCars(++currentPageNumber, itemsPerPage, showAll);
                List<Car> nextPageItems = carsNext.getBody().getCars();
                GridListDataView<Car> nextDataView = grid.setItems(nextPageItems);
                CarFilter nextCarFilter = new CarFilter(nextDataView);
                headerRow.getCell(model).setComponent(
                        createFilterHeader("Модель", nextCarFilter::setModel));
                headerRow.getCell(type).setComponent(
                        createFilterHeader("Тип", nextCarFilter::setType));
                headerRow.getCell(brand).setComponent(
                        createFilterHeader("Бренд", nextCarFilter::setBrand));
                headerRow.getCell(power).setComponent(
                        createFilterHeader("Мощность", nextCarFilter::setPower));
                headerRow.getCell(price).setComponent(
                        createFilterHeader("Стоимость", nextCarFilter::setPrice));
                headerRow.getCell(number).setComponent(
                        createFilterHeader("Регистрационный номер", nextCarFilter::setNumber));
                currPage.setText(String.valueOf(currentPageNumber));
            });*/
            //HorizontalLayout horizontalLayout = new HorizontalLayout();
            //horizontalLayout.add(previousButton, currPage, nextButton);
            //horizontalLayout.getStyle().set("margin-left", "16px");
            grid.addItemClickListener(item -> {
                UI.getCurrent().navigate("car/" + item.getItem().getCar_uid());
                //UI.getCurrent().navigate("car/" + "109b42f3-198d-4c89-9276-a7520a7120ab");
            });
            add(grid);

        } catch (Exception e) {
            Notification.show("Что-то пошло не так");
        }
    }

    private static Component createFilterHeader(String labelText, Consumer<String> filterChangeConsumer) {
        Label label = new Label(labelText);
        label.getStyle().set("padding-top", "var(--lumo-space-m)")
                .set("font-size", "var(--lumo-font-size-xs)");
        TextField textField = new TextField();
        textField.setValueChangeMode(ValueChangeMode.EAGER);
        textField.setClearButtonVisible(true);
        textField.addThemeVariants(TextFieldVariant.LUMO_SMALL);
        textField.setWidthFull();
        textField.getStyle().set("max-width", "100%");
        textField.addValueChangeListener(
                e -> filterChangeConsumer.accept(e.getValue()));
        VerticalLayout layout = new VerticalLayout(label, textField);
        layout.getThemeList().clear();
        layout.getThemeList().add("spacing-xs");

        return layout;
    }

    private static class CarFilter {
        private final GridListDataView<Car> dataView;

        private String model;
        private String type;
        private String brand;
        private String power;
        private String price;
        private String number;

        public CarFilter(GridListDataView<Car> dataView) {
            this.dataView = dataView;
            this.dataView.addFilter(this::test);
        }

        public void setModel(String model) {
            this.model = model;
            this.dataView.refreshAll();
        }

        public void setType(String type) {
            this.type = type;
            this.dataView.refreshAll();
        }

        public void setBrand(String brand) {
            this.brand = brand;
            this.dataView.refreshAll();
        }

        public void setPower(String power) {
            this.power = power;
            this.dataView.refreshAll();
        }

        public void setPrice(String price) {
            this.price = price;
            this.dataView.refreshAll();
        }

        public void setNumber(String number) {
            this.number = number;
            this.dataView.refreshAll();
        }

        public boolean test(Car carResponse) {
            return true;
        }

    }
}
