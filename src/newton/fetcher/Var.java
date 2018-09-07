/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newton.fetcher;

/**
 *
 * @author Meghdut Mandal
 */
public class Var<T> {

    /**
     *
     */
    public T obj = null;

    @Override
    public String toString() {
        return obj.toString();
    }

    @Override
    public int hashCode() {
        return obj.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return this.obj.equals(obj);
    }

}
