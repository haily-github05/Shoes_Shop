package com.example.application.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class FilterDayByDay {
    private String toDate;
    private String fromDate;


    public String getFromDate() {
        return fromDate;
    }

    public String getToDate() {
        return toDate;
    }

}
