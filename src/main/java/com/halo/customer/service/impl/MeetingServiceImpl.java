package com.halo.customer.service.impl;

import com.halo.customer.entity.Meeting;
import com.halo.common.vo.Summary;
import com.halo.customer.mapper.MeetingMapper;
import com.halo.customer.service.IMeetingService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author halo
 * @since 2023-04-14
 */
@Service
public class MeetingServiceImpl extends ServiceImpl<MeetingMapper, Meeting> implements IMeetingService {



    @Override
    public HashMap<String, String[]> getSummary(Integer videoId) {
        HashMap<String,Object> queryMap = new HashMap<>();
        queryMap.put("video_id",videoId);
        List<Meeting> meetings = this.baseMapper.selectByMap(queryMap);
        if (!meetings.isEmpty()) {
            Meeting meeting = meetings.get(0);

            String agenda = meeting.getAgenda();
            if(agenda == null){
                agenda = "";
            }
            String discussion = meeting.getDiscussion();
            if(discussion == null){
                discussion = "";
            }
            String conclusion = meeting.getConclusion();
            if(conclusion == null){
                conclusion = "";
            }
            String next = meeting.getNext();
            if(next == null){
                next = "";
            }
            String url = meeting.getUrl();
            if(url == null){
                url = "";
            }

            String[] agendas = agenda.split("/");
            String[] discussions = discussion.split("/");
            String[] conclusions = conclusion.split("/");
            String[] nexts = next.split("/");
            String[] urls = url.split("/");

            HashMap<String,String[]> resultMap = new HashMap<>();
            resultMap.put("agendas",agendas);
            resultMap.put("discussions",discussions);
            resultMap.put("conclusions",conclusions);
            resultMap.put("nexts",nexts);
            resultMap.put("urls",urls);

            return resultMap;
        }

        return null;
    }

    @Override
    public Integer addSummary(Summary summary) {
        HashMap<String,Object> queryMap = new HashMap<>();
        queryMap.put("video_id",summary.getVideoId());
        List<Meeting> meetings = this.baseMapper.selectByMap(queryMap);
        if(meetings.isEmpty()){
            return 0;
        }
        Meeting meeting = meetings.get(0);
        String summaryType = summary.getSummaryType();
        if(summaryType.equals("agenda")){
            String agenda = meeting.getAgenda();
            if(agenda==null || agenda.equals("")){
                agenda = summary.getFirst() + "$" + summary.getSecond();
            }else{
                agenda = agenda + "/" + summary.getFirst() + "$" + summary.getSecond();
            }
            meeting.setAgenda(agenda);
            this.baseMapper.updateById(meeting);
        }else if(summaryType.equals("discussion")){
            String discussion = meeting.getDiscussion();
            if(discussion==null || discussion.equals("")){
                discussion = summary.getFirst() + "$" + summary.getSecond();
            }else{
                discussion = discussion + "/" + summary.getFirst() + "$" + summary.getSecond();
            }
            meeting.setDiscussion(discussion);
            this.baseMapper.updateById(meeting);
        }else if(summaryType.equals("conclusion")){
            String conclusion = meeting.getConclusion();
            if(conclusion==null || conclusion.equals("")){
                conclusion = summary.getFirst() + "$" + summary.getSecond();
            }else{
                conclusion = conclusion + "/" + summary.getFirst() + "$" + summary.getSecond();
            }
            meeting.setConclusion(conclusion);
            this.baseMapper.updateById(meeting);
        }else if(summaryType.equals("next")){
            String next = meeting.getNext();
            if(next==null || next.equals("")){
                next = summary.getFirst() + "$" + summary.getSecond();
            }else{
                next = next + "/" + summary.getFirst() + "$" + summary.getSecond();
            }
            meeting.setNext(next);
            this.baseMapper.updateById(meeting);
        }else{
            String url = meeting.getUrl();
            if(url==null || url.equals("")){
                url = summary.getFirst() + "$" + summary.getSecond();
            }else{
                url = url + "/" + summary.getFirst() + "$" + summary.getSecond();
            }
            meeting.setUrl(url);
            this.baseMapper.updateById(meeting);
        }
        return 1;
    }

    @Override
    public Integer deleteSummary(String type, String time, String context, String videoId) {
        HashMap<String,Object> queryMap = new HashMap<>();
        queryMap.put("video_id",videoId);
        List<Meeting> meetings = this.baseMapper.selectByMap(queryMap);
        if(meetings.isEmpty()){
            return 0;
        }
        Meeting meeting = meetings.get(0);
        String deleted = time + "$" + context;
        if(type.equals("agenda")){
            String agenda = meeting.getAgenda();
            agenda = handleString(agenda,deleted);
            meeting.setAgenda(agenda);
        }else if(type.equals("discussion")){
            String discussion = meeting.getDiscussion();
            discussion = handleString(discussion,deleted);
            meeting.setDiscussion(discussion);
        }else if(type.equals("conclusion")){
            String conclusion = meeting.getConclusion();
            conclusion = handleString(conclusion,deleted);
            meeting.setConclusion(conclusion);
        }else if(type.equals("next")){
            String next = meeting.getNext();
            next = handleString(next,deleted);
            meeting.setNext(next);
        }else{
            String url = meeting.getUrl();
            url = handleString(url,deleted);
            meeting.setUrl(url);
        }
        this.baseMapper.updateById(meeting);
        return 1;
    }

    public String handleString(String string, String deleted){
        String[] array = string.split("/");
        if(array.length == 1){
            string = "";
        }else{
            for (int i = 0; i < array.length - 1; i++) {
                if(array[i].equals(deleted)){
                    for(int j = i; j < array.length - 1; j++){
                        array[j] = array[j+1];
                    }
                    break;
                }
            }
            string = array[0];
            for(int i = 1; i < array.length - 1; i++){
                string += ("/" + array[i]);
            }
        }
        return string;
    }
}
