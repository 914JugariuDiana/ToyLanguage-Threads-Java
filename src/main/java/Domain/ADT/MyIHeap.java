package Domain.ADT;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public interface MyIHeap<K, V> {
    V lookup(K key);
    void put(K k, V v);
    boolean isVarDef(K key);
    void update(K key, V value);
    String toString();
    void delete(K key);
    Set<K> getKeys();
    void setContent(Map<K, V> m);
    Map<K, V> getContent();
}


