package com.ruoci.shortlink.admin.remote.dto.resp;

import lombok.Data;

/**
 * 短链接分组内数量返回实体
 **/
@Data
public class ShortLinkGroupCountQueryRespDTO {

    private String gid;

    private Integer shortLinkCount;


}
