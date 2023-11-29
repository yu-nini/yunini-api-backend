package com.yunini.api.common;

import lombok.Data;

import java.io.Serializable;

/**
 * 统一请求
 *

 */
@Data
public class IdRequest implements Serializable {

    /**
     * id
     */
    private Long id;

    private static final long serialVersionUID = 1L;
}