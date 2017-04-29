package com.ptun.app.controllers.dataoperations;

import com.ptun.app.apis.enpoints.models.Scan;
import com.ptun.app.apis.enpoints.models.User;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Lenovo on 4/29/2017.
 */

@Data
public class DataScanLogOperations {
    private List<Scan> scanLogs;

    public DataScanLogOperations(List<Scan> allScanLogs) {
        this.scanLogs = allScanLogs;
    }

    public Map<String, Map<String, List<Scan>>> groupByDateThenPIN() {
        return getScanLogs()
                .stream()
                .collect(Collectors
                        .groupingBy(Scan::getScanDateString, Collectors
                                .groupingBy(Scan::getPIN)));
    }



}
