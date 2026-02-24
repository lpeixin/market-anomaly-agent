package com.liupeixin.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class USStockRss {

    private String id;
    private String stockCode;
    private String title;
    private String titleCn;
    private String link;
    private LocalDateTime pubDateGmt;
    private LocalDateTime pubDateCn;
    private String tags;
}
