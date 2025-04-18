package hhh.irp2;

public class MyArray {
    private String[] arr = new String[4];
    private int size = 0;

    public String[] getArr() {
        return arr;
    }

    public int getSize() {
        return size;
    }

    public void add(String name){
        if( !contains(name) ) {
            arr[size] = name;
            size++;
        }
    }

    private boolean contains(String name){
        for (int i = 0; i < size; i++) {
            if(arr[i].contains(name)){
                return true;
            }
        }
        return false;
    }
}
