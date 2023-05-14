package com.github.skremiec.pionteching.onlinegame;

import lombok.val;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Comparator.comparingInt;

class Clans {
    private final List<Clan> clans;

    private final Map<Integer, List<Clan>> clansByNumberOfPlayers;

    Clans(final List<Clan> clans) {
        this.clans = clans.stream()
            .sorted(comparingInt(Clan::getPoints).reversed().thenComparingInt(Clan::getNumberOfPlayers))
            .collect(Collectors.toList());

        this.clansByNumberOfPlayers = this.clans.stream().collect(Collectors.groupingBy(Clan::getNumberOfPlayers));
    }

    Group getNextGroupOf(final int groupCount) {
        val clans = new ArrayList<Clan>();

        val firstClan = getFirstClan();
        clans.add(firstClan);
        int openSpots = groupCount - firstClan.getNumberOfPlayers();

        do {
            val nextClan = getClanWithMaxPlayers(openSpots);

            if (nextClan == null) {
                break;
            }

            pop(nextClan);
            clans.add(nextClan);
            openSpots -= nextClan.getNumberOfPlayers();
        } while (openSpots > 0);

        return new Group(clans);
    }

    private Clan getFirstClan() {
        return pop(clans.get(0));
    }

    private Clan getClanWithMaxPlayers(int maxNumberOfPlayers) {
        for (int i = maxNumberOfPlayers; i > 0; --i) {
            val clans = clansByNumberOfPlayers.getOrDefault(i, Collections.emptyList());

            if (clans.size() > 0) {
                return pop(clans.get(0));
            }
        }

        return null;
    }

    private Clan pop(final Clan clan) {
        if (clan != null) {
            clans.remove(clan);
            clansByNumberOfPlayers.get(clan.getNumberOfPlayers()).remove(clan);
        }

        return clan;
    }

    boolean isEmpty() {
        return clans.isEmpty();
    }
}
