package com.finstone.tmall.util;

import com.finstone.tmall.pojo.Product;

import java.util.Comparator;

public class ProductComparator implements Comparator<Product> {

    /**
     * 比较方式
     */
    private String sort;

    public ProductComparator() {
    }

    public ProductComparator(String sort) {
        this.sort = sort;
    }

    @Override
    public int compare(Product o1, Product o2) {
        int result = 0;
        switch (sort){
            case "all": //热门（销量*评论数）
                result = o1.getSaleCount()*o1.getReviewCount() - o2.getSaleCount()*o2.getReviewCount();
                break;
            case "review": //评价数多
                result =  o1.getReviewCount() - o2.getReviewCount();
                break;
            case "date": //发布日期近
                result = o1.getCreateDate().compareTo(o2.getCreateDate());
                break;
            case "saleCount": //销量多
                result = -(o1.getSaleCount() - o2.getSaleCount());
                break;
            case "price": //价格低
                result = o1.getPromotePrice().compareTo(o2.getPromotePrice());
                break;
            default:
                result = 0;
        }
        return result;
    }
}
