package tick3star;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class GreedyMatcherFAIL implements Matcher {

	@Override
	public int[] getMatching(int[][] weights) {
		int m = weights.length;
		int n = weights[0].length;

		int[] flow = new int[m];
		int[] revFlow = new int[n];

		boolean[] seenL = new boolean[m];
		boolean[] seenR = new boolean[n];
		int[] parentL = new int[m];
		int[] parentR = new int[n];
		Queue<Integer> toExploreL;
		Queue<Integer> toExploreR;
		int v;
		boolean found;
		boolean atLeft;
		List<Integer> possibleEnd;
		int maxAugFlow;
		int temp;
		Arrays.fill(flow, -1);
		Arrays.fill(revFlow, -1);

		while (true) {
			// find a shortest path
			Arrays.fill(seenL, false);
			Arrays.fill(seenR, false);
			Arrays.fill(parentL, -1);
			Arrays.fill(parentR, -1);
			toExploreL = new LinkedList<>();
			toExploreR = new LinkedList<>();
			v = -1;
			atLeft = true;
			found = false;
			possibleEnd = new ArrayList<>();
			maxAugFlow = Integer.MIN_VALUE;

			// initialise the queue
			for (int ir = 0; ir < m; ++ir) {
				if (flow[ir] == -1) {
					toExploreL.offer(ir);
				}
			}

			while (!(toExploreL.isEmpty() && toExploreR.isEmpty()) && !found) {
				if (atLeft) {
					while (!toExploreL.isEmpty()) {
						v = toExploreL.poll();
						for (int j = 0; j < n; ++j) {
							if (!seenR[j] && weights[v][j] != 0 && flow[v] != j) {
								toExploreR.offer(j);
								parentR[j] = v;
								seenR[j] = true;
							}
						}
					}
				} else {
					while (!toExploreR.isEmpty()) {
						v = toExploreR.poll();
						if (revFlow[v] == -1) {
							found = true;
							possibleEnd.add(v);
							break;
						}
						for (int i = 0; i < m; ++i) {
							if (!seenL[i] && weights[i][v] != 0 && flow[i] == v) {
								toExploreL.offer(i);
								parentL[i] = v;
								seenL[i] = true;
							}
						}
					}

					if (found) {
						while (!toExploreR.isEmpty()) {
							v = toExploreR.poll();
							if (revFlow[v] == -1) {
								possibleEnd.add(v);
							}
						}
					}
				}

				atLeft = !atLeft;
			}

			if (!found) {
				return flow;
			}

			int j0;
			for (Integer j : possibleEnd) {
				j0 = j;
				temp = 0;
				atLeft = false;
				while (!(atLeft && parentL[j] == -1)) {
					if (atLeft) {
						temp -= weights[j][parentL[j]];
						j = parentL[j];
					} else {
						temp += weights[parentR[j]][j];
						j = parentR[j];
					}
					atLeft = !atLeft;
				}

				if (temp > maxAugFlow) {
					maxAugFlow = temp;
					v = j0;
				}
			}

			atLeft = false;

			while (!(atLeft && parentL[v] == -1)) {
				if (atLeft) {
					v = parentL[v];
				} else {
					revFlow[v] = parentR[v];
					flow[parentR[v]] = v;
					v = parentR[v];
				}
				atLeft = !atLeft;
			}

		}

	}

	public static void main(String[] args) {
		GreedyMatcherFAIL matching = new GreedyMatcherFAIL();
		int[][] weights = new int[][] { { 10, 5, 1 }, { 6, 8, 3 }, { 2, 4, 0 } };
		// {{0, 0, 0, 5}, {0, 3, 2, 0}, {6, 0, 0, 8}}
		// { { 10, 4, 5 }, { 6, 8, 3 }, { 5, 4, 0 } };
		// {{10, 5, 1}, {6, 8, 3}, {2, 4, 0}}
		int[] ans = matching.getMatching(weights);
		for (int i : ans) {
			System.out.println(i);
		}
	}

}
