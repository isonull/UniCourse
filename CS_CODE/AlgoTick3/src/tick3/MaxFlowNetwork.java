package tick3;

public final class MaxFlowNetwork {
	private final int mFlow;
	private final GraphBase mFlowNetwork;

	public MaxFlowNetwork(int flow, GraphBase g) {
		mFlow = flow;
		mFlowNetwork = g;
	}

	public int getFlow() {
		return mFlow;
	}

	public GraphBase getNetwork() {
		return mFlowNetwork;
	}
}