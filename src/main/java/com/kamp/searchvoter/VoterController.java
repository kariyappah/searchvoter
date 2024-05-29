package com.kamp.searchvoter;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class VoterController {
    private final VoterService voterService;

    public VoterController(VoterService voterService) {
        this.voterService = voterService;
    }

    @GetMapping("/")
    public String getHomePage(Model model, @RequestParam(value = "name", required = false) String name,
                              @RequestParam(value = "relation", required = false) String relation) {
        voterService.findAllVoters();
        if (name == null || name.isBlank()) {
//            model.addAttribute("voters", allVoters);
            model.addAttribute("voters", new ArrayList<Voter>());
        } else {
            List<Voter> voters = voterService.findAllVoters(name, relation);
            model.addAttribute("voters", new ArrayList<Voter>());
            model.addAttribute("voters", voters);
        }
        model.addAttribute("name", name);
        model.addAttribute("relation", relation);
        return "home";
    }
}
