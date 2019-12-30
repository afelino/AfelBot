package ru.tsirkunov.afelbot;

import java.util.AbstractQueue;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Это спеифическая очередь, которая способна хранить в себе только один
 * элемент.Про попытке засунуть в эту очередь второй элемент до того, как первый
 изъят, первый элемент будет забыт навсегда. Такое поведение позволяет выкинуть излишние данные,
 которые не успели передать в драйвер двигателя.
 * 
 * @author Циркунов Виталий Андреевич
 * @param <E> Класс хранящегося элемента.
 */
public class LastElementQueue <E> extends AbstractQueue<E>{

    private final AtomicReference<E> buffer = new AtomicReference<>();
    
    @Override
    public Iterator<E> iterator() {
        return new LocalIterator();
    }

    @Override
    public int size() {
        return buffer.get() == null ? 0 : 1;
    }

    @Override
    public boolean offer(E e) {
        buffer.set(e);
        return true;
    }

    @Override
    public E poll() {
        return buffer.getAndSet(null);
    }

    @Override
    public E peek() {
        return buffer.get();
    }

    // Итератор. Не нужен для проекта. Сделан только потому что, есть метод
    // возвращающий итератор.
    private class LocalIterator implements Iterator<E> {
        private E next;
        
        // Конструктор, который фиксирует состояние буффера для последующего
        // консистентного поведения.
        private LocalIterator(){
            next = buffer.get();
        }
        
        @Override
        public boolean hasNext() {
            return next != null;
        }

        @Override
        public E next() {
            E ret = next;
            next = null;
            return ret;
        }
    
    }
}
