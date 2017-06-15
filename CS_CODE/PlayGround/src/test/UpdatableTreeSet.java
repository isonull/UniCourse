package test;

import java.util.TreeSet;

public class UpdatableTreeSet extends TreeSet<SubscribableStudentInfo> {
	@Override
	public boolean add(SubscribableStudentInfo s) {
		s.subscribe(this);
		return super.add(s);
	}

	@Override
	public boolean remove(Object s) {
		SubscribableStudentInfo si = (SubscribableStudentInfo) s;
		si.unsubscribe(this);
		return super.remove(s);
	}

	public void beforeUpdate(SubscribableStudentInfo s) {
		remove(s);
	}

	public void afterUpdate(SubscribableStudentInfo s) {
		add(s);
	}
}