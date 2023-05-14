package com.github.skremiec.pionteching.onlinegame;

import lombok.val;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GroupsCalculatorTest {
    private final GroupsCalculator groupsCalculator = new GroupsCalculator();

    @Test
    public void groupMaxSize() {
        val result = groupsCalculator.calculate(4, List.of(
            new Clan(2, 10),
            new Clan(2, 6),
            new Clan(2, 8)
        ));

        assertEquals(
            List.of(
                new Group(
                    new Clan(2, 10),
                    new Clan(2, 8)
                ),
                new Group(
                    new Clan(2, 6)
                )
            ),
            result
        );
    }

    @Test
    public void bestClansGoFirst() {
        val groups = groupsCalculator.calculate(6, List.of(
            new Clan(6, 60),
            new Clan(4, 50),
            new Clan(2, 70)
        ));

        assertEquals(
            List.of(
                new Group(
                    new Clan(2, 70),
                    new Clan(4, 50)
                ),
                new Group(
                    new Clan(6, 60)
                )
            ),
            groups
        );
    }

    @Test
    public void example() {
        val groups = groupsCalculator.calculate(6, List.of(
            new Clan(4, 50),
            new Clan(2, 70),
            new Clan(6, 60),
            new Clan(1, 15),
            new Clan(5, 40),
            new Clan(3, 45),
            new Clan(1, 12),
            new Clan(4, 40)
        ));

        assertEquals(
            List.of(
                new Group(
                    new Clan(2, 70),
                    new Clan(4, 50)
                ),
                new Group(
                    new Clan(6, 60)
                ),
                new Group(
                    new Clan(3, 45),
                    new Clan(1, 15),
                    new Clan(1, 12)
                ),
                new Group(
                    new Clan(4, 40)
                ),
                new Group(
                    new Clan(5, 40)
                )
            ),
            groups
        );
    }
}
