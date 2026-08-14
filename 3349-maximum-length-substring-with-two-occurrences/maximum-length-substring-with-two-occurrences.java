class Solution {
    public int maximumLengthSubstring(String s) {

        int[] freq = new int[128];
        int left = 0;
        int maxLength = 0;

        for (int right = 0; right < s.length(); right++) {

            char ch = s.charAt(right);
            freq[ch]++;

            // If any character occurs more than 2 times
            while (freq[ch] > 2) {
                freq[s.charAt(left)]--;
                left++;
            }

            // Current valid window length
            maxLength = Math.max(maxLength, right - left + 1);
        }

        return maxLength;
    }
}