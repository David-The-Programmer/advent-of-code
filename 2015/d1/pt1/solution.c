#include <stdio.h>
#include <stdlib.h>

int main(void) {
    char inputPath[] = "../input.txt";
    FILE *fp = fopen(inputPath, "r");
    if (fp == NULL) {
        printf("failed to read %s", inputPath);
        exit(EXIT_FAILURE);
    }
    int ch;
    int floor = 0;
    while((ch = getc(fp)) != EOF) {
        if (ch == '(') {
            floor++;
        } else if (ch == ')') {
            floor--;
        }
    }
    fclose(fp);
    printf("The solution is floor %d\n", floor);
}
