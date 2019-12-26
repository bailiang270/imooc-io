package com.bai.socket.suanfa;

import java.util.HashMap;
import java.util.Map;

class Solution {


    /**
     * @DESC:两数之和
     * @param nums
     * @param target
     * @return
     */
    public static int[] twoSum(int[] nums, int target) {

        Map<Integer,Integer> map = new HashMap<>();
        for (int i=0;i<nums.length;i++){
            int complement = target-nums[i];
            if (map.containsKey(complement)){
                return new int[] {map.get(complement),i};
            }
            map.put(nums[i],i);
        }
        throw new IllegalArgumentException("no");
    }


    public static void main(String[] args) {
        int[] array = {1,2,3,4,5,6,7,8};
        int[] ints = twoSum(array, 3);
        System.out.println(ints[0]+""+ints[1]);
    }
}
