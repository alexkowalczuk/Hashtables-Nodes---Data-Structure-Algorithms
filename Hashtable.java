/*
By: Alex Kowalczuk
CS245 - Practice Assigment 11 - Extra Credit
May 12, 2019 
*/

import java.util.*;
import java.util.Set;

public class Hashtable{
	public int size;
	public int length;
	public Hashnode[] array;

	Hashtable(int initialSize) {
		this.size = initialSize;
		this.array = new Hashnode[this.size];
		this.length = 0;
	}
	Hashtable() {
		this.size = 5000;
		this.array = new Hashnode[this.size];
		this.length = 0;
	}

	public String get(String key){
		int index = Math.abs(key.hashCode()) % this.size;

		for (int i = 0; i < this.size; ++i) {
			int computedIndex = (index + i) % this.size;

			if (array[computedIndex] != null &&
				array[computedIndex].deleted == false &&
				array[computedIndex].key.equals(key)) {
				return array[computedIndex].value;
			}
		}
		
		return null;
	}
	
	public boolean containsKey(String key){
		int index = Math.abs(key.hashCode()) % this.size;

		for (int i = 0; i < this.size; ++i) {
			int computedIndex = (index + i) % this.size;

			if (array[computedIndex] != null &&
				array[computedIndex].deleted == false &&
				array[computedIndex].key.equals(key)) {
				return true;
			}
		}
		
		return false;
	}


	public void put(String key, String value){
		if ((double)this.length > (double)this.size*(3.0/4.0)) {
			resize();
		}
		length++;

		int index = Math.abs(key.hashCode()) % this.size;
		Hashnode node;

		for (int i = 0; i < this.size; i++) {
			int computedIndex = (index + i) % this.size;
			node = array[computedIndex];

			if (node == null) {
				 node = new Hashnode(key, value);
				 array[computedIndex] = node;
				 return;
			}
			else if (node.deleted == true) {
				 node.key = key;
				 node.value = value;
				 node.deleted = false; 
				 return;
			}
		}
	}

	public void resize() {
		int newSize = size * 4;

		Hashtable temp = new Hashtable(newSize);

		for (int i = 0; i < this.size; i++) {
			Hashnode node = array[i];
			if (node == null || node.deleted) 
				continue;
			temp.put(node.key, node.value);
		}

		this.array = temp.array;
		this.size = temp.size;
	}

	public String remove(String key){
		int index = Math.abs(key.hashCode()) % this.size;

		for (int i = 0; i < this.size; ++i) {
			int computedIndex = (index + i) % this.size;
			Hashnode node = array[computedIndex];

			if (node == null) {
				return null;
			} else if (node.deleted == false && node.key.equals(key)) {
				node.deleted = true;
				return node.value;
			}
		}

		throw new RuntimeException("No pair found for this key: "+key);
	}
}

class Hashnode{
	public String key;
	public String value;
	public Boolean deleted;

	public Hashnode(String key, String value){
		this.key = key;
		this.value = value;
		this.deleted = false;
	}

	String get_key(){
		return this.key;
	}

	String get_val(){
		return this.value;
	}


	@Override public String toString() {
		return this.key + " => " + this.value;
	}
}