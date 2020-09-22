package com.michealwang.mqmail.platform.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.michealwang.mqmail.platform.pojo.MsgLog;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MsgLogMapper extends BaseMapper<MsgLog> {


    void updateStatus(MsgLog msgLog);

    List<MsgLog> selectTimeoutMsg();

    void updateTryCount(MsgLog msgLog);

    MsgLog selectByPrimaryKey(String msgId);

}
