package com.github.wangchenning.controllers;

import com.github.wangchenning.beans.AutoWired;
import com.github.wangchenning.service.SalaryService;
import com.github.wangchenning.web.mvc.RequestMapping;
import com.github.wangchenning.web.mvc.Controller;
import com.github.wangchenning.web.mvc.RequestParam;


@Controller
public class SalaryController {
    @AutoWired
    private SalaryService salaryService;

    @RequestMapping("/get_salary.json")
    public Integer getSalary(@RequestParam("name") String name, @RequestParam("experience") String experience) {
        return salaryService.calSalary(Integer.parseInt(experience));
    }

}
