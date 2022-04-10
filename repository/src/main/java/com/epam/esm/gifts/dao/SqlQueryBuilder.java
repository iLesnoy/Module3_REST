package com.epam.esm.gifts.dao;

import java.util.List;
import java.util.Objects;

import static com.epam.esm.gifts.dao.constants.SqlQuery.SEARCH_AND_SORT_QUERY;

public class SqlQueryBuilder {

    private static final String DEFAULT_SORT = "ASC";
    private static final String GIFT_ID = "gift_certificate.id";
    private static final String EMPTY = "";
    private static final String SPACE = " ";
    private static final String COMMA_SIGN = ", ";


    public static String buildCertificateQueryForSearchAndSort(String tagName, String searchPart,
                                                               String description, List<String> sortingFieldList,
                                                               String orderSort) {
        tagName = tagName != null ? tagName : EMPTY;
        searchPart = searchPart != null ? searchPart : EMPTY;

        String queryMainPart = String.format(SEARCH_AND_SORT_QUERY, tagName, searchPart, description);
        StringBuilder resultQuery = new StringBuilder(queryMainPart);

        if (sortingFieldList != null && !sortingFieldList.isEmpty()) {
            sortingFieldList.forEach(field -> resultQuery
                    .append(GIFT_ID)
                    .append(field)
                    .append(SPACE)
                    .append(Objects.requireNonNullElse(orderSort, DEFAULT_SORT))
                    .append(COMMA_SIGN));
            resultQuery.deleteCharAt(resultQuery.length() - 2);
        } else {
            resultQuery.append(GIFT_ID).append(SPACE).append(Objects.requireNonNullElse(orderSort, DEFAULT_SORT));
        }
        return resultQuery.toString();
    }
}
