package com.kamp.searchvoter;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class VoterController {
    private final VoterService voterService;

    public VoterController(VoterService voterService) {
        this.voterService = voterService;
    }

    @GetMapping("/")
    public String getHomePage(Model model, @RequestParam(value = "keyword", required = false) String keyword) {
        List<Voter> allVoters = voterService.findAllVoters();
        if (keyword == null || keyword.isBlank()) {
            model.addAttribute("voters", allVoters);
        } else {
            List<Voter> voters = voterService.findAllVoters(keyword);
            model.addAttribute("voters", voters);
        }
        model.addAttribute("keyword", keyword);
        return "home";
    }
}
