package com.blue.bluearchive.report.dto;

import com.blue.bluearchive.board.entity.CommentsComment;
import com.blue.bluearchive.report.entity.Report;
import lombok.Data;
import lombok.ToString;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotBlank;


@Data
public class ReportCommentsCommentFormDto {
    @NotBlank(message = "내용은 필수 입니다")
    private String reportContent;
    @NotBlank(message = "카테고리 선택은 필수 입니다")
    private String reportCategory;
    private String targetCreatedBy;
    private CommentsComment commentsComment;


    private static ModelMapper modelMapper = new ModelMapper();
    public Report createReport(){
        Report report = new Report();
        report.setReportId(0);
        report.setBoard(null);
        report.setComment(null);
        report.setReportContent(this.reportContent);
        report.setReportCategory(this.reportCategory);
        report.setTargetCreatedBy(this.targetCreatedBy);
        report.setCommentsComment(this.commentsComment);
        return report;
    }

    public static ReportCommentFormDto of(Report report){
        return modelMapper.map(report, ReportCommentFormDto.class);
    }
}