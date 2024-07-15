package com.ruoci.shortlink.project.dto.req;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoci.shortlink.project.dao.entity.ShortLinkDO;
import lombok.Data;

/**
 * 短链接分页请求参数
 **/
@Data
public class ShortLinkPageReqDTO extends Page<ShortLinkDO> {

    private String gid;

}
