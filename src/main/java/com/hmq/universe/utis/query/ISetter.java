package com.hmq.universe.utis.query;

import java.io.Serializable;

@FunctionalInterface
public interface ISetter<T, V> extends Serializable {
    void apply(T target,V value);
}
