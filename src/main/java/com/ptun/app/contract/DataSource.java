package com.ptun.app.contract;

import java.util.List;

/**
 * Created by Lenovo on 4/30/2017.
 */
public interface DataSource {
    List getLocalData();
    List getMachineData();
}
