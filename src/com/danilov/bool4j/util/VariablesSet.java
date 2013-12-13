package com.danilov.bool4j.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class VariablesSet {
	
	private HashMap<String, Boolean> variables;
	private List<String> keySet;
	private int counter;
	
	public VariablesSet(final Set<String> keySet) {
		this.keySet = new ArrayList<String>();
		init(keySet);
	}
	
	public VariablesSet(final List<String> keySet) {
		this.keySet = new ArrayList<String>();
		init(keySet);
	}
	
	public HashMap<String, Boolean> get() {
		return variables;
	}
	
	public void moveToNext() {
		increment();
	}
	
	public boolean notEnd() {
		return counter < Math.pow(2, keySet.size());
	}
	
	public List<String> getKeySet() {
		return keySet;
	}
	
	private void init(final Set<String> keySet) {
		counter = 0;
		variables = new HashMap<String, Boolean>();
		for (String key : keySet) {
			this.keySet.add(key);
			variables.put(key, false);
		}
		Collections.sort(this.keySet);
	}
	
	private void init(final List<String> keyList) {
		counter = 0;
		variables = new HashMap<String, Boolean>();
		keySet = keyList;
		
		for (String key : keyList)
			variables.put(key, false);
		
		Collections.sort(this.keySet);
	}

	private void increment() {
		for (int i = keySet.size() - 1; i >= 0; i--) {
			String key = keySet.get(i);
			Boolean value = variables.get(key);
			if (value) {
				variables.put(key, false);
			} else {
				variables.put(key, true);
				break;
			}
		}
		counter++;
	}
}
