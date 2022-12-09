package Domain.ADT;

import java.util.*;

public class MyDictionary<K, V> implements MyIDictionary<K, V> {
    HashMap<K, V> map;

    public MyDictionary(){
        map = new HashMap<K, V>();
    }
    @Override
    public V lookup(K key) {
        return map.get(key);
    }

    @Override
    public void put(K k, V v) {
        map.put(k, v);
    }

    @Override
    public boolean isVarDef(K key) {
        return map.containsKey(key);
    }

    @Override
    public void update(K key, V value) {
        map.put(key, value);
    }

    public String toString(){
        StringBuilder str = new StringBuilder();
        for (K key: map.keySet())
            str.append("    " + key.toString() + " --> " + lookup(key).toString() + "\n");
        return str.toString();
    }

    @Override
    public void delete(K key) {
        map.remove(key);
    }

    @Override
    public Set<K> getKeys() {
        return map.keySet();
    }

    @Override
    public Map<K, V> getContent() {
        return map;
    }

    @Override
    public MyIDictionary<K, V> cloneH() {
        MyIDictionary<K, V> clone = new MyDictionary<K, V>();
        for (K e : map.keySet())
            clone.put(e, lookup(e));
        return clone;
    }
}
