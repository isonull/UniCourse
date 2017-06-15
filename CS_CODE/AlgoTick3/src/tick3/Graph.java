package tick3;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Graph extends GraphBase {

	public Graph(URL url) throws IOException {
		super(url);
	}

	public Graph(String file) throws IOException {
		super(file);
	}

	public Graph(int adj[][]) {
		super(adj);
	}

	@Override
	public List<Integer> getFewestEdgesPath(int src, int target) throws TargetUnreachable {
		LinkedList<Integer> fewestEdgesPath = new LinkedList<>();
		Queue<Integer> toExplore = new LinkedList<>();
		boolean[] seen = new boolean[mN];
		int[] parent = new int[mN];
		boolean found = false;
		Arrays.fill(seen, false);
		Arrays.fill(parent, -1);
		toExplore.offer(src);
		seen[src] = true;

		int v;
		while (!toExplore.isEmpty() && !found) {
			v = toExplore.poll();
			for (int i = 0; i < mN; ++i) {
				if (mAdj[v][i] != 0 && !seen[i]) {
					toExplore.offer(i);
					parent[i] = v;
					seen[i] = true;
					if (i == target) {
						found = true;
						break;
					}
				}
			}
		}

		if (!found) {
			throw new TargetUnreachable();
		} else {
			v = target;
			while (v != src) {
				fewestEdgesPath.offerFirst(v);
				v = parent[v];
			}
			fewestEdgesPath.offerFirst(v);

			return fewestEdgesPath;
		}
	}

	@Override
	public MaxFlowNetwork getMaxFlow(int s, int t) {
		int totalFlow = 0;
		int augFlow;
		List<Integer> path;
		Graph residualCapacity = new Graph(new int[mN][mN]);
		Graph flow = new Graph(new int[mN][mN]);

		while (true) {
			augFlow = Integer.MAX_VALUE;
			residualCapacity.mAdj = new int[mN][mN];

			for (int i = 0; i < mN; ++i) {
				for (int j = 0; j < mN; ++j) {
					if (flow.mAdj[i][j] < mAdj[i][j]) {
						residualCapacity.mAdj[i][j] = mAdj[i][j] - flow.mAdj[i][j];
					} else if (flow.mAdj[j][i] > 0) {
						residualCapacity.mAdj[i][j] = flow.mAdj[j][i];
					}
				}
			}

			try {
				path = residualCapacity.getFewestEdgesPath(s, t);
			} catch (TargetUnreachable e) {
				return new MaxFlowNetwork(totalFlow, this);
			}

			for (int i = 0; i < path.size() - 1; ++i) {
				augFlow = Math.min(augFlow, residualCapacity.mAdj[path.get(i)][path.get(i + 1)]);
			}

			totalFlow += augFlow;
			for (int i = 0; i < path.size() - 1; ++i) {
				if (mAdj[path.get(i)][path.get(i + 1)] > 0) {
					flow.mAdj[path.get(i)][path.get(i + 1)] += augFlow;
				} else {
					flow.mAdj[path.get(i + 1)][path.get(i)] -= augFlow;
				}
			}
		}
	}

	public static void main(String[] args) throws IOException, TargetUnreachable {
		Graph g = new Graph(new URL("http://www.cl.cam.ac.uk/teaching/1617/Algorithms/ticks/maxflow_test.01.in"));
		List<Integer> f = g.getFewestEdgesPath(0, 3);
		g.getMaxFlow(0, 3);
		for (Integer integer : f) {
			System.out.println(integer);
		}
		System.out.println(g.getMaxFlow(0, 3).getFlow());
	}
}
