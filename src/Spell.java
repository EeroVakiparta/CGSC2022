package src;

import java.util.Scanner;
/*
Spells
        Your team will also acquire 1 point of mana per damage dealt to a monster, even from monsters with no health left.
        Mana is shared across the team and heroes can spend 10 mana points to cast a spell.
        A spell command has parameters, which you must separate with a space.
        command	parameters	effect	range
        WIND	<x> <y>	All entities (except your own heroes) within 1280 units are pushed 2200 units in the direction from the spellcaster to x,y.	1280
        SHIELD	<entityId>	The target entity cannot be targeted by spells for the next 12 rounds.	2200
        CONTROL	<entityId> <x> <y>	Override the target's next action with a step towards the given coordinates.	2200
        A hero may only cast a spell on entities that are within the spell's range from the hero.
*/
class Spell {
    static final String WIND = "WIND";
    static final String SHIELD = "SHIELD";
    static final String CONTROL = "CONTROL";
}