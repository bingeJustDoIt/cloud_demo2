package com.example.common;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

//@SpringBootTest
@Slf4j
class CommonApplicationTests {
    public static void main(String[] args) {
        CommonApplicationTests commonApplicationTests = new CommonApplicationTests();
        List<List<Integer>> lists = commonApplicationTests.combinationSum2(new int[]{10, 1, 2, 7, 6, 1, 5}, 8);
        System.out.println(lists);
    }

   /* LinkedList<Integer> path = new LinkedList<>();
    List<List<Integer>> ans = new ArrayList<>();
    boolean[] used;
    int sum = 0;

    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        used = new boolean[candidates.length];
        // 加标志数组，用来辅助判断同层节点是否已经遍历
        Arrays.fill(used, false);
        // 为了将重复的数字都放到一起，所以先进行排序
        Arrays.sort(candidates);
        backTracking(candidates, target, 0);
        return ans;
    }

    private void backTracking(int[] candidates, int target, int startIndex) {
        if (sum == target) {
            ans.add(new ArrayList(path));
        }
        for (int i = startIndex; i < candidates.length; i++) {
            if (sum + candidates[i] > target) {
                break;
            }
            // 出现重复节点，同层的第一个节点已经被访问过，所以直接跳过
            if (i > 0 && candidates[i] == candidates[i - 1] && !used[i - 1]) {
                continue;
            }
            used[i] = true;
            sum += candidates[i];
            path.add(candidates[i]);
            // 每个节点仅能选择一次，所以从下一位开始
            backTracking(candidates, target, i + 1);
            used[i] = false;
            sum -= candidates[i];
            path.removeLast();
        }
    }*/


    private LinkedList<Integer> path = new LinkedList<>();
    private Integer sum = 0;
    private boolean[] used;
    List<List<Integer>> result = new ArrayList<>();


    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        used = new boolean[candidates.length];
        Arrays.fill(used, false);
//        int[] array = Arrays.stream(candidates).sorted().toArray();
        Arrays.sort(candidates);
        log.info("排序后：{}", candidates);
        backTracking(candidates, target, 0);
        log.info("结果:{}", result);
        return result;
    }

    private void backTracking(int[] candidates, int target, int start) {
        if (sum == target) {
            result.add(new ArrayList<>(path));
            return;
        }
        for (int i = start; i < candidates.length; i++) {
            if (sum + candidates[i] > target) {
                continue;
            }
            if (i - 1 >= 0 && candidates[i - 1] == candidates[i] && !used[i - 1]) {
                continue;
            }
            used[i] = true;
            sum += candidates[i];
            path.add(candidates[i]);
            backTracking(candidates, target, i + 1);
            sum -= candidates[i];
            path.removeLast();
            used[i] = false;
        }
    }

}
