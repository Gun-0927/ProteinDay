package com.blue.bluearchive.admin.service;

import com.blue.bluearchive.admin.dto.ReportDto;
import com.blue.bluearchive.admin.dto.ReportPageDto;
import com.blue.bluearchive.admin.repository.AdminCommentRepository;
import com.blue.bluearchive.admin.repository.AdminCommentsCommentRepository;
import com.blue.bluearchive.admin.repository.AdminMemberCareRepository;
import com.blue.bluearchive.board.entity.Board;
import com.blue.bluearchive.board.entity.Comment;
import com.blue.bluearchive.board.entity.CommentsComment;
import com.blue.bluearchive.board.repository.BoardRepository;
import com.blue.bluearchive.board.repository.CommentRepository;
import com.blue.bluearchive.board.repository.CommentsCommentRepository;
import com.blue.bluearchive.member.dto.MemberDto;
import com.blue.bluearchive.member.entity.Member;
import com.blue.bluearchive.member.repository.MemberRepository;
import com.blue.bluearchive.report.entity.Report;
import com.blue.bluearchive.report.repository.ReportBoardRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminReportService {
    private final BoardRepository boardRepository;
    private final AdminCommentRepository commentRepository;
    private final AdminCommentsCommentRepository commentsCommentRepository;
    private final ReportBoardRepository reportBoardRepository;
    private final AdminMemberCareRepository memberCareRepository;

    //승훈 추가
    ModelMapper modelMapper = new ModelMapper();
    public ReportPageDto getReportsForBoard(int boardId, int page, int pageSize) {
        Optional<Board> board = boardRepository.findById(boardId);
        Pageable pageable = PageRequest.of(page - 1, pageSize, Sort.by("regTime").descending());
        Page<Report> reportPage = reportBoardRepository.findByBoardAndReportStatusOrderByRegTimeDesc(board.get(), true, pageable);
        List<ReportDto> reportDtoList = reportPage.map(report -> modelMapper.map(report, ReportDto.class)).getContent();

        ReportPageDto reportPageDto = new ReportPageDto();
        reportPageDto.setReportList(reportDtoList);
        reportPageDto.setCurrentPage(reportPage.getNumber() + 1);
        reportPageDto.setTotalPages(reportPage.getTotalPages());

        return reportPageDto;
    }
    public List<ReportDto> getReportsForBoard(int boardId) {
        Optional<Board> board = boardRepository.findById(boardId);
        List<Report> reportPage = reportBoardRepository.findByBoardAndReportStatusOrderByRegTimeDesc(board.get(), false);
        List<ReportDto> reportDtoList = reportPage.stream().map(report -> modelMapper.map(report, ReportDto.class)).toList();
        return  reportDtoList;
    }
    public List<ReportDto> getReportsForComment(int commentId) {
        Optional<Comment> commentsComment = commentRepository.findById(commentId);
        List<Report> reportPage = reportBoardRepository.findByCommentAndReportStatusOrderByRegTimeDesc(commentsComment.get(), false);
        List<ReportDto> reportDtoList = reportPage.stream().map(report -> modelMapper.map(report, ReportDto.class)).toList();
        return  reportDtoList;
    }
    //페이징 처리
    public ReportPageDto getReportsForComment(int commentId,int page, int pageSize) {
        Optional<Comment> commentsComment = commentRepository.findById(commentId);
        Pageable pageable = PageRequest.of(page - 1, pageSize, Sort.by("regTime").descending());
        Page<Report> reportPage = reportBoardRepository.findByCommentAndReportStatusOrderByRegTimeDesc(commentsComment.get(), true, pageable);
        List<ReportDto> reportDtoList = reportPage.map(report -> modelMapper.map(report, ReportDto.class)).getContent();

        ReportPageDto reportPageDto = new ReportPageDto();
        reportPageDto.setReportList(reportDtoList);
        reportPageDto.setCurrentPage(reportPage.getNumber() + 1);
        reportPageDto.setTotalPages(reportPage.getTotalPages());

        return reportPageDto;
    }
    public ReportPageDto getReportsForCommentsComment(int commentsCommentId, int page, int pageSize) {
        Optional<CommentsComment> commentsComment = commentsCommentRepository.findById(commentsCommentId);
        Pageable pageable = PageRequest.of(page - 1, pageSize, Sort.by("regTime").descending());
        Page<Report> reportPage = reportBoardRepository.findByCommentsCommentAndReportStatusOrderByRegTimeDesc(commentsComment.get(), true, pageable);
        List<ReportDto> reportDtoList = reportPage.map(report -> modelMapper.map(report, ReportDto.class)).getContent();

        ReportPageDto reportPageDto = new ReportPageDto();
        reportPageDto.setReportList(reportDtoList);
        reportPageDto.setCurrentPage(reportPage.getNumber() + 1);
        reportPageDto.setTotalPages(reportPage.getTotalPages());

        return reportPageDto;
    }
    public List<ReportDto> getReportsForCommentsComment(int commentsCommentId) {
        Optional<CommentsComment> commentsComment = commentsCommentRepository.findById(commentsCommentId);
        List<Report> reportPage = reportBoardRepository.findByCommentsCommentAndReportStatusOrderByRegTimeDesc(commentsComment.get(), false);
        List<ReportDto> reportDtoList = reportPage.stream().map(report -> modelMapper.map(report, ReportDto.class)).toList();
        return  reportDtoList;
    }

    @Transactional
    public int confirmReports(List<Integer> reportIds) {
        List<Report> reports = reportBoardRepository.findAllById(reportIds);

        for (Report report : reports) {
            report.setReportStatus(true); // 읽음 상태로 설정
        }

        reportBoardRepository.saveAll(reports);

        return reports.size();
    }
    //유저 처리된 신고수
    public ReportPageDto getNoReportResult(Long id, int page, int pageSize) {
        Optional<Member> member = memberCareRepository.findById(id);
        System.out.println("member의 createdBy의 값 ===="+String.valueOf(member.get().getIdx()));
        List<CommentsComment> commentsComment = commentsCommentRepository.findByCreatedBy(String.valueOf(member.get().getIdx()));
        List<Comment> comments = commentRepository.findByCreatedBy(String.valueOf(member.get().getIdx()));
        List<Board> boards = boardRepository.findByCreatedBy(String.valueOf(member.get().getIdx()));

        Pageable pageable = PageRequest.of(page - 1, pageSize, Sort.by("regTime").descending());

        Page<Report> reportComment = reportBoardRepository.findByCommentInAndReportStatusOrderByRegTimeDesc(comments, true, pageable);
        Page<Report> boardReport = reportBoardRepository.findByBoardInAndReportStatusOrderByRegTimeDesc(boards, true, pageable);
        Page<Report> commentsCommentReport = reportBoardRepository.findByCommentsCommentInAndReportStatusOrderByRegTimeDesc(commentsComment, true, pageable);

        List<ReportDto> reportDtoList = new ArrayList<>();
        reportDtoList.addAll(reportComment.map(report -> modelMapper.map(report, ReportDto.class)).getContent());
        reportDtoList.addAll(boardReport.map(report -> modelMapper.map(report, ReportDto.class)).getContent());
        reportDtoList.addAll(commentsCommentReport.map(report -> modelMapper.map(report, ReportDto.class)).getContent());

        ReportPageDto reportPageDto = new ReportPageDto();
        reportPageDto.setReportList(reportDtoList);
        reportPageDto.setCurrentPage(commentsCommentReport.getNumber() + 1);
        reportPageDto.setTotalPages(commentsCommentReport.getTotalPages());

        return reportPageDto;
    }
    //유저 미처리 신고수
    public List<ReportDto> okReportResult(Long id) {
        Optional<Member> member = memberCareRepository.findById(id);

        List<Board> boards = boardRepository.findByCreatedBy(String.valueOf(member.get().getIdx()));
        List<Report> boardList = reportBoardRepository.findByBoardInAndReportStatusOrderByRegTimeDesc(boards, false);
        List<Comment> comments = commentRepository.findByCreatedBy(String.valueOf(member.get().getIdx()));
        List<Report> commentList = reportBoardRepository.findByCommentInAndReportStatusOrderByRegTimeDesc(comments, false);
        List<CommentsComment> commentsComment = commentsCommentRepository.findByCreatedBy(String.valueOf(member.get().getIdx()));
        List<Report> commentsCommentList = reportBoardRepository.findByCommentsCommentInAndReportStatusOrderByRegTimeDesc(commentsComment, false);
        List<ReportDto> reportDtoList = new ArrayList<>();
        reportDtoList.addAll(boardList.stream().map(report -> modelMapper.map(report, ReportDto.class)).toList());
        reportDtoList.addAll(commentList.stream().map(report -> modelMapper.map(report, ReportDto.class)).toList());
        reportDtoList.addAll(commentsCommentList.stream().map(report -> modelMapper.map(report, ReportDto.class)).toList());

        return  reportDtoList;
    }
}
