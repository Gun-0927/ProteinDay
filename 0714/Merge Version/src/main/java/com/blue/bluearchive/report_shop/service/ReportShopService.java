package com.blue.bluearchive.report_shop.service;


import com.blue.bluearchive.report_shop.dto.ReportShopDto;
import com.blue.bluearchive.report_shop.entity.ReportShop;
import com.blue.bluearchive.report_shop.repository.ReportShopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReportShopService {
    private final ReportShopRepository reportShopRepository;

    @Transactional(readOnly = false)
    public void report(ReportShopDto reportShopDto) {
        ReportShop reportShop = reportShopDto.createReportShop();
        reportShopRepository.save(reportShop);
    }
}
