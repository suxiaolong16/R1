package com.halo.customer.service;

import com.halo.customer.entity.Meeting;
import com.baomidou.mybatisplus.extension.service.IService;
import com.halo.common.vo.Summary;

import java.util.HashMap;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author halo
 * @since 2023-04-14
 */
public interface IMeetingService extends IService<Meeting> {

    HashMap<String, String[]> getSummary(Integer videoId);

    Integer addSummary(Summary summary);

    Integer deleteSummary(String type, String time, String context, String videoId);
}
