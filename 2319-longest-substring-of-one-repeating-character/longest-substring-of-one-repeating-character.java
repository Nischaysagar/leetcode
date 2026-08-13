class Solution {

    int[] leftChar;
    int[] rightChar;
    int[] prefix;
    int[] suffix;
    int[] best;
    int[] len;

    public int[] longestRepeating(String s, String queryCharacters, int[] queryIndices) {

        int n = s.length();

        int size = 4 * n + 5;

        leftChar = new int[size];
        rightChar = new int[size];
        prefix = new int[size];
        suffix = new int[size];
        best = new int[size];
        len = new int[size];

        char[] arr = s.toCharArray();

        // Build segment tree
        build(1, 0, n - 1, arr);

        int k = queryIndices.length;
        int[] ans = new int[k];

        for (int i = 0; i < k; i++) {

            int index = queryIndices[i];
            int ch = queryCharacters.charAt(i) - 'a';

            // Update the character
            arr[index] = queryCharacters.charAt(i);

            update(1, 0, n - 1, index, ch);

            // Root contains the answer for the entire string
            ans[i] = best[1];
        }

        return ans;
    }

    // Build segment tree
    private void build(int node, int l, int r, char[] s) {

        if (l == r) {

            int ch = s[l] - 'a';

            leftChar[node] = ch;
            rightChar[node] = ch;

            prefix[node] = 1;
            suffix[node] = 1;
            best[node] = 1;
            len[node] = 1;

            return;
        }

        int mid = l + (r - l) / 2;

        build(node * 2, l, mid, s);
        build(node * 2 + 1, mid + 1, r, s);

        merge(node);
    }

    // Update one position
    private void update(int node, int l, int r, int index, int ch) {

        if (l == r) {

            leftChar[node] = ch;
            rightChar[node] = ch;

            prefix[node] = 1;
            suffix[node] = 1;
            best[node] = 1;
            len[node] = 1;

            return;
        }

        int mid = l + (r - l) / 2;

        if (index <= mid) {
            update(node * 2, l, mid, index, ch);
        } else {
            update(node * 2 + 1, mid + 1, r, index, ch);
        }

        merge(node);
    }

    // Merge two child nodes
    private void merge(int node) {

        int left = node * 2;
        int right = node * 2 + 1;

        len[node] = len[left] + len[right];

        leftChar[node] = leftChar[left];
        rightChar[node] = rightChar[right];

        // Prefix
        prefix[node] = prefix[left];

        if (prefix[left] == len[left] &&
            leftChar[left] == leftChar[right]) {

            prefix[node] = len[left] + prefix[right];
        }

        // Suffix
        suffix[node] = suffix[right];

        if (suffix[right] == len[right] &&
            rightChar[left] == rightChar[right]) {

            suffix[node] = len[right] + suffix[left];
        }

        // Best answer
        best[node] = Math.max(best[left], best[right]);

        // Combine suffix of left + prefix of right
        if (rightChar[left] == leftChar[right]) {

            best[node] = Math.max(
                best[node],
                suffix[left] + prefix[right]
            );
        }
    }
}