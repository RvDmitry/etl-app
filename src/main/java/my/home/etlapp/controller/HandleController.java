package my.home.etlapp.controller;

import lombok.RequiredArgsConstructor;
import my.home.etlapp.dto.BusinessDto;
import my.home.etlapp.entity.BusinessType;
import my.home.etlapp.service.EventProcessor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Set;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class HandleController {

    private final EventProcessor eventProcessor;
    @GetMapping("/values/created")
    public ResponseEntity<Page<BusinessDto>> pageByCreatedAt(
            @RequestParam("dateFrom") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFrom,
            @RequestParam("dateTo") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateTo,
            Pageable pageable) {
        eventProcessor.loadDataByCreationDate(dateFrom, dateTo, pageable.getPageNumber(), pageable.getPageSize());
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @GetMapping("/values/types")
    public ResponseEntity<Page<BusinessDto>> pageByType(@RequestParam("types") Set<BusinessType> types, Pageable pageable) {
        eventProcessor.loadDataByType(types, pageable.getPageNumber(), pageable.getPageSize());
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

}
