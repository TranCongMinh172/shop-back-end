package com.example.shop.repositories.customizations;

import com.example.shop.dtos.responses.PageResponse;
import com.example.shop.models.enums.OrderStatus;
import com.example.shop.models.enums.Status;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class BaseCustomizationRepository<T> {
    @PersistenceContext
    protected EntityManager em;
    protected final Class<T> entityClass;
    protected static final Pattern FILTER_PATTERN = Pattern.compile("(.*?)([<>]=?|:|-|!)([^-]*)-?(or)?");
    protected static final Pattern SORT_PATTERN = Pattern.compile("(\\w+?)(:)(asc|desc)");

    protected BaseCustomizationRepository(Class<T> entityClass) {
        this.entityClass = entityClass;
    }
    protected PageResponse<?> getPageData(int pageNo, int pageSize, String[] search, String[] sort){
        String sql = String.format("select o from %s o where 1=1",entityClass.getName());
        StringBuilder queryBuilder = new StringBuilder(sql);
        appendQueryBuilder(search, queryBuilder, " %s %so.%s%s %s ?%s");
        sortBy(queryBuilder, " order by o.%s %s", sort);
        Query query = em.createQuery(queryBuilder.toString());
        query.setFirstResult((pageNo - 1) * pageSize);
        query.setMaxResults(pageSize);
        setValueParams(search, query);

        String sqlCount = String.format("select count(*) from %s o where 1=1", entityClass.getName());
        StringBuilder countQuery = new StringBuilder(sqlCount);
        appendQueryBuilder(search, countQuery, " %s %so.%s%s %s ?%s");

        Query queryCount = em.createQuery(countQuery.toString());
        setValueParams(search, queryCount);

        var data = query.getResultList();
        return PageResponse.builder()
                .data(data)
                .totalPage((long) Math.ceil(((long) queryCount.getSingleResult() * 1.0) / pageSize))
                .pageNo(pageNo)
                .totalElement(data.size())
                .build();
    }

    protected void appendQueryBuilder(String[] search, StringBuilder queryBuilder, String queryFormat) {
        if(search != null) {
            for(String s : search) {
                Matcher matcher = FILTER_PATTERN.matcher(s);
                if(matcher.find()) {
                    String lower = "";
                    String lowerClose = "";
                    if((OperatorQuery.getOperator(matcher.group(2)).equals("like"))) {
                        lower = "lower(";
                        lowerClose = ")";
                    }
                    String operator = OperatorQuery.getOperator(matcher.group(2));
                    String format = String.format(queryFormat, matcher.group(4) != null ? "or" : "and",
                            lower, matcher.group(1), lowerClose, operator,
                            Arrays.stream(search).toList().indexOf(s) + 1);
                    queryBuilder.append(format);
                }
            }
        }
    }


    protected void sortBy(StringBuilder queryBuilder, String queryFormat, String... sort) {
        if (sort != null) {
            for (String s : sort) {
                Matcher matcher = SORT_PATTERN.matcher(s);

                if (matcher.find()) {
                    String sortBy = String.format(queryFormat, matcher.group(1), matcher.group(3));
                    queryBuilder.append(sortBy);
                }
            }
        }
    }
    protected void setValueParams(String[] search, Query query) {
        if (search != null) {
            for (String s : search) {
                Matcher matcher = FILTER_PATTERN.matcher(s);
                if (matcher.find()) {
                    String operator = OperatorQuery.getOperator(matcher.group(2));
                    if (!operator.isEmpty()) {
                        var value = matcher.group(3);
                        if (OperatorQuery.getOperator(matcher.group(2)).equals("like")) {
                            value = String.format("%%%s%%", value.toLowerCase());
                        }
                        if(matcher.group(1).equals("productStatus")) {
                            query.setParameter(Arrays.stream(search).toList().indexOf(s) + 1, Status.valueOf(value));
                        } else if(matcher.group(1).equals("orderStatus")) {
                            query.setParameter(Arrays.stream(search).toList().indexOf(s) + 1, OrderStatus.valueOf(value));
                        } else {
                            query.setParameter(Arrays.stream(search).toList().indexOf(s) + 1, value);
                        }

                    }
                }
            }
        }
    }
}