package com.storactive.stg.controller;

import com.storactive.stg.service.AlertService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/alerts")
@RequiredArgsConstructor
public class AlertController {

    final AlertService alertSer;

    @RequestMapping({"", "/"})
    public String getIndex() {
        return "alert/index";
    }


}
