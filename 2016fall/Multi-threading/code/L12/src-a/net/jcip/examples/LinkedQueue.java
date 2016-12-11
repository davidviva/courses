package net.jcip.examples;

import java.util.concurrent.atomic.*;

/**
 * LinkedQueue
 * <p/>
 * Insertion in the Michael-Scott nonblocking queue algorithm
 *
 * @author Brian Goetz and Tim Peierls
 */
@ThreadSafe
public class LinkedQueue <E> {

	private static class QNode <E> {
		final E item;
		final AtomicReference<QNode<E>> next;

		public QNode(E item, QNode<E> next) {
			this.item = item;
			this.next = new AtomicReference<QNode<E>>(next);
		}
	}

	private final QNode<E> dummy = new QNode<E>(null, null);
	private final AtomicReference<QNode<E>> head
	= new AtomicReference<QNode<E>>(dummy);
	private final AtomicReference<QNode<E>> tail
	= new AtomicReference<QNode<E>>(dummy);

	public boolean put(E item) {
		QNode<E> newNode = new QNode<E>(item, null);
		while (true) {
			QNode<E> curTail = tail.get();
			QNode<E> tailNext = curTail.next.get();
			if (curTail == tail.get()) {
				if (tailNext != null) {
					// Queue in intermediate state, advance tail
					tail.compareAndSet(curTail, tailNext);
				} else {
					// In quiescent state, try inserting new node
					if (curTail.next.compareAndSet(null, newNode)) {
						// Insertion succeeded, try advancing tail
						tail.compareAndSet(curTail, newNode);
						return true;
					}
				}
			}
		}
	}
}
