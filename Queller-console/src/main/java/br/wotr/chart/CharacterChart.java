package br.wotr.chart;

import br.wotr.bot.Main;
import br.wotr.util.ConstantUtil;
import br.wotr.util.DiceUtil;

public class CharacterChart extends Chart {

	public String runChart(char strat, String die, int entry) {
		super.runChart(strat, die, entry);

		switch (strategy) {
		/*
		 ********************************
		 * Character Chart - Corruption *
		 ********************************
		 */
		case 'c':
			// Entry points
			if (entryPoint == 1) {
				trunk = 1;
			}
			if (entryPoint == 2) {
				trunk = 3;
			}

			// Decide action
			while (chartResult == null) {
				if (trunk == 1) {
					mu.q("AGGRESSIVE army against adjacent TARGET AND Has Witch-king or max. possible leadership?");
					if (Main.yes()) {
						chartResult = mu.a(ConstantUtil.getAttack());
						continue;
					}
					trunk = 2;
				}
				if (trunk == 2) {
					mu.q("MOBILE army with leadership AND A valid move/attack towards nearest available TARGET?");
					if (Main.yes()) {
						chartResult = mu.a(ConstantUtil.getArmy4());
						continue;
					}
					trunk = 3;
				}
				if (trunk == 3) {
					mu.q("Nazg�l OR Witch-king in play?");
					if (!Main.yes()) {
						chartResult = mu.a(ConstantUtil.getEventcdie());
						continue;
					} else {
						branch = 1;
					}
					if (branch == 1) {
						mu.q("Witch-king not in an AGGRESSIVE army and can join/create one?");
						if (Main.yes()) {
							fu.printPriorities("CharacterChart-Witch-king");
						}
						branch = 2;
					}
					if (branch == 2) {
						mu.q("Nazg�l not in FSP region and can move there?");
						if (Main.yes()) {
							fu.printPriorities("CharacterChart-Nazgul");
						}
						branch = 3;
					}
					if (branch == 3) {
						mu.q("Nazg�l not in an AGGRESSIVE army and can join/create one?");
						if (Main.yes()) {
							fu.printPriorities("CharacterChart-Nazgul");
						}
						branch = 4;
					}
					if (branch == 4) {
						mu.q("Mouth of Sauron not in an AGGRESSIVE OR MOBILE army?");
						if (Main.yes()) {
							fu.printPriorities("CharacterChart-MouthOfSauron");
						}
						branch = 5;
					}
					if (branch == 5) {
						mu.q("Die has been used?");
						if (Main.yes()) {
							chartResult = mu
									.a(ConstantUtil.getMoveminionsandnazgul());
						} else {
							chartResult = mu.a(ConstantUtil.getEventcdie());
						}
						continue;
					}
				}
			}

			// Corruption - Verify which actions consume the die
			if (chartResult.equals(ConstantUtil.getMoveminionsandnazgul())) {
				chartResult = DiceUtil.useDie(dieToUse);
			}
			break;
		/*
		 ******************************
		 * Character Chart - Military *
		 ******************************
		 */
		case 'm':
			// Entry points
			if (entryPoint == 1) {
				trunk = 1;
			}

			// Decide action
			while (chartResult == null) {
				if (trunk == 1) {
					mu.q("Nazg�l OR Witch-king in play?");
					if (Main.yes()) {
						branch = 1;
						if (branch == 1) {
							mu.q("Witch-king not in AGGRESSIVE army?");
							if (Main.yes()) {
								fu.printPriorities("Chart-C-C-WKplacement");
							}
							branch = 2;
						}
						if (branch == 2) {
							mu.q("Nazg�l not in an AGGRESSIVE army and can join/create one?");
							if (Main.yes()) {
								fu.printPriorities("Chart-C-C-Nazgulplacement");
							}
							branch = 3;
						}
						if (branch == 3) {
							mu.q("Mouth of Sauron not in AGGRESSIVE OR MOBILE army?");
							if (Main.yes()) {
								fu.printPriorities(
										"Chart-C-C-MouthofSauronplacement");
							}
						}
						if (branch == 4) {
							mu.q("Die has been used?");
							if (Main.yes()) {
								chartResult = mu.a(
										ConstantUtil.getMoveminionsandnazgul());
								continue;
							}
						}
					}
					trunk = 2;
				}
				if (trunk == 2) {
					mu.q("Army AGGRESSIVE AND Has Witch-king or Max. possible leadership?");
					if (Main.yes()) {
						chartResult = mu.a(ConstantUtil.getAttack());
						continue;
					}
					trunk = 3;
				}
				if (trunk == 3) {
					mu.q("MOBILE army with leadership AND A valid Move/Attack towards nearest available TARGET?");
					if (Main.yes()) {
						chartResult = mu.a(ConstantUtil.getArmy3());
						continue;
					}
					chartResult = mu.a(ConstantUtil.getEventcdie());
				}

			}

			// Military - Verify which actions consume the die
			if (chartResult.equals(ConstantUtil.getMoveminionsandnazgul())) {
				chartResult = DiceUtil.useDie(dieToUse);
			}
			break;
		default:
			break;
		}
		return chartResult;
	}
}
