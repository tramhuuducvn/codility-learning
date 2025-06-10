package org.example.interview.AntGroup.question_2;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DetermineSplitWord {

    public static boolean solution(String s, List<String> wordDict) {
        // for cover case s is a sentence that have whitespace
        wordDict.add(" ");
        Set<String> wordSet = new HashSet<>(wordDict);
        boolean[] dp = new boolean[s.length() + 1];

        dp[0] = true; // Empty string is always breakable

        for (int i = 1; i <= s.length(); i++) {
            for (int j = 0; j < i; j++) {
                // Check if s[0...j] is breakable and s[j...i] is in the dict
                if (dp[j] && wordSet.contains(s.substring(j, i))) {
                    dp[i] = true;
                    break;
                }
            }
        }

        return dp[s.length()];
    }

    public static void main(String[] args) {
        // Example 1
        String s1 = "leet code";
        List<String> dict1 = Arrays.asList("leet", "code");
        System.out.println(solution(s1, dict1));

        // Example 2
        String s2 = "applepenapple";
        List<String> dict2 = Arrays.asList("apple", "pen");
        System.out.println(solution(s2, dict2));
    }
}
