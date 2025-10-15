/*
Brief description of the problem:
Given a string **S** and an array of indices **A**, replace the characters in **S** at positions from **A** with the corresponding letters of the English alphabet (1 → 'a', 2 → 'b', …, 26 → 'z').
If the input violates any rules (invalid lengths, duplicate or out-of-range values, invalid characters, etc.), output `"Invalid inputs"`.
*/
#include <stdio.h>
#include <string.h>

char x[72] = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789()*!@#$%&^";
// Create flag, which will store 1 (if answer is "invalid inputs") or 0 (if all data is correct)
int flag = 0;


void create_output(int fl, int N, char S[N]) {
    // Write text in output.txt
    FILE *f1;
    f1 = fopen("output.txt", "w");
    if (fl == 1) {
        fputs("Invalid inputs\n", f1);
    } else {
        fprintf(f1, "%s\n", S);
    }
    fclose(f1);
}


// function of checking acceptable symbol for S
void check(char c) {
    char *m = " ";
    m = strchr(x, c);
    if (m == NULL) {
        flag = 1;
    }
}


int main() {
    FILE *f;
    int N;
    int M;
    // Read all values from input.txt
    f = fopen("input.txt", "r");
    fscanf(f, "%d", &N);
    char S[N];
    fscanf(f, "%s", S);
    fscanf(f, "%d", &M);
    int A[M];
    for (int ik = 0; ik < M; ik++) {
        fscanf(f, "%d", &A[ik]);
    }
    fclose(f);


    ///// Checking the correctness of inputs
    // Rules of valid input for S
    for (int i = 0; i < strlen(S); i++) {
        // Check that symbol is valid
        check(S[i]);
        if (flag == 1) {
            create_output(flag, N, S);
            return 0;
        }
    }
    // Check that values in A are not distinct:
    char temp;
    for (int p1 = 0; p1 < M-1; p1++) {
        temp = A[p1];
        for (int p2 = p1+1; p2 < M; p2++) {
            if (temp == A[p2]) {
                create_output(flag=1, N, S);
                return 0;
            }
        }
    }
    // Check the condition: N in the range[2, 50]
    if (N < 2 || N > 50) {
        create_output(flag=1, N, S);
        return 0;
    }
    // Find max and min in A to check conditions: A in the range [1, 26] and N <= max A
    int mX = 0;
    int minimum;
    int mN = 1000000000;
    for (int g = 0; g < M; g++) {
        if (A[g] > mX) {
            mX = A[g];
        }
        if (A[g] < mN) {
            mN = A[g];
        }
        // find minimum
        if (N - 1 < 26) {
            minimum = N - 1;
        } else {
            minimum = 26;
        }
        // Check remaining conditions
        if (mX >= N || mX > 26 || mN < 1) {
            create_output(flag=1, N, S);
            return 0;
        }
    }
    // Check another conditions(1 ≤ M ≤ min(N - 1, 26) and the maximum value in A is less than the length of S)
    if (M < 1 || M > minimum || mX >= strlen(S)) {
        create_output(flag=1, N, S);
        return 0;
    }


    // Task
    char alphabet[27] = "abcdefghijklmnopqrstuvwxyz";
    int u = 0;
    // Iterate over all elements of A by index
    while (u < M) {
        int k = A[u];
        char alp = alphabet[k-1];
        // Replace the element corresponding to the index with a letter in the alphabet with the same serial number
        S[k] = alp;
        // Increase the index by 1 to avoid program looping
        u += 1;
    }
    create_output(flag, N, S);
    return 0;
}
