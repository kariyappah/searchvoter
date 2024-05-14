package com.kamp.searchvoter;

import java.util.List;

public interface VoterService {
    List<Voter> findAllVoters(String keyword);
    List<Voter> findAllVoters();

}
