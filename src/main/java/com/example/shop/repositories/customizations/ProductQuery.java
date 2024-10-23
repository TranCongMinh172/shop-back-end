package com.example.shop.repositories.customizations;

import com.example.shop.dtos.responses.PageResponse;
import com.example.shop.dtos.responses.product.ProductUserResponse;
import com.example.shop.models.Product;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;


@Repository
public class ProductQuery extends BaseCustomizationRepository<Product>{
    public ProductQuery() {
        super(Product.class);
    }
    private String getQuery(String query) {
        return String.format("select distinct %s " +
                "from Product p " +
                "left join ProductPrice pp " +
                "on p = pp.product and pp.expiredDate > current date where (pp.discountPrice = " +
                "(select max(pp2.discountPrice) from ProductPrice pp2 where pp2.product = p) or pp.discountPrice is null) ", query);
    }
    private String getPromotionQuery(String query) {
        return String.format("select distinct %s " +
                "from Product p " +
                "inner join ProductPrice pp " +
                "on p = pp.product and pp.expiredDate > current date where (pp.discountPrice = " +
                "(select max(pp2.discountPrice) from ProductPrice pp2 where pp2.product = p) or pp.discountPrice is null) ", query);
    }
    @Override
    public PageResponse<?> getPageData(int pageNo, int pageSize, String[] search, String[] sort) {

        StringBuilder queryBuilder = new StringBuilder(getQuery("new com.example.shop.dtos.responses.product.ProductUserResponse(p, pp.discount, pp.discountPrice, pp.expiredDate)"));

        appendQueryBuilder(search, queryBuilder, " %s %sp.%s%s %s ?%s");
        sortBy(queryBuilder, " order by p.%s %s", sort);
        TypedQuery<ProductUserResponse> query = em.createQuery(queryBuilder.toString(), ProductUserResponse.class);
        query.setFirstResult((pageNo - 1) * pageSize);
        query.setMaxResults(pageSize);
        setValueParams(search, query);

        var data = query.getResultList();

        // count element
        StringBuilder countQueryBuilder = new StringBuilder(getQuery("count(*) "));

        appendQueryBuilder(search, countQueryBuilder, " %s %sp.%s%s %s ?%s");


        Query countQuery = em.createQuery(countQueryBuilder.toString());
        setValueParams(search, countQuery);
        System.out.println(Math.ceil(((long) countQuery.getSingleResult() * 1.0) / pageSize));
        System.out.println(data.size());
        return PageResponse.builder()
                .data(data)
                .totalPage((long) Math.ceil(((long) countQuery.getSingleResult() * 1.0) / pageSize))
                .pageNo(pageNo)
                .totalElement(data.size())
                .build();
    }

    public PageResponse<?> getPageDataPromotion(int pageNo, int pageSize, String[] search, String[] sort) {

//        Class<Product> productClass = Product.class;
//        Class<ProductPrice> productPriceClass = ProductPrice.class;

        StringBuilder queryBuilder = new StringBuilder(getPromotionQuery("new com.example.shop.dtos.responses.product.ProductUserResponse(p, pp.discount, pp.discountPrice, pp.expiredDate)"));
        appendQueryBuilder(search, queryBuilder, " %s %sp.%s%s %s ?%s");
        sortBy(queryBuilder, " order by p.%s %s", sort);
        TypedQuery<ProductUserResponse> query = em.createQuery(queryBuilder.toString(), ProductUserResponse.class);
        query.setFirstResult((pageNo - 1) * pageSize);
        query.setMaxResults(pageSize);
        setValueParams(search, query);

        var data = query.getResultList();

        // count element
        StringBuilder countQueryBuilder = new StringBuilder(getPromotionQuery("count(*) "));

        appendQueryBuilder(search, countQueryBuilder, " %s %sp.%s%s %s ?%s");


        Query countQuery = em.createQuery(countQueryBuilder.toString());
        setValueParams(search, countQuery);

        return PageResponse.builder()
                .data(data)
                .totalPage((long) Math.ceil(((long) countQuery.getSingleResult() * 1.0) / pageSize))
                .pageNo(pageNo)
                .totalElement(data.size())
                .build();
    }


}
