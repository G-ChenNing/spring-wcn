package com.github.wangchenning.service;

import com.github.wangchenning.beans.Bean;


@Bean
public class SalaryService {
    public Integer calSalary(Integer experience) {
        return experience * 5000;
    }
}
