package com.github.skremiec.pionteching.onlinegame;

import lombok.val;

import java.util.ArrayList;
import java.util.List;

public class GroupsCalculator {
    public List<Group> calculate(final int groupCount, final List<Clan> clans) {
        return calculate(groupCount, new Clans(clans));
    }

    private List<Group> calculate(final int groupCount, final Clans clans) {
        val groups = new ArrayList<Group>();

        do {
            val group = clans.getNextGroupOf(groupCount);

            if (group != null) {
                groups.add(group);
            }
        } while (!clans.isEmpty());

        return groups;
    }
}
