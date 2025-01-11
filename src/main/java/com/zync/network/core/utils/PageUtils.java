package com.zync.network.core.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;


public class PageUtils {
    public static Pageable of(int limit, int offset){
        return PageRequest.of(getPageNumber(limit, offset), limit);
    }

    public static Pageable of(int limit, int offset, Sort sort){
        return PageRequest.of(getPageNumber(limit, offset), limit, sort);
    }

    private static int getPageNumber(int limit, int offset){
        if (limit == 0) return 1;
        return offset/limit;
    }
}
