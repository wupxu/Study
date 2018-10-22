package com.lexue.bp.admin.v1.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lexue.bp.admin.inf.domain.req.NoticeRequest;
import com.lexue.bp.admin.inf.domain.req.StrategyRequest;
import com.lexue.bp.admin.inf.domain.res.ContentResponse;
import com.lexue.bp.admin.inf.domain.res.NoticeResponse;
import com.lexue.bp.admin.inf.domain.res.StrategyResponse;
import com.lexue.bp.admin.inf.spec.ContentServiceSpec;
import com.lexue.bp.admin.service.ContentService;
import com.lexue.bp.common.domain.ResponseDto;
import com.lexue.bp.common.entity.NoticeEntity;
import com.lexue.bp.common.entity.StrategyEntity;
import com.lexue.bp.common.enums.ContentEnums;
import com.lexue.bp.common.util.ResponseUtil;

@RestController("contentControllerV1")
public class ContentController implements ContentServiceSpec {

    @Autowired
    private ContentService contentService;

    @Override
    public ResponseDto<List<ContentResponse>> getAllContent(@RequestParam(defaultValue = "8193")int moduleId) {
        NoticeEntity notice = contentService.getNotice(moduleId);
        StrategyEntity strategy = contentService.getStrategy(moduleId);
        List<ContentResponse> contentResponseList = new ArrayList<>();

        ContentResponse noticeResponse = new ContentResponse();
        noticeResponse.setId(ContentEnums.NOTICE.getCode());
        if(notice != null){
            noticeResponse.setOperatorName(notice.getOperatorName());
            noticeResponse.setUpdateTime(notice.getUpdateTime());
        }



        ContentResponse strategyResponse = new ContentResponse();
        strategyResponse.setId(ContentEnums.STRATEGY.getCode());
        if (strategy != null) {
            strategyResponse.setOperatorName(strategy.getOperatorName());
            strategyResponse.setUpdateTime(strategy.getUpdateTime());
        }

        contentResponseList.add(noticeResponse);
        contentResponseList.add(strategyResponse);
        return ResponseUtil.generateSuccessResponse(contentResponseList);
    }

    @Override
    public ResponseDto<Void> saveNotice(@RequestBody NoticeRequest noticeRequest) {
        contentService.saveNotice(noticeRequest);
        return ResponseUtil.generateSuccessResponse();
    }

    @Override
    public ResponseDto<Void> saveStrategy(@RequestBody StrategyRequest strategyRequest) {
        contentService.saveStrategy(strategyRequest);
        return ResponseUtil.generateSuccessResponse();
    }

    @Override
    public ResponseDto<NoticeResponse> findNotice(@RequestParam(defaultValue = "8193") int moduleId) {
        return ResponseUtil.generateSuccessResponse(contentService.findNotice(moduleId));
    }

    @Override
    public ResponseDto<StrategyResponse> findStrategy(@RequestParam(defaultValue = "8193") int moduleId) {
        return ResponseUtil.generateSuccessResponse(contentService.findStrategy(moduleId));
    }

}
