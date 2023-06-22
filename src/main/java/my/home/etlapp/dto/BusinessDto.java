package my.home.etlapp.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import my.home.etlapp.entity.BusinessType;

import java.time.LocalDateTime;

public record BusinessDto(
    Long id,
    BusinessType type,
    String businessValue,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy HH:mm:ss")
    LocalDateTime createdAt,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy HH:mm:ss")
    LocalDateTime updatedAt
    ) {
}
