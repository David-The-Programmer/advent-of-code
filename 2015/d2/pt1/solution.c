#include <stdio.h>
#include <stdlib.h>

int main(void) {
    char inputFilePath[] = "../input.txt";
    int l, w, h;
    FILE *fp = fopen(inputFilePath, "r");
    if (fp == NULL) {
        printf("failed to read input file path %s\n", inputFilePath);
        exit(EXIT_FAILURE);
    }
    int totalPaperArea = 0;
    while(fscanf(fp, "%dx%dx%d", &l, &w, &h) != EOF) {
        totalPaperArea += (2*l*w) + (2*w*h) + (2*h*l);
        int smallestArea = l*w;
        if (smallestArea > w*h) {
            smallestArea = w*h;
        }
        if (smallestArea > h*l) {
            smallestArea = h*l;
        }
        totalPaperArea += smallestArea;
    }
    fclose(fp);
    printf("Total square feet of wrapped paper required is %d\n", totalPaperArea);
}
