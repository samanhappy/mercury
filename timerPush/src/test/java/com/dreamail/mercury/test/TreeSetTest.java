package com.dreamail.mercury.test;

import java.util.TreeSet;

public class TreeSetTest {
	public static void main(String[] args) {
		TreeSet<Integer> treeSet = new TreeSet<Integer>();
		treeSet.add(336);
		treeSet.add(536);
		treeSet.add(136);
		treeSet.add(136);
		treeSet.add(136);
		System.out.println(treeSet);
		
		treeSet.remove(136);
		System.out.println(treeSet);
	}

}
