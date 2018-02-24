package me.magicsu.api;


public interface Injector<T> {

    void inject(T host, Object source, Finder finder);
}
