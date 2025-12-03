package com.atlashorticole.product_service.utils;

import java.util.Set;

import org.springframework.data.domain.Sort;

public class Utils {

    public static final Set<String> ALLOWED_SORT_FIELDS = Set.of(
            "id", "name", "description", "resume", "dosage",
            "active", "displayOrder", "composition", "category",
            "imageUrl", "technicalSheetUrl"
    );

    public static Sort checkParamSort(String sort) {
        if (sort == null || !sort.contains(",")) {
            throw new IllegalArgumentException("Invalid sort parameter: " + sort);
        }

        String[] parts = sort.split(",");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid sort format: " + sort);
        }

        String field = parts[0].trim();
        String direction = parts[1].trim().toLowerCase();

        if (!ALLOWED_SORT_FIELDS.contains(field)) {
            throw new IllegalArgumentException("Sorting not allowed on field: " + field);
        }

        if (!direction.equals("asc") && !direction.equals("desc")) {
            throw new IllegalArgumentException("Sort direction must be 'asc' or 'desc'");
        }

        return Sort.by(Sort.Order.by(field).with(Sort.Direction.fromString(direction)));
    }
}

