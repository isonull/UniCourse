package tick3star;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class MaximumMatcher implements Matcher {

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
							break;
						}
						for (int i = 0; i < n; ++i) {
							if (!seenL[i] && weights[i][v] != 0 && flow[i] == v) {
								toExploreL.offer(i);
								parentL[i] = v;
								seenL[i] = true;
							}
						}
					}
				}

				atLeft = !atLeft;
			}

			if (!found) {
				return flow;
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
		Matcher matching = new MaximumMatcher();
		int[][] weights = new int[][] { { 0, 0, 0, 5 }, { 0, 3, 2, 0 }, { 6, 0, 0, 8 } };
		int[] ans = matching.getMatching(weights);
		for (int i : ans) {
			System.out.println(i);
		}
	}
}
