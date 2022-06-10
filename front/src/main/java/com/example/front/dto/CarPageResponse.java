package com.example.front.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class CarPageResponse {
    private int page;

    private int pageSize;

    private int totalElements;

    private List<CarResponse> items;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(int totalElements) {
        this.totalElements = totalElements;
    }

    public List<CarResponse> getItems() {
        return items;
    }

    public void setItems(List<CarResponse> items) {
        this.items = items;
    }
}
