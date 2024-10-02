package com.example.shortlink.admin.dto.req;

import lombok.Data;

/**
 * 短链接分组添加请求参数
 */
@Data
public class ShortLinkGroupAddReq {

    /**
     * 分组名称
     */
    private String name;
}
