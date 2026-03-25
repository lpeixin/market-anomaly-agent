package com.liupeixin.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class USStockMsg {
    private String stockCode;
    private String title;
    private String titleCn;
    private String pubDate;
    private String tags;
    private Long countOneDay;
    private Long countThreeDays;
    private Long countOneWeek;
}
