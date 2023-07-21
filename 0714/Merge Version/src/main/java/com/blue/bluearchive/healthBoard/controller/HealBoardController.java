package com.blue.bluearchive.healthBoard.controller;


import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/health")
public class HealBoardController {

    @GetMapping("/healthCalculator")
    public String calculator(){
        return "healthContent/healthCalculator";
    }
    @GetMapping("/bmiCalculator")
    public String bmiCalculator(){
        return "healthContent/healthCalculator2";
    }

    @GetMapping("/consumeCalculator")
    public String consumeCalculator(){
        return "healthContent/healthCalculator3";
    }

    @GetMapping("/1RepMaxCalculator")
    public String _1RepMaxCalculator(){
        return "healthContent/healthCalculator4";
    }

    @GetMapping("/recommendedPage")
    public String recommended_page(){return "healthContent/healthPage";}
    @GetMapping("/exerciseInformationPage")
    public String exerciseInformationPage(){return "healthContent/healthInformation";}

}
