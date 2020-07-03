package com.dovit.backend.payloads.responses;

import lombok.Data;

import java.util.List;

/**
 * @author Ramón París
 * @since 03-10-2019
 */
@Data
public class PagedResponse<T> {

    private List<T> content;
    private int page;
    private int size;
    private long totalElements;
    private long totalPages;
    private boolean last;

    public PagedResponse(
            List<T> content, int page, int size, long totalElements, long totalPages, boolean last) {
        this.content = content;
        this.page = page;
        this.size = size;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.last = last;
    }

    public PagedResponse() {
    }
}
