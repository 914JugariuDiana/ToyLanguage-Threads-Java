package Domain.ADT;

import Domain.Value.Value;

import java.util.Map;
import java.util.Set;

public interface MyIDictionary<K, V>{
    V lookup(K key);
    void put(K k, V v);
    boolean isVarDef(K key);
    void update(K key, V value);
    String toString();
    void delete(K key);
    Set<K> getKeys();
    Map<K, V> getContent();
    MyIDictionary<K, V> cloneH();
}
