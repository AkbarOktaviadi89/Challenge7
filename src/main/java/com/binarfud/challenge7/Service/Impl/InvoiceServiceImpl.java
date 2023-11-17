package com.binarfud.challenge7.Service.Impl;

import com.binarfud.challenge7.Model.Response.OrderDetailResponse;
import com.binarfud.challenge7.Service.InvoiceService;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class InvoiceServiceImpl implements InvoiceService {

    @Override
    public byte[] generateInvoice(String username) {
        try {
            log.info("Generating invoice for {}", username);

            List<OrderDetailResponse> orderDetails = Arrays.asList(
                    OrderDetailResponse.builder().productName("Kwetiaw").price("Rp. 10.000").quantity(2L).build(),
                    OrderDetailResponse.builder().productName("Mie Ayam").price("Rp. 12.000").quantity(2L).build(),
                    OrderDetailResponse.builder().productName("Es Teh Manis").price("Rp. 5.000").quantity(4L).build()
            );

            Map<String, Object> parameterMap = new HashMap<>();
            parameterMap.put("username", username);
            parameterMap.put("finalPrice", "Rp. 27.000");
            parameterMap.put("orderDetail", orderDetails);

            JasperPrint invoice = JasperFillManager.fillReport(
                    JasperCompileManager.compileReport(ResourceUtils.getFile("classpath:Invoice.jrxml").getAbsolutePath()),
                    parameterMap,
                    new JRBeanCollectionDataSource(orderDetails)
            );

            return JasperExportManager.exportReportToPdf(invoice);
        } catch (FileNotFoundException e) {
            log.error("JasperReports template file not found: " + e.getMessage(), e);
            throw new RuntimeException("JasperReports template file not found.", e);
        } catch (JRException e) {
            log.error("Error generating JasperReports invoice: " + e.getMessage(), e);
            throw new RuntimeException("Error generating JasperReports invoice.", e);
        }
    }
}
