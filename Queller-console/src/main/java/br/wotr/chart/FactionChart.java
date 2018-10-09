package br.wotr.chart;

import br.wotr.bot.Main;
import br.wotr.util.ConstantUtil;
import br.wotr.util.DiceUtil;

public class FactionChart extends Chart {
	/**
	 * Start the flow in the chart
	 * 
	 * @param entry
	 *            The entry point in the chart flow.
	 * @return True if a possible action was executed with the die.
	 */
	public String runChart(char strat, String die, int entry) {
		super.runChart(strat, die, entry);
		// TODO Z - Faction chart incomplete
		switch (strategy) {
		/*
		 ******************************
		 * Faction Chart - Corruption *
		 ******************************
		 */
		case 'c':
			// Entry points
			if (entryPoint == 1) {
				trunk = 1;
			}
			if (entryPoint == 2) {
				trunk = 2;
			}
			if (entryPoint == 3) {
				trunk = 5;
			}
			if (entryPoint == 4) {
				fu.printPriorities("Chart-C-F-PriorityRecruit");
				trunk = 7;
			}

			// Decide action
			while (chartResult == null) {
				if (trunk == 1) {
					mu.q("Holding playable Faction Card");
					if (Main.yes()) {
						fu.printPriorities(
								"Chart-C-F-PriorityPlayableFactionCard");
						chartResult = mu
								.a(ConstantUtil.getPlayarmymustercard());
						continue;
					}
					trunk = 2;
				}
				if (trunk == 2) {
					mu.q("Black Sails on table AND Corsairs in play?");
					if (Main.yes()) {
						mu.q("One or more Corsairs could move to a siege?");
						if (Main.yes()) {
							fu.printPriorities(
									"Chart-C-F-T2-DestinationPriority");
							// TODO K - 2 Moves for Factions too?
							chartResult = mu.a(ConstantUtil.getMove());
							continue;
						}
					}
					trunk = 3;
				}
				if (trunk == 3) {
					// TODO K - Check the rule for this Faction die
					mu.q("Using Play Faction Event Die?");
					if (Main.yes()) {
						chartResult = mu
								.a(ConstantUtil.getContinuedieselection());
					} else {
						chartResult = mu.a(ConstantUtil.getDrawfactioncard());
					}
					continue;
				}
				if (trunk == 5) {
					mu.q("Faction figure in FSP region?");
					if (Main.yes()) {
						fu.printPriorities(
								"Chart-C-F-T5-Y-DestinationPriority");
						chartResult = mu.a(ConstantUtil.getMove());
						continue;
					}
					trunk = 6;
				}
				if (trunk == 6) {
					mu.q("FSP in a Stronghold OR Out To Sea?");
					if (Main.yes()) {
						fu.printPriorities(
								"Chart-C-F-T6-Y-DestinationPriority");
					} else {
						fu.printPriorities(
								"Chart-C-F-T6-N-DestinationPriority");
					}
					chartResult = mu.a(ConstantUtil.getMove());
					continue;
				}
				if (trunk == 7) {
					mu.q("Faction eligible to be brought into play?");
					if (Main.yes()) {
						chartResult = mu
								.a(ConstantUtil.getBringfactionintoplay());
					} else {
						chartResult = mu
								.a(ConstantUtil.getMusterfactionfigures());
					}
					continue;
				}
			}

			// Corruption - Verify which actions consume the die
			if (chartResult.equals(ConstantUtil.getDrawfactioncard())) {
				mu.q("Over 4 Faction Cards?");
				if (Main.yes()) {
					fu.printPriorities("Chart-C-F-DiscardPriority");
					mu.a(ConstantUtil.getDiscard());
				}
				mu.a(ConstantUtil.getEnd());
			}

			if (chartResult.equals(ConstantUtil.getPlayarmymustercard())) {
				mu.q("Will you Play Army Card?");
				if (Main.yes()) {
					chartResult = ConstantUtil.getPlayarmycard();
				} else {
					mu.a("Then it's a Muster Card");
					chartResult = ConstantUtil.getPlaymustercard();
				}
			}

			if (!(chartResult.equals(ConstantUtil.getContinuedieselection())
					&& chartResult
							.equals(ConstantUtil.getPlayarmymustercard()))) {
				chartResult = DiceUtil.useDie(dieToUse);
			}
			break;
		/*
		 ****************************
		 * Faction Chart - Military *
		 ****************************
		 */
		case 'm':
			// Entry points
			if (entryPoint == 1) {
				trunk = 1;
			}
			if (entryPoint == 2) {
				trunk = 2;
			}
			if (entryPoint == 3) {
				trunk = 5;
			}
			if (entryPoint == 4) {
				fu.printPriorities("FactionChartM-EP4");
				trunk = 7;
			}

			// Decide action
			while (chartResult == null) {
				if (trunk == 1) {
					mu.q("Holding playable Faction card?");
					if (Main.yes()) {
						fu.printPriorities("FactionChartM-T1");
						chartResult = mu
								.a(ConstantUtil.getPlayarmymustercard());
						continue;
					}
					trunk = 2;
				}
				if (trunk == 2) {
					mu.q("Black Sails on table AND Corsairs in play?");
					if (Main.yes()) {
						mu.q("One or more Corsairs could move to a siege?");
						if (Main.yes()) {
							fu.printPriorities("FactionChartM-T2");
							chartResult = mu.a(ConstantUtil.getMove());
							continue;
						}
					}
					trunk = 3;
				}
				if (trunk == 3) {
					mu.q("Using Play Faction Event die?");
					if (Main.yes()) {
						chartResult = mu
								.a(ConstantUtil.getContinuedieselection());
					} else {
						chartResult = mu.a(ConstantUtil.getDrawfactioncard());
					}
					continue;
				}
				// trunk 4 inside Draw Faction Card action
				if (trunk == 5) {
					mu.q("Faction figure in FSP region?");
					if (Main.yes()) {
						fu.printPriorities("FactionChartM-T5");
						chartResult = mu.a(ConstantUtil.getMove());
						continue;
					}
					trunk = 6;
				}
				if (trunk == 6) {
					mu.q("FSP in a Stronghold OR Out to sea?");
					if (Main.yes()) {
						fu.printPriorities("FactionChartM-T6Y");

					} else {
						fu.printPriorities("FactionChartM-T6N");
					}
					chartResult = mu.a(ConstantUtil.getMove());
					continue;
				}
				if (trunk == 7) {
					mu.q("Faction eligible to be brought into play?");
					if (Main.yes()) {
						chartResult = mu
								.a(ConstantUtil.getBringfactionintoplay());
					} else {
						chartResult = mu
								.a(ConstantUtil.getMusterfactionfigures());
					}
					continue;
				}
			}

			// Military - Verify which actions consume the die
			if (chartResult.equals(ConstantUtil.getDrawfactioncard())) {
				mu.q("Over 4 Faction Cards?");
				if (Main.yes()) {
					fu.printPriorities("Chart-C-F-DiscardPriority");
					mu.a(ConstantUtil.getDiscard());
				}
				mu.a(ConstantUtil.getEnd());
			}

			if (chartResult.equals(ConstantUtil.getPlayarmymustercard())) {
				mu.q("Will you Play Army Card?");
				if (Main.yes()) {
					chartResult = ConstantUtil.getPlayarmycard();
				} else {
					mu.a("Then it's a Muster Card");
					chartResult = ConstantUtil.getPlaymustercard();
				}
			}

			if (!(chartResult.equals(ConstantUtil.getPlayarmymustercard())
					&& chartResult
							.equals(ConstantUtil.getContinuedieselection()))) {
				chartResult = DiceUtil.useDie(dieToUse);
			}
			break;
		default:
			break;
		}
		return chartResult;
	}

}
