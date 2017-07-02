package org.natrolite.util;

@FunctionalInterface
public interface Callback<T> {

  void run(T type);
}