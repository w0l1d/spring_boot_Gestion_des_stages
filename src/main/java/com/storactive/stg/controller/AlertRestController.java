package com.storactive.stg.controller;

import com.storactive.stg.service.AlertService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/alert")
@RequiredArgsConstructor
public class AlertRestController {

    final AlertService alertSer;


    @RequestMapping
    public ResponseEntity<?> getIndex() {
        return ResponseEntity.ok(alertSer.getIndex());
    }


}
