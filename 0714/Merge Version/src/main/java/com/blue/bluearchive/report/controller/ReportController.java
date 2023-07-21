package com.blue.bluearchive.report.controller;


import com.blue.bluearchive.report.dto.ReportBoardFormDto;
import com.blue.bluearchive.report.dto.ReportCommentFormDto;
import com.blue.bluearchive.report.dto.ReportCommentsCommentFormDto;
import com.blue.bluearchive.report.dto.ReportdDto;
import com.blue.bluearchive.report.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping(value = "/report/board/{boardId}")
    public String openReportBoard(@PathVariable int boardId, Model model) {
        Map<String, String> reportBoard;
        reportBoard=reportService.openReportBoard(boardId);
        model.addAttribute("reportMap", reportBoard);
        return "report/reportBoard";
    }

    @PostMapping(value = "/report/board/new")
    public String reportBoardNew(@Valid ReportBoardFormDto reportBoardFormDto, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()){
            System.out.println("================================신고 유효성 추가 예정==============================");
            model.addAttribute("errorMessage","신고 유효성 오류");
            return "/report/reportFalse";
        }
        try {
//            System.out.println(reportBoardFormDto);
            reportService.saveReportBoard(reportBoardFormDto);
        }catch (Exception e){
            e.printStackTrace();
            model.addAttribute("errorMessage","신고 등록 중 오류");
            return "/report/reportFalse";
        }
        return "report/reportSuccess";
    }

    @GetMapping(value = "/report/comment/{commentId}")
    public String openReportComment(@PathVariable int commentId, Model model) {
        ReportdDto report;
        report=reportService.openReportComment(commentId);
        model.addAttribute("reportMap", report);
        return "report/reportComment";
    }

    @PostMapping(value = "/report/comment/new")
    public String reportCommentNew(@Valid ReportCommentFormDto reportCommentFormDto, BindingResult bindingResult, Model model) {
        System.out.println("===========댓글 신고 컨트롤러 진입================");
        if(bindingResult.hasErrors()){
            System.out.println("================================신고 유효성 추가 예정==============================");
            model.addAttribute("errorMessage","신고 유효성 오류");
            return "/report/reportFalse";
        }
        try {
//            System.out.println(reportCommentFormDto);
            reportService.saveReportComment(reportCommentFormDto);
        }catch (Exception e){
            e.printStackTrace();
            model.addAttribute("errorMessage","신고 등록 중 오류");
            return "/report/reportFalse";
        }
        return "report/reportSuccess";
    }


    @GetMapping(value = "/report/commentsComment/{commentsCommentId}")
    public String openReportCommentsComment(@PathVariable int commentsCommentId, Model model) {
        ReportdDto report;
        report=reportService.openReportCommentsComment(commentsCommentId);
        model.addAttribute("reportMap", report);
        return "report/reportCommentsComment";
    }

    @PostMapping(value = "/report/commentsComment/new")
    public String reportCommentsCommentNew(@Valid ReportCommentsCommentFormDto reportCommentsCommentFormDto, BindingResult bindingResult, Model model) {
        System.out.println("===========대댓글 신고 컨트롤러 진입================");
        if(bindingResult.hasErrors()){
            System.out.println("================================신고 유효성 추가 예정==============================");
            model.addAttribute("errorMessage","신고 유효성 오류");
            return "/report/reportFalse";
        }
        try {
//            System.out.println(reportCommentsCommentFormDto);
            reportService.saveReportCommentsComment(reportCommentsCommentFormDto);
        }catch (Exception e){
            e.printStackTrace();
            model.addAttribute("errorMessage","신고 등록 중 오류");
            return "/report/reportFalse";
        }
        return "report/reportSuccess";
    }


}
