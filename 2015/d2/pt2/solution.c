#include <stdio.h>
#include <stdlib.h>

int min(int a, int b);

int main(void) {
    char inputFilePath[] = "../input.txt";
    FILE *fp = fopen(inputFilePath, "r");
    if (fp == NULL) {
        printf("failed to read input file path %s", inputFilePath);
        exit(EXIT_FAILURE);
    }
    int l, w, h;
    int ribbonLen = 0;
    while(fscanf(fp, "%dx%dx%d", &l, &w, &h) != EOF) {
        int minPerimeter = 2 * min(min(l+w, l+h), w+h);
        ribbonLen += minPerimeter + (l*w*h);
    }
    fclose(fp);
    printf("%d ft of ribbon is required\n", ribbonLen);
}

int min(int a, int b) {
    if (a < b) {
        return a;
    }
    return b;
}
