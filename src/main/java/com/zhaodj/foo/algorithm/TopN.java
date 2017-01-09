package com.zhaodj.foo.algorithm;

import java.util.Arrays;
import java.util.Random;

/**
 * Created by zhaodaojun on 2016/12/18.
 * 最小堆实现取topn数据
 */
public class TopN {

    public static void minHeapify(int[] heap, int size, int index){
        int left = (index << 1) + 1;
        int right = (index << 1) + 2;
        int lessIdx = index;
        if(left < size && heap[left] < heap[index]){
            lessIdx = left;
        }
        if(right < size && heap[right] < heap[lessIdx]){
            lessIdx = right;
        }
        if(lessIdx != index){
            //swap
            int temp = heap[index];
            heap[index] = heap[lessIdx];
            heap[lessIdx] = temp;
            //next child
            minHeapify(heap, size, lessIdx);
        }

    }

    public static int[] getTop(int[] src, int n){
        int size = n > src.length ? src.length : n;
        int[] heap = new int[size];
        for(int i = 0; i < heap.length; i++){
            heap[i] = src[i];
        }
        int lastParent = (heap.length - 1 - 1) >> 1;
        for(int k = lastParent; k >= 0; k--){
            minHeapify(heap, heap.length, k);
        }
        for(int j = heap.length; j < src.length; j++){
            if(src[j] > heap[0]){
                heap[0] = src[j];
                minHeapify(heap, heap.length, 0);
            }
        }
        return heap;
    }

    public static void sort(int[] heap){
        for(int i = heap.length - 1; i > 0; i--){
            int temp = heap[0];
            heap[0] = heap[i];
            heap[i] = temp;
            minHeapify(heap, i, 0);
        }
    }

    public static void main(String[] args){
        Random rand = new Random();
        int len = 10000;
        int[] arr = new int[len];
        for(int i = 0; i < len; i++){
            arr[i] = rand.nextInt(10000);
        }
        System.out.println(Arrays.toString(arr));
        int[] top = getTop(arr, 20);
        System.out.println(Arrays.toString(top));
        sort(top);
        System.out.println(Arrays.toString(top));
    }

}
