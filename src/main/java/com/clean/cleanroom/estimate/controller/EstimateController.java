package com.clean.cleanroom.estimate.controller;

import com.clean.cleanroom.estimate.service.EstimateService;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping ("/api/estimate")
public class EstimateController {

    private EstimateService estimateService;

    public EstimateController(EstimateService estimateService) {
        this.estimateService = estimateService;
    }




}
