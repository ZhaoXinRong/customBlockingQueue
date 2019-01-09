package com.snowruin.queue;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 自定义实现 queue
 * @author zxm
 * @version 1.0.0
 * @date 2019-01-09 15:30:08
 *
 * @param <E>
 */
public class CustomArrayBlockingQueue<E> implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7810797531598338539L;

	private final LinkedList<E> container;
	
	private final ReentrantLock lock;
	
	private final int count;
	
	private int size; 
	
	private final Condition condition;
	
	
	public CustomArrayBlockingQueue(int count) {
		if(count == 0 ) {
			throw new RuntimeException("初始值不能为0");
		}
		this.container = new LinkedList<>();
		this.lock = new ReentrantLock();
		this.condition = this.lock.newCondition();
		this.count = count;
	}
	
	
	public void put(E e) {
		final ReentrantLock lock =  this.lock;
		lock.lock();
		try {
			while(count <= size) {
				condition.await();
			}
			if(count > size) {
				 container.add(e);
				 size ++;
				 condition.signal();
			} 
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}finally {
			lock.unlock();
		}
	}
	
	
	public E remove(E e) {
		final ReentrantLock  lock =  this.lock;
		lock.lock();
		try {
			while( size == 0 ) {
				try {
					condition.await();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
			if(!container.contains(e)) {
				throw new RuntimeException("没有该元素");
			}
			if(container.remove(e)) {
				size --;
			}
			return e;
		} catch (Exception e2) {
			e2.printStackTrace();
		}finally {
			lock.unlock();
		}
		return null;
	}
	
	
	public E get() {
		final ReentrantLock lock =  this.lock;
		lock.lock();
		try {
			int size2 = container.size();
			System.out.println("长度："+size2);
			if(size2 > 0 ) {
				//E e =  container.remove((size2-1));
				E e = container.removeFirst();
				size --;
				return e;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			lock.unlock();
		}
		return null;
	}
	
	
	public int size() {
		return container.size();
	}

	public String toString() {
		return this.container.toString();
	}
	
	public boolean isEmpty() {
		return this.container.isEmpty();
	}
	
	public boolean notEmpty() {
		return !this.isEmpty();
	}
	
	public void clear() {
		final ReentrantLock lock =  this.lock;
		lock.lock();
		try {
			this.container.clear();
			this.size=0;
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			lock.unlock();
		}
	}
	
}
