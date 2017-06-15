package test;

import java.util.HashSet;
import java.util.Set;

public class SubscribableStudentInfo extends StudentInfo {
	public SubscribableStudentInfo(String n) {
		super(n);
	}

	private Set<UpdatableTreeSet> mRegistered = new HashSet<UpdatableTreeSet>();

	public void subscribe(UpdatableTreeSet u) {
		mRegistered.add(u);
	}

	public void unsubscribe(UpdatableTreeSet u) {
		mRegistered.remove(u);
	}

	@Override
	public void setCompleted(int n) {
		Set<UpdatableTreeSet> tmp = new HashSet<UpdatableTreeSet>(mRegistered);
		for (UpdatableTreeSet u : tmp) {
			u.beforeUpdate(this);
		}
		nCompleted = n;
		for (UpdatableTreeSet u : tmp) {
			u.afterUpdate(this);
		}
	}
}