package com.example.gateway;

import java.util.List;

public class PaginationResponse {
    private Integer page;
    private Integer pageSize;
    private Integer totalElements;
    private List<CarResponse> items;

    public PaginationResponse(Integer page, Integer pageSize, Integer totalElements, List<CarResponse> items) {
        this.page = page;
        this.pageSize = pageSize;
        this.totalElements = totalElements;
        this.items = items;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(Integer totalElements) {
        this.totalElements = totalElements;
    }

    public List<CarResponse> getItems() {
        return items;
    }

    public void setItems(List<CarResponse> items) {
        this.items = items;
    }
}
