package com.kamp.searchvoter.impl;


import com.kamp.searchvoter.Voter;
import com.kamp.searchvoter.VoterService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class VoterServiceImpl implements VoterService {
    private final ResourceLoader resourceLoader;
    private boolean isXlsxFileUploaded = false;
    List<Voter> voters = new ArrayList<>();

    public VoterServiceImpl(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public List<Voter> findAllVoters(String keyword) {
        if (keyword != null) {
            return voters.stream().filter(s -> s.getFullName().toLowerCase().startsWith(keyword.toLowerCase())).toList();
        }
        return voters;
    }

    @Override
    public List<Voter> findAllVoters() {
        if (!isXlsxFileUploaded) {
            addVoters();
        }
        return voters;
    }

    private void addVoters() {
        Resource resource = resourceLoader.getResource("classpath:votersdata.xlsx");
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(resource.getInputStream());
            XSSFSheet sheet = workbook.getSheet("Sheet1");

            int rowIndex = 0;
            for (Row row : sheet) {
                if (rowIndex == 0) {
                    rowIndex++;
                    continue;
                }
                Iterator<Cell> cellIterator = row.iterator();
                int cellIndex = 0;
                Voter voter = new Voter();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    switch (cellIndex) {
                        case 0 -> voter.setPartNo((double)cell.getNumericCellValue());
                        case 1 -> voter.setSlNo((double)cell.getNumericCellValue());
                        case 2 -> voter.setFullName(cell.getStringCellValue());
                        case 3 -> voter.setRelationName(cell.getStringCellValue());
                        case 4 -> voter.setAddress(cell.getStringCellValue());
                        case 5 -> voter.setQualification(cell.getStringCellValue());
                        case 6 -> voter.setBusiness(cell.getStringCellValue());
                        case 7 -> voter.setAge((double) cell.getNumericCellValue());
                        case 8 -> voter.setSex(cell.getStringCellValue());
                        case 9 -> voter.setEpicNumber(cell.getStringCellValue());
                        case 10 -> voter.setPhoto(cell.getStringCellValue());
                        default -> {

                        }
                    }
                    cellIndex++;
                }
                voters.add(voter);
            }
            isXlsxFileUploaded = true;
        } catch (IOException e) {
            isXlsxFileUploaded = false;
            throw new RuntimeException(e);
        }
    }
}
