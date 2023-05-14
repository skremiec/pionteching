package com.github.skremiec.pionteching.onlinegame;

import lombok.Data;

import java.util.Arrays;
import java.util.List;

@Data
public class Group {
    private final List<Clan> clans;

    public Group(final List<Clan> clans) {
        this.clans = clans;
    }

    public Group(final Clan... clans) {
        this.clans = Arrays.asList(clans);
    }
}
