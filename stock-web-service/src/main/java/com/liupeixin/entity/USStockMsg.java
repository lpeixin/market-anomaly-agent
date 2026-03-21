package com.liupeixin.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

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
    private Integer count_24h;
}
