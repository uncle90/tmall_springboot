package com.finstone.tmall.pojo;

import org.springframework.data.domain.Page;

import java.util.List;

/**
 * 基于 Sping Data JPA 分页接口 {@link Page} 的实现类 PageImpl 和 Chunk改造：
 * 1）将所需属性、变量，转换为 {@link Page4JPA} 的成员变量；
 * 2）改造分页bean，指定分页标签个数；
 * @param <T>
 */
public class Page4JPA<T>{
    public static final int defaultSize = 5; //默认每页显示5条

    public Page4JPA() { size = defaultSize; }

    public Page4JPA(Page<T> page) {
        content = page.getContent();
        size = page.getSize();
        totalElements = page.getTotalElements();
        number = page.getNumber();
        numberOfElements = page.getNumberOfElements();
        totalPages = page.getTotalPages();
        hasContent = page.hasContent();
        first = page.isFirst();
        last = page.isLast();
        hasPrevious = page.hasPrevious();
        hasNext = page.hasNext();
    }

    /**
     * 以下内容摘自 Sping Data JPA 分页接口 {@link Page}
     */
    List<T> content; //分页数据

    int size; //每页记录条数limit, 常数.

    long totalElements; //记录总数

    int totalPages; //分页总数 = totalElements/size;

    int number; //当前页编号(0,1,2,..,n-1)

    int numberOfElements; //当前页记录条数 <= size

    boolean hasContent; //是否有数据

    boolean first; //是否为第一页

    boolean last; //是否为最后一页

    boolean hasPrevious; //是否有上一页

    boolean hasNext; //是否有下一页

    /**
     * 自定义内容，指定分页标签个数
     */
    int navigatePages; //要显示的分页标签个数。如果[3,4,5,6,7]即为5个。在分页数较多时，便于前端显示。

    int[] navigatepageNums; //与navigatePages对应，要显示的分页标签编号list。navigatePages=5时，为[3,4,5,6,7]。

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getNumberOfElements() {
        return numberOfElements;
    }

    public void setNumberOfElements(int numberOfElements) {
        this.numberOfElements = numberOfElements;
    }

    public boolean isHasContent() {
        return hasContent;
    }

    public void setHasContent(boolean hasContent) {
        this.hasContent = hasContent;
    }

    public boolean isFirst() {
        return first;
    }

    public void setFirst(boolean first) {
        this.first = first;
    }

    public boolean isLast() {
        return last;
    }

    public void setLast(boolean last) {
        this.last = last;
    }

    public boolean isHasPrevious() {
        return hasPrevious;
    }

    public void setHasPrevious(boolean hasPrevious) {
        this.hasPrevious = hasPrevious;
    }

    public boolean isHasNext() {
        return hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }

    public int getNavigatePages() {
        return navigatePages;
    }

    public void setNavigatePages(int navigatePages) {
        this.navigatePages = navigatePages;
    }

    public int[] getNavigatepageNums() {
        return navigatepageNums;
    }

    public void setNavigatepageNums(int[] navigatepageNums) {
        this.navigatepageNums = navigatepageNums;
    }
}
