package com.blue.bluearchive.report.dto;


import com.blue.bluearchive.board.entity.Board;
import com.blue.bluearchive.report.entity.Report;
import lombok.Data;
import lombok.ToString;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotBlank;

@Data
@ToString
public class ReportBoardFormDto {
    @NotBlank(message = "내용은 필수 입니다")
    private String reportBoardContent;
    @NotBlank(message = "카테고리 선택은 필수 입니다")
    private String reportBoardCategory;
    private String targetCreatedBy;
    private Board board;


    private static ModelMapper modelMapper = new ModelMapper();
    public Report createReport(){
        Report report = modelMapper.map(this, Report.class);
        report.setReportId(0);
        report.setComment(null);
        report.setCommentsComment(null);
        return report;
    }

    public static ReportBoardFormDto of(Report report){
        return modelMapper.map(report, ReportBoardFormDto.class);
    }
}
